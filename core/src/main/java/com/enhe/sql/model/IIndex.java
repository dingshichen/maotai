package com.enhe.sql.model;

import java.util.List;

/**
 * @author ding.shichen
 */
public interface IIndex extends Ordered {

    boolean isUnique();

    String getName();

    List<String> getColumnNames();
}
