package com.enhe.sql.type;

import com.enhe.sql.model.IDataType;

import java.sql.JDBCType;

/**
 * @author ding.shichen
 */
public class MySQLTypeMapping implements ITypeMapping {

    @Override
    public JDBCType getType(String type) {
        String upperCase = type.toUpperCase();
        return switch (upperCase) {
            case "DATE", "DATETIME" -> JDBCType.TIMESTAMP;
            case "JSON" -> JDBCType.JAVA_OBJECT;
            case "INT" -> JDBCType.INTEGER;
            case "TEXT" -> JDBCType.LONGVARCHAR;
            default -> JDBCType.valueOf(upperCase);
        };
    }

    @Override
    public String getText(IDataType dataType) {
        // TODO

        return null;
    }
}
