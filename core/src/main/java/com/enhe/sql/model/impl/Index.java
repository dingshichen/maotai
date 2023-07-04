package com.enhe.sql.model.impl;

import com.enhe.sql.model.IIndex;

import java.util.List;

/**
 * @author ding.shichen
 */
public class Index implements IIndex {

    private int order;

    private boolean isUnique;

    private String name;

    private List<String> columnNames;

    public Index(int order, boolean isUnique, String name, List<String> columnNames) {
        this.order = order;
        this.isUnique = isUnique;
        this.name = name;
        this.columnNames = columnNames;
    }

    @Override
    public boolean isUnique() {
        return isUnique;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
