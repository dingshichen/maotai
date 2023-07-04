package com.enhe.sql.model.impl;

import com.enhe.sql.model.IDataType;

import javax.annotation.Nullable;
import java.sql.JDBCType;

/**
 * @author ding.shichen
 */
public class DataType implements IDataType {

    private JDBCType jdbcType;

    private String lengthPrecision;

    public DataType(JDBCType jdbcType, @Nullable String lengthPrecision) {
        this.jdbcType = jdbcType;
        this.lengthPrecision = lengthPrecision == null ? "" : lengthPrecision;
    }

    @Override
    public JDBCType getType() {
        return jdbcType;
    }

    @Override
    public String getLengthPrecision() {
        return lengthPrecision;
    }
}
