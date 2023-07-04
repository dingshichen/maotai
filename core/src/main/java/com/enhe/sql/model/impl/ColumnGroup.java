package com.enhe.sql.model.impl;

import com.enhe.sql.model.IColumn;

import java.util.List;

/**
 * @author ding.shichen
 */
public class ColumnGroup {

    private List<IColumn> columns;

    private List<IColumn> primaryKey;

    public ColumnGroup(List<IColumn> columns, List<IColumn> primaryKey) {
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public List<IColumn> getColumns() {
        return columns;
    }

    public List<IColumn> getPrimaryKey() {
        return primaryKey;
    }
}
