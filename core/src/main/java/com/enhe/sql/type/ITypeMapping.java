package com.enhe.sql.type;

import com.enhe.sql.model.IDataType;

import java.sql.JDBCType;

/**
 * 类型映射
 * @author ding.shichen
 */
public interface ITypeMapping {

    JDBCType getType(String type);

    String getText(IDataType dataType);
}
