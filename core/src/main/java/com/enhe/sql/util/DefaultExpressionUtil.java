package com.enhe.sql.util;

import com.enhe.sql.model.IExpression;
import com.enhe.sql.model.impl.DefaultBitExpression;
import com.enhe.sql.model.impl.DefaultExpression;
import com.enhe.sql.model.impl.DefaultNullExpression;

import java.util.regex.Pattern;

/**
 * @author ding.shichen
 */
public abstract class DefaultExpressionUtil {

    public static IExpression of(String text) {
        if (text.equalsIgnoreCase("null")) {
            return new DefaultNullExpression();
        }
        if (Pattern.matches("b'[01]'", text)) {
            return new DefaultBitExpression(text);
        }
        return new DefaultExpression(text);
    }
}
