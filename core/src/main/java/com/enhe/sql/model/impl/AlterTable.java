package com.enhe.sql.model.impl;

import com.enhe.sql.model.IAlterTable;
import com.enhe.sql.model.IColumn;
import com.enhe.sql.model.IIndex;
import com.enhe.sql.model.IName;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author ding.shichen
 */
public class AlterTable implements IAlterTable {

    private int order;

    private String tableName;

    private List<IColumn> addColumns;

    private List<IName> dropColumns;

    private List<IColumn> modifyColumns;

    private List<IIndex> addIndex;

    private List<IName> dropIndex;

    private String rename;

    public AlterTable(int order, String tableName,
                      @Nullable List<IColumn> column,
                      @Nullable List<IName> dropColumns,
                      @Nullable List<IColumn> modifyColumns,
                      @Nullable List<IIndex> addIndex,
                      @Nullable List<IName> dropIndex,
                      @Nullable String rename) {
        this.order = order;
        this.tableName = tableName;
        this.addColumns = column;
        this.dropColumns = dropColumns;
        this.modifyColumns = modifyColumns;
        this.addIndex = addIndex;
        this.dropIndex = dropIndex;
        this.rename = rename;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public @Nullable List<IColumn> getAddColumns() {
        return addColumns;
    }

    @Override
    public @Nullable List<IName> getDropColumns() {
        return dropColumns;
    }

    @Override
    public @Nullable List<IColumn> getModifyColumns() {
        return modifyColumns;
    }

    @Override
    public @Nullable List<IIndex> getAddIndex() {
        return addIndex;
    }

    @Override
    public @Nullable List<IName> getDropIndex() {
        return dropIndex;
    }

    @Override
    public @Nullable String getRename() {
        return rename;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
