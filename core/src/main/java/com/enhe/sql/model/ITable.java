package com.enhe.sql.model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author ding.shichen
 */
public interface ITable extends Ordered {

    String getName();

    @Nullable String getComment();

    List<IColumn> getColumns();

    @Nullable List<IColumn> getPrimaryKey();

    @Nullable List<IIndex> getIndexGroup();
}
