package com.enhe.sql.generate;

import com.enhe.sql.lang.RuleWrapperUtil;
import com.enhe.sql.model.*;
import com.enhe.sql.model.impl.DDLScript;
import com.enhe.sql.model.impl.DefaultExpression;
import com.enhe.sql.model.impl.DefaultNullExpression;
import com.enhe.sql.type.ITypeMapping;
import com.enhe.sql.type.TypeMappingManager;

import javax.annotation.Nullable;
import java.sql.JDBCType;
import java.util.List;
import java.util.stream.Collectors;

import static com.enhe.sql.DBProduct.DM8;

/**
 * @author ding.shichen
 */
public class DM8Generator implements SQLGenerator {

    private static final String DROP_IF_EXIST_TEMP = "drop table if exists %s cascade;";

    private static final String CREATE_TABLE_TEMP = "create table if not exists %s (\n" +
                                                    "%s\n" +
                                                    ");";

    private static final String CREATE_TABLE_COLUMN_TEMP = "    %s %s %s %s";

    private static final String CREATE_TABLE_PK_TEMP = "    primary key (%s)";

    private static final String TABLE_COMMENT_TEMP = "comment on table %s is '%s';";

    private static final String COLUMN_COMMENT_TEMP = "comment on column %s.%s is '%s';";

    private static final String ADD_COLUMN_TEMP = "alter table %s add %s %s %s %s;";

    private static final String DROP_COLUMN_TEMP = "alter table %s drop %s;";

    private static final String MODIFY_COLUMN_TEMP = "alter table %s modify %s %s %s %s;";

    private static final String ADD_INDEX_TEMP = "create index %s on %s (%s);";

    private static final String ADD_UNIQUE_TEMP = "create unique index %s on %s (%s);";

    private static final String DROP_INDEX = "drop index %s;";

    private static final String DROP_TABLE = "drop table if exists %s;";

    private static final String RENAME = "alter table %s rename to %s;";

    @Override
    public IScript toCreateTable(ITable table) {
        ITypeMapping typeMapping = TypeMappingManager.getInstance(DM8);

        IScript script = new DDLScript();

        String tableName = RuleWrapperUtil.getWrapperName(table.getName(), DM8);

        String columnsText = table.getColumns().stream()
                .map(e -> String.format(CREATE_TABLE_COLUMN_TEMP, RuleWrapperUtil.getWrapperName(e.getName(), DM8),
                        typeMapping.getText(e.getDataType()),
                        e.isNullable() ? "" : "not null",
                        defaultText(e.getDataType().getType(), e.getDefaultExpression())))
                .collect(Collectors.joining(",\n"));
        if (table.getPrimaryKey() != null) {
            columnsText += ",\n" + String.format(CREATE_TABLE_PK_TEMP, table.getPrimaryKey().stream()
                    .map(e -> RuleWrapperUtil.getWrapperName(e.getName(), DM8))
                    .collect(Collectors.joining(", ")));
        }
        script.addText(String.format(CREATE_TABLE_TEMP, tableName, columnsText));

        if (table.getComment() != null) {
            script.addText(String.format(TABLE_COMMENT_TEMP, tableName, table.getComment()));
        }

        table.getColumns().stream()
                .filter(e -> e.getComment() != null)
                .forEach(e -> script.addText(String.format(COLUMN_COMMENT_TEMP, tableName, RuleWrapperUtil.getWrapperName(e.getName(), DM8), e.getComment())));

        if (table.getIndexGroup() != null) {
            table.getIndexGroup().forEach(e ->
                    script.addText(String.format(e.isUnique() ? ADD_UNIQUE_TEMP : ADD_INDEX_TEMP, e.getName(), tableName,
                            e.getColumnNames().stream()
                                    .map(c -> RuleWrapperUtil.getWrapperName(c, DM8))
                                    .collect(Collectors.joining(", ")))
                    )
            );
        }
        return script;
    }

    @Override
    public IScript toAlterTable(IAlterTable alterTable) {
        ITypeMapping typeMapping = TypeMappingManager.getInstance(DM8);
        IScript script = new DDLScript();
        List<IColumn> columns = alterTable.getAddColumns();

        String tableName = RuleWrapperUtil.getWrapperName(alterTable.getTableName(), DM8);
        if (columns != null) {
            for (IColumn column : columns) {
                String columnName = RuleWrapperUtil.getWrapperName(column.getName(), DM8);
                script.addText(String.format(ADD_COLUMN_TEMP, tableName, columnName, typeMapping.getText(column.getDataType()), column.isNullable() ? "" : "not null", defaultText(column.getDataType().getType(), column.getDefaultExpression())));
                script.addText(String.format(COLUMN_COMMENT_TEMP, tableName, columnName, column.getComment()));
            }
        }
        List<IName> dropColumns = alterTable.getDropColumns();
        if (dropColumns != null) {
            for (IName column : dropColumns) {
                script.addText(String.format(DROP_COLUMN_TEMP, tableName, RuleWrapperUtil.getWrapperName(column.getText(), DM8)));
            }
        }

        List<IColumn> modifyColumns = alterTable.getModifyColumns();
        if (modifyColumns != null) {
            for (IColumn column : modifyColumns) {
                String columnName = RuleWrapperUtil.getWrapperName(column.getName(), DM8);
                script.addText(String.format(MODIFY_COLUMN_TEMP, tableName, columnName, typeMapping.getText(column.getDataType()), column.isNullable() ? "" : "not null", defaultText(column.getDataType().getType(), column.getDefaultExpression())));
                if (column.getComment() != null) {
                    script.addText(String.format(COLUMN_COMMENT_TEMP, tableName, columnName, column.getComment()));
                }
            }
        }
        if (alterTable.getAddIndex() != null) {
            for (IIndex index : alterTable.getAddIndex()) {
                if (index.isUnique()) {
                    script.addText(String.format(ADD_UNIQUE_TEMP, index.getName(), tableName,
                            index.getColumnNames().stream()
                                    .map(c -> RuleWrapperUtil.getWrapperName(c, DM8))
                                    .collect(Collectors.joining(", ")))
                    );
                } else {
                    script.addText(String.format(ADD_INDEX_TEMP, index.getName(), tableName,
                            index.getColumnNames().stream()
                                    .map(c -> RuleWrapperUtil.getWrapperName(c, DM8))
                                    .collect(Collectors.joining(", ")))
                    );
                }
            }
        }
        if (alterTable.getDropIndex() != null) {
            for (IName index : alterTable.getDropIndex()) {
                script.addText(String.format(DROP_INDEX, index.getText()));
            }
        }
        if (alterTable.getRename() != null) {
            script.addText(String.format(RENAME, tableName, RuleWrapperUtil.getWrapperName(alterTable.getRename(), DM8)));
        }
        return script;
    }

    @Override
    public IScript toDropIndex(ITableIndex tableIndex) {
        IScript script = new DDLScript();
        script.addText(String.format(DROP_INDEX, tableIndex.getIndexName()));
        return script;
    }

    @Override
    public IScript toDropTable(IName tableName) {
        IScript script = new DDLScript();
        script.addText(String.format(DROP_TABLE, tableName.getText()));
        return script;
    }

    private String defaultText(JDBCType type, @Nullable IExpression defaultExpression) {
        if (defaultExpression == null) {
            return "";
        }
        switch (type) {
            case DATE:
            case TIME:
            case TIMESTAMP:
            case TIME_WITH_TIMEZONE:
            case TIMESTAMP_WITH_TIMEZONE:
                return "";
            default:
                if (defaultExpression instanceof DefaultNullExpression) {
                    return "";
                }
                return "default " + defaultExpression.getText();
        }
    }
}
