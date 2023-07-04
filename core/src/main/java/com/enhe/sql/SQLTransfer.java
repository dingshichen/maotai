package com.enhe.sql;

import com.enhe.sql.model.IScript;

/**
 * SQK 转换
 * @author ding.shichen
 */
public interface SQLTransfer {

    /**
     * 把一个 SQL 转换成目标平台的 SQL 脚本
     * @param sql
     * @param target 目标平台
     * @return 转换后的脚本
     */
    IScript transform(String sql, DBProduct target);

}
