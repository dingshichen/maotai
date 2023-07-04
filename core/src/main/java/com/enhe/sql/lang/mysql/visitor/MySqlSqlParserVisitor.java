package com.enhe.sql.lang.mysql.visitor;

import com.enhe.sql.DBProduct;
import com.enhe.sql.lang.RuleWrapperUtil;
import com.enhe.sql.lang.mysql.MySqlParser;
import com.enhe.sql.lang.mysql.MySqlParserBaseVisitor;
import com.enhe.sql.model.*;
import com.enhe.sql.model.impl.*;
import com.enhe.sql.type.ITypeMapping;
import com.enhe.sql.type.TypeMappingManager;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author ding.shichen
 */
public class MySqlSqlParserVisitor extends MySqlParserBaseVisitor<Object> {

    private AtomicInteger count = new AtomicInteger(0);

    private List<ITable> createTables = new ArrayList<>();

    private List<IAlterTable> alterTables = new ArrayList<>();

    private List<ITableIndex> dropIndex = new ArrayList<>();

    private List<IName> dropTables = new ArrayList<>();

    public List<ITable> getCreateTables() {
        return createTables;
    }

    public List<IAlterTable> getAlterTables() {
        return alterTables;
    }

    public List<ITableIndex> getDropIndex() {
        return dropIndex;
    }

    public List<IName> getDropTables() {
        return dropTables;
    }

    @Override
    public ITable visitColumnCreateTable(MySqlParser.ColumnCreateTableContext ctx) {
        String tableName = RuleWrapperUtil.getMySQLRealName(ctx.tableName().getText());

        MySqlCreateColumnParserVisitor visitor = new MySqlCreateColumnParserVisitor();
        ctx.createDefinitions().accept(visitor);
        String comment = null;
        for (MySqlParser.TableOptionContext tableOption : ctx.tableOption()) {
            if (tableOption instanceof MySqlParser.TableOptionCommentContext) {
                comment = tableOption.stop.getText().substring(1, tableOption.stop.getText().length() - 1);
            }
        }
        Table table = new Table(count.getAndIncrement(), tableName, comment, visitor.getColumnGroup(), visitor.getIndexGroup());
        createTables.add(table);
        return table;
    }

    /**
     * 修改表结构，MySQL 支持同一个表的多个修改用 "," 分割
     * @return
     */
    @Override
    public IAlterTable visitAlterTable(MySqlParser.AlterTableContext ctx) {
        // 一个 alter table 语句中用 "," 分割的个多语句，也要有顺序
        AtomicInteger alterCount = new AtomicInteger(0);
        MySqlParser.TableNameContext tableNameContext = ctx.tableName();
        List<IColumn> addColumns = new ArrayList<>();
        List<IName> dropColumns = new ArrayList<>();
        List<IColumn> modifyColumns = new ArrayList<>();
        List<IIndex> addIndex = new ArrayList<>();
        List<IName> dropIndex = new ArrayList<>();
        for (MySqlParser.AlterSpecificationContext alterSpecificationContext : ctx.alterSpecification()) {
            if (alterSpecificationContext instanceof MySqlParser.AlterByAddColumnContext) {
                // 新增字段
                addColumns(addColumns, (MySqlParser.AlterByAddColumnContext) alterSpecificationContext, alterCount);
            } else if (alterSpecificationContext instanceof MySqlParser.AlterByDropColumnContext) {
                // 删除字段
                dropColumns(dropColumns, (MySqlParser.AlterByDropColumnContext) alterSpecificationContext, alterCount);
            } else if (alterSpecificationContext instanceof MySqlParser.AlterByModifyColumnContext) {
                // 修改字段
                modifyColumns(modifyColumns, (MySqlParser.AlterByModifyColumnContext) alterSpecificationContext, alterCount);
            } else if (alterSpecificationContext instanceof MySqlParser.AlterByAddIndexContext) {
                // 增加索引
                addIndex(addIndex, (MySqlParser.AlterByAddIndexContext) alterSpecificationContext, alterCount);
            } else if (alterSpecificationContext instanceof MySqlParser.AlterByAddUniqueKeyContext) {
                // 增加唯一索引
                addUnique(addIndex, (MySqlParser.AlterByAddUniqueKeyContext) alterSpecificationContext, alterCount);
            } else if (alterSpecificationContext instanceof MySqlParser.AlterByDropIndexContext) {
                // 删除索引
                dropIndex(dropIndex, (MySqlParser.AlterByDropIndexContext) alterSpecificationContext, alterCount);
            }
        }
        IAlterTable alterTable = new AlterTable(count.getAndIncrement(), RuleWrapperUtil.getMySQLRealName(tableNameContext.getText()), addColumns, dropColumns, modifyColumns, addIndex, dropIndex);
        alterTables.add(alterTable);
        return alterTable;
    }

    @Override
    public List<ITableIndex> visitDropIndex(MySqlParser.DropIndexContext ctx) {
        String tableName = RuleWrapperUtil.getMySQLRealName(ctx.tableName().getText());
        String indexName = ctx.uid().simpleId().ID().getText();
        dropIndex.add(new TableIndex(count.getAndIncrement(), tableName, indexName));
        return dropIndex;
    }

    @Override
    public List<IName> visitDropTable(MySqlParser.DropTableContext ctx) {
        for (MySqlParser.TableNameContext tableNameContext : ctx.tables().tableName()) {
            dropTables.add(new Name(count.getAndIncrement(), RuleWrapperUtil.getMySQLRealName(tableNameContext.getText())));
        }
        return dropTables;
    }

    /**
     * 新增字段
     */
    private void addColumns(List<IColumn> addColumns, MySqlParser.AlterByAddColumnContext context, AtomicInteger alterCount) {
        String columnName = RuleWrapperUtil.getMySQLRealName(context.uid(0).getText());
        // 获取字段类型
        IDataType dataType = null;
        ParseTree parseTree = context.columnDefinition().children.get(0);
        if (parseTree instanceof MySqlParser.DataTypeContext) {
            MySqlParser.DataTypeContext dataTypeContext = (MySqlParser.DataTypeContext) parseTree;
            ITypeMapping typeMapping = TypeMappingManager.getInstance(DBProduct.MySQL);
            JDBCType type = typeMapping.getType(dataTypeContext.children.get(0).getText());
            String lengthPrecision = dataTypeContext.children.size() >= 2 ? dataTypeContext.children.get(1).getText() : null;
            dataType = new DataType(type, lengthPrecision);
        }
        // 字段注释
        String columnComment = null;
        // 是否可空
        boolean isNullable = false;
        // 默认值
        IExpression defaultExpression = null;
        for (MySqlParser.ColumnConstraintContext columnConstraint : context.columnDefinition().columnConstraint()) {
            if (columnConstraint instanceof MySqlParser.CommentColumnConstraintContext) {
                columnComment = columnConstraint.stop.getText().substring(1, columnConstraint.stop.getText().length() - 1);
            } else if (columnConstraint instanceof MySqlParser.DefaultColumnConstraintContext) {
                MySqlParser.DefaultValueContext defaultValue = ((MySqlParser.DefaultColumnConstraintContext) columnConstraint).defaultValue();
                defaultExpression = new DefaultExpression(defaultValue.getText());
                if ("null".equalsIgnoreCase(defaultValue.getText()) ) {
                    isNullable = true;
                }
            } else if (columnConstraint instanceof MySqlParser.NullColumnConstraintContext) {
                isNullable = ((MySqlParser.NullColumnConstraintContext) columnConstraint).nullNotnull().children.size() != 2;
            }
        }
        addColumns.add(new Column(alterCount.getAndIncrement(), columnName, dataType, isNullable, defaultExpression, columnComment));
    }

    /**
     * 删除字段
     */
    private void dropColumns(List<IName> dropColumns, MySqlParser.AlterByDropColumnContext context, AtomicInteger alterCount) {
        dropColumns.add(new Name(alterCount.getAndIncrement(), RuleWrapperUtil.getMySQLRealName(context.uid().simpleId().ID().getText())));
    }

    /**
     * 修改字段
     */
    private void modifyColumns(List<IColumn> modifyColumns, MySqlParser.AlterByModifyColumnContext context, AtomicInteger alterCount) {
        String columnName = RuleWrapperUtil.getMySQLRealName(context.uid(0).getText());
        // 获取字段类型
        IDataType dataType = null;
        ParseTree parseTree = context.columnDefinition().children.get(0);
        if (parseTree instanceof MySqlParser.DataTypeContext) {
            MySqlParser.DataTypeContext dataTypeContext = (MySqlParser.DataTypeContext) parseTree;
            ITypeMapping typeMapping = TypeMappingManager.getInstance(DBProduct.MySQL);
            JDBCType type = typeMapping.getType(dataTypeContext.children.get(0).getText());
            String lengthPrecision = dataTypeContext.children.size() >= 2 ? dataTypeContext.children.get(1).getText() : null;
            dataType = new DataType(type, lengthPrecision);
        }
        // 字段注释
        String columnComment = null;
        // 是否可空
        boolean isNullable = false;
        // 默认值
        IExpression defaultExpression = null;
        for (MySqlParser.ColumnConstraintContext columnConstraint : context.columnDefinition().columnConstraint()) {
            if (columnConstraint instanceof MySqlParser.CommentColumnConstraintContext) {
                columnComment = columnConstraint.stop.getText().substring(1, columnConstraint.stop.getText().length() - 1);
            } else if (columnConstraint instanceof MySqlParser.DefaultColumnConstraintContext) {
                MySqlParser.DefaultValueContext defaultValue = ((MySqlParser.DefaultColumnConstraintContext) columnConstraint).defaultValue();
                defaultExpression = new DefaultExpression(defaultValue.getText());
                if ("null".equalsIgnoreCase(defaultValue.getText()) ) {
                    isNullable = true;
                }
            } else if (columnConstraint instanceof MySqlParser.NullColumnConstraintContext) {
                isNullable = ((MySqlParser.NullColumnConstraintContext) columnConstraint).nullNotnull().children.size() != 2;
            }
        }
        modifyColumns.add(new Column(alterCount.getAndIncrement(), columnName, dataType, isNullable, defaultExpression, columnComment));
    }

    /**
     * 增加索引
     */
    private void addIndex(List<IIndex> addIndex, MySqlParser.AlterByAddIndexContext alterSpecificationContext, AtomicInteger alterCount) {
        String indexName = alterSpecificationContext.uid().simpleId().getText();
        addIndex.add(new Index(alterCount.getAndIncrement(), false,
                indexName,
                alterSpecificationContext.indexColumnNames().indexColumnName().stream()
                        .map(RuleContext::getText)
                        .collect(Collectors.toList()))
        );
    }

    /**
     * 增加唯一索引
     */
    private void addUnique(List<IIndex> addIndex, MySqlParser.AlterByAddUniqueKeyContext alterSpecificationContext, AtomicInteger alterCount) {
        String indexName = alterSpecificationContext.uid(0).simpleId().ID().getText();
        addIndex.add(new Index(alterCount.getAndIncrement(), true,
                indexName,
                alterSpecificationContext.indexColumnNames().indexColumnName().stream()
                        .map(RuleContext::getText)
                        .collect(Collectors.toList()))
        );
    }

    /**
     * 删除索引
     */
    private void dropIndex(List<IName> dropIndex, MySqlParser.AlterByDropIndexContext context, AtomicInteger alterCount) {
        dropIndex.add(new Name(alterCount.getAndIncrement(), context.uid().simpleId().ID().getText()));
    }
}
