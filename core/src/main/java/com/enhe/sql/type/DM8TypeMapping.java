package com.enhe.sql.type;

import com.enhe.sql.model.IDataType;

import java.sql.JDBCType;

/**
 * @author ding.shichen
 */
public class DM8TypeMapping implements ITypeMapping {

    @Override
    public JDBCType getType(String type) {
        // TODO

        return null;
    }

    /**
     * TODO
     */
    @Override
    public String getText(IDataType dataType) {
        switch (dataType.getType()) {
            case BIT:
                return "bit";
            case TINYINT:
            case SMALLINT:
            case INTEGER:
            case BOOLEAN:
                return "int";
            case BIGINT:
                return "bigint";
            case FLOAT:
            case REAL:
                return "float";
            case DOUBLE:
            case NUMERIC:
                return "double";
            case DECIMAL:
                return "decimal" + dataType.getLengthPrecision();
            case DATE:
            case TIME:
            case TIMESTAMP:
            case TIME_WITH_TIMEZONE:
            case TIMESTAMP_WITH_TIMEZONE:
                return "datetime";
            case JAVA_OBJECT:
                return "json";
            case CHAR:
                return "char" + dataType.getLengthPrecision();
            case VARCHAR:
            case LONGVARCHAR:
            case BINARY:
            case VARBINARY:
            case LONGVARBINARY:
            case NULL:
            case OTHER:
            case DISTINCT:
            case STRUCT:
            case ARRAY:
            case BLOB:
            case CLOB:
            case REF:
            case DATALINK:
            case ROWID:
            case NCHAR:
            case NVARCHAR:
            case LONGNVARCHAR:
            case NCLOB:
            case SQLXML:
            case REF_CURSOR:
            default:
                return "varchar" + dataType.getLengthPrecision();
        }
    }
}