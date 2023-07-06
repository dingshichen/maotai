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
        return switch (dataType.getType()) {
            case BIT -> "bit";
            case TINYINT, SMALLINT, INTEGER, BOOLEAN -> "int";
            case BIGINT -> "bigint";
            case FLOAT, REAL -> "float";
            case DOUBLE, NUMERIC -> "double";
            case DECIMAL -> "decimal" + dataType.getLengthPrecision();
            case DATE, TIME, TIMESTAMP, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE -> "datetime";
            case JAVA_OBJECT -> "clob";
            case CHAR -> "char" + dataType.getLengthPrecision();
            case LONGVARCHAR -> "text";
            default -> "varchar" + dataType.getLengthPrecision();
        };
    }
}
