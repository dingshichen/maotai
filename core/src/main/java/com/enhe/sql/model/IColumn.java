package com.enhe.sql.model;

import javax.annotation.Nullable;

/**
 * 字段
 * @author ding.shichen
 */
public interface IColumn extends Ordered {

    /**
     * 字段名
     */
    String getName();

    /**
     * 数据类型
     */
    IDataType getDataType();

    /**
     * 是否可空
     */
    boolean isNullable();

    /**
     * 默认值
     */
    @Nullable IExpression getDefaultExpression();

    /**
     * 注释
     */
    @Nullable String getComment();

}
