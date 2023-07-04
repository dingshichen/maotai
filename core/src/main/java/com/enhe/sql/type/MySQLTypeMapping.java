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
        switch (upperCase) {
            case "DATE":
            case "DATETIME":
                return JDBCType.TIMESTAMP;
            case "JSON":
                return JDBCType.JAVA_OBJECT;
            case "INT":
                return JDBCType.INTEGER;
            case "TEXT":
                return JDBCType.LONGVARCHAR;
            default:
                return JDBCType.valueOf(upperCase);
        }
    }

    @Override
    public String getText(IDataType dataType) {
        // TODO

        return null;
    }
}
