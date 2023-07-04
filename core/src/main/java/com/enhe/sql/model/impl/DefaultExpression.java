package com.enhe.sql.model.impl;

import com.enhe.sql.model.IExpression;

/**
 * @author ding.shichen
 */
public class DefaultExpression implements IExpression {

    private String text;

    public DefaultExpression(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
