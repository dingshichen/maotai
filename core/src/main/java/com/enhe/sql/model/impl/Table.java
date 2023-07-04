package com.enhe.sql.model.impl;

import com.enhe.sql.model.IColumn;
import com.enhe.sql.model.IIndex;
import com.enhe.sql.model.ITable;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author ding.shichen
 */
public class Table implements ITable {

    private int order;

    private String name;

    private String comment;

    private ColumnGroup columnGroup;

    private List<IIndex> indexGroup;

    public Table(int order, String name, String comment, ColumnGroup columnGroup, List<IIndex> indexGroup) {
        this.order = order;
        this.name = name;
        this.comment = comment;
        this.columnGroup = columnGroup;
        this.indexGroup = indexGroup;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public @Nullable String getComment() {
        return comment;
    }

    @Override
    public List<IColumn> getColumns() {
        return columnGroup.getColumns();
    }

    @Override
    public @Nullable List<IColumn> getPrimaryKey() {
        return columnGroup.getPrimaryKey();
    }

    @Nullable
    @Override
    public List<IIndex> getIndexGroup() {
        return indexGroup;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
