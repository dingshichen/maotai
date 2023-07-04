package com.enhe.sql.model.impl;

import com.enhe.sql.model.IColumn;
import com.enhe.sql.model.IDataType;
import com.enhe.sql.model.IExpression;

import javax.annotation.Nullable;

/**
 * @author ding.shichen
 */
public class Column implements IColumn {

    private int order;

    private String name;

    private IDataType dataType;

    private boolean isNullable;

    private IExpression defaultExpression;

    private String comment;

    public Column(int order, String name, IDataType dataType, boolean isNullable, IExpression defaultExpression, String comment) {
        this.order = order;
        this.name = name;
        this.dataType = dataType;
        this.isNullable = isNullable;
        this.defaultExpression = defaultExpression;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IDataType getDataType() {
        return dataType;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public @Nullable IExpression getDefaultExpression() {
        return defaultExpression;
    }

    @Override
    public @Nullable String getComment() {
        return comment;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
