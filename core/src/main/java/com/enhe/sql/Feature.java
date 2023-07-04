package com.enhe.sql;

/**
 * @author ding.shichen
 */
public abstract class Feature {

    /**
     * 支持语法
     */
    public static String getComment() {
        return "Create table, Alter table add column, Alter table modify column, Alter table drop column, Alter table add index, Alter table drop index, Drop table, Drop Index.";
    }
}
