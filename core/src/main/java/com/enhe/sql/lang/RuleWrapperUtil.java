package com.enhe.sql.lang;

import com.enhe.sql.DBProduct;
import com.enhe.sql.lang.mysql.MySqlLexer;
import com.enhe.sql.lang.plsql.PlSqlLexer;
import com.enhe.sql.lang.postgresql.PostgreSQLLexer;

import java.util.Arrays;

/**
 * @author ding.shichen
 */
public abstract class RuleWrapperUtil {

    public static String getWrapperName(String name, DBProduct product) {
        switch (product) {
            case MySQL:
                if (Arrays.stream(MySqlLexer.ruleNames).anyMatch(e -> e.equalsIgnoreCase(name))) {
                    return String.format("`%s`", name);
                } else {
                    return name;
                }
            case DM8:
                if (Arrays.stream(PlSqlLexer.ruleNames).anyMatch(e -> e.equalsIgnoreCase(name))) {
                    return String.format("\"%s\"", name.toUpperCase());
                } else {
                    return name;
                }
            case GaussDB:
                if (Arrays.stream(PostgreSQLLexer.ruleNames).anyMatch(e -> e.equalsIgnoreCase(name))) {
                    return String.format("\"%s\"", name.toUpperCase());
                } else {
                    return name;
                }
            default:
                return name;
        }
    }

    public static String getMySQLRealName(String text) {
        if (text.startsWith("`") && text.endsWith("`")) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }
}
