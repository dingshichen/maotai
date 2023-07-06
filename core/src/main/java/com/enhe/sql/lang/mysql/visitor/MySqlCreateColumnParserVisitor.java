package com.enhe.sql.lang.mysql.visitor;

import com.enhe.sql.DBProduct;
import com.enhe.sql.lang.RuleWrapperUtil;
import com.enhe.sql.lang.mysql.MySqlParser;
import com.enhe.sql.lang.mysql.MySqlParserBaseVisitor;
import com.enhe.sql.model.IDataType;
import com.enhe.sql.model.IExpression;
import com.enhe.sql.model.IIndex;
import com.enhe.sql.model.impl.*;
import com.enhe.sql.type.ITypeMapping;
import com.enhe.sql.type.TypeMappingManager;
import com.enhe.sql.util.DefaultExpressionUtil;
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
public class MySqlCreateColumnParserVisitor extends MySqlParserBaseVisitor<ColumnGroup> {

    private AtomicInteger count = new AtomicInteger(0);

    private ColumnGroup columnGroup = new ColumnGroup(new ArrayList<>(), new ArrayList<>());

    private List<IIndex> indexGroup = new ArrayList<>();

    public ColumnGroup getColumnGroup() {
        return columnGroup;
    }
    
    public List<IIndex> getIndexGroup() {
        return indexGroup;
    }

    /**
     * 字段定义
     */
    @Override
    public ColumnGroup visitColumnDeclaration(MySqlParser.ColumnDeclarationContext ctx) {
        // 字段名
        String columnName = RuleWrapperUtil.getMySQLRealName(ctx.fullColumnName().getText());

        // 获取字段类型
        IDataType dataType = null;
        ParseTree parseTree = ctx.columnDefinition().children.get(0);
        if (parseTree instanceof MySqlParser.DataTypeContext) {
            MySqlParser.DataTypeContext dataTypeContext = (MySqlParser.DataTypeContext) parseTree;
            ITypeMapping typeMapping = TypeMappingManager.getInstance(DBProduct.MySQL);
            JDBCType type = typeMapping.getType(dataTypeContext.children.get(0).getText());
            String lengthPrecision = dataTypeContext.children.size() >= 2 ? dataTypeContext.children.get(1).getText() : null;
            dataType = new DataType(type, lengthPrecision);
        }

        // 是否为主键
        boolean isPrimaryKey = false;
        // 字段注释
        String columnComment = null;
        // 是否可空
        boolean isNullable = true;
        // 默认值
        IExpression defaultExpression = null;
        for (MySqlParser.ColumnConstraintContext columnConstraint : ctx.columnDefinition().columnConstraint()) {
            if (columnConstraint instanceof MySqlParser.PrimaryKeyColumnConstraintContext) {
                isPrimaryKey = true;
            } else if (columnConstraint instanceof MySqlParser.CommentColumnConstraintContext) {
                columnComment = columnConstraint.stop.getText().substring(1, columnConstraint.stop.getText().length() - 1);
            } else if (columnConstraint instanceof MySqlParser.DefaultColumnConstraintContext) {
                MySqlParser.DefaultValueContext defaultValue = ((MySqlParser.DefaultColumnConstraintContext) columnConstraint).defaultValue();
                defaultExpression = DefaultExpressionUtil.of(defaultValue.getText());
            } else if (columnConstraint instanceof MySqlParser.NullColumnConstraintContext) {
                isNullable = ((MySqlParser.NullColumnConstraintContext) columnConstraint).nullNotnull().children.size() != 2;
            }
        }
        if (isPrimaryKey) {
            // 如果是主键，必须非空
            isNullable = false;
        }
        Column column = new Column(count.getAndIncrement(), columnName, dataType, isNullable, defaultExpression, columnComment);
        columnGroup.getColumns().add(column);
        if (isPrimaryKey) {
            columnGroup.getPrimaryKey().add(column);
        }
        return columnGroup;
    }

    /**
     * 唯一键定义
     */
    @Override
    public ColumnGroup visitConstraintDeclaration(MySqlParser.ConstraintDeclarationContext ctx) {
        if (ctx.tableConstraint() instanceof MySqlParser.PrimaryKeyTableConstraintContext) {
            MySqlParser.PrimaryKeyTableConstraintContext primaryKeys = (MySqlParser.PrimaryKeyTableConstraintContext) ctx.tableConstraint();
            for (MySqlParser.IndexColumnNameContext indexColumnNameContext : primaryKeys.indexColumnNames().indexColumnName()) {
                columnGroup.getColumns().stream()
                        .filter(e -> e.getName().equals(RuleWrapperUtil.getMySQLRealName(indexColumnNameContext.getText())))
                        .findAny()
                        .ifPresent(e -> columnGroup.getPrimaryKey().add(e));
            }
        } else if (ctx.tableConstraint() instanceof MySqlParser.UniqueKeyTableConstraintContext) {
            MySqlParser.UniqueKeyTableConstraintContext uniqueKeys = (MySqlParser.UniqueKeyTableConstraintContext) ctx.tableConstraint();
            String indexName = uniqueKeys.uid(0).simpleId().getText().replace("`", "");
            Index index = new Index(count.getAndIncrement(), true,
                    indexName,
                    uniqueKeys.indexColumnNames().indexColumnName().stream()
                            .map(RuleContext::getText).map(r->r.replace("`", ""))
                            .collect(Collectors.toList())
            );
            indexGroup.add(index);
        }
        return columnGroup;
    }

    /**
     * 普通索引定义
     */
    @Override
    public ColumnGroup visitIndexDeclaration(MySqlParser.IndexDeclarationContext ctx) {
        if (ctx.indexColumnDefinition() instanceof MySqlParser.SimpleIndexDeclarationContext) {
            MySqlParser.SimpleIndexDeclarationContext simpleIndexDeclarationContext = (MySqlParser.SimpleIndexDeclarationContext) ctx.indexColumnDefinition();
            String indexName;
            if (simpleIndexDeclarationContext.uid() == null) {
                indexName = "idx_" + ctx.getText().hashCode();
            } else {
                indexName = simpleIndexDeclarationContext.uid().getText().replace("`", "");
            }
            Index index = new Index(count.getAndIncrement(), false,
                    indexName,
                    simpleIndexDeclarationContext.indexColumnNames().indexColumnName().stream()
                            .map(RuleContext::getText).map(r->r.replace("`", ""))
                            .collect(Collectors.toList())
            );
            indexGroup.add(index);
        }
        return columnGroup;
    }
}
