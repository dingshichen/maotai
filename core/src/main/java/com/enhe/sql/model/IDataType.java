package com.enhe.sql.model;

import java.sql.JDBCType;

/**
 * @author ding.shichen
 */
public interface IDataType {

    /**
     * 字段类型
     */
    JDBCType getType();

    /**
     * 长度精度
     */
    String getLengthPrecision();

}
