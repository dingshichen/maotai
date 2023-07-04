package com.enhe.sql.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author ding.shichen
 */
public interface IAlterTable extends Ordered {

    String getTableName();

    @Nullable List<IColumn> getAddColumns();

    @Nullable List<IName> getDropColumns();

    @Nullable List<IColumn> getModifyColumns();

    @Nullable List<IIndex> getAddIndex();

    @Nullable List<IName> getDropIndex();
}
