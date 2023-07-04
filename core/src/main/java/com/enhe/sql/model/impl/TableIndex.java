package com.enhe.sql.model.impl;

import com.enhe.sql.model.ITableIndex;

/**
 * @author ding.shichen
 */
public class TableIndex implements ITableIndex {

    private int order;

    private String tableName;

    private String indexName;

    public TableIndex(int order, String tableName, String indexName) {
        this.order = order;
        this.tableName = tableName;
        this.indexName = indexName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getIndexName() {
        return indexName;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
