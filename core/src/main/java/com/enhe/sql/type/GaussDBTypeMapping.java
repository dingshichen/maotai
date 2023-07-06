package com.enhe.sql.type;

import com.enhe.sql.model.IDataType;

import java.sql.JDBCType;

/**
 * @author ding.shichen
 */
public class GaussDBTypeMapping implements ITypeMapping {

    @Override
    public JDBCType getType(String type) {
        return null;
    }

    @Override
    public String getText(IDataType dataType) {
        return switch (dataType.getType()) {
            case BIT, TINYINT, SMALLINT, INTEGER, BOOLEAN -> "int";
            case BIGINT -> "bigint";
            case FLOAT, REAL -> "float";
            case DOUBLE, NUMERIC -> "double";
            case DECIMAL -> "decimal" + dataType.getLengthPrecision();
            case DATE, TIME, TIMESTAMP, TIME_WITH_TIMEZONE, TIMESTAMP_WITH_TIMEZONE -> "timestamp(0)";
            case JAVA_OBJECT -> "json";
            case CHAR -> "char" + dataType.getLengthPrecision();
            case LONGVARCHAR -> "text";
            default -> "varchar" + dataType.getLengthPrecision();
        };
    }
}
