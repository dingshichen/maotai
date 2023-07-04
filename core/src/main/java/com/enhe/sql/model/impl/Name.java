package com.enhe.sql.model.impl;

import com.enhe.sql.model.IName;

/**
 * @author ding.shichen
 */
public class Name implements IName {

    private int order;

    private String text;

    public Name(int order, String text) {
        this.order = order;
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
