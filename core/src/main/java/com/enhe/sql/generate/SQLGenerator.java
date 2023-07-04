package com.enhe.sql.generate;

import com.enhe.sql.model.*;

/**
 * SQL 生成器
 * @author ding.shichen
 */
public interface SQLGenerator {

    /**
     * 将 table 模型生成到脚本
     * @param table 表模型
     * @return SQL 脚本
     */
    IScript toCreateTable(ITable table);

    /**
     * 生成修改表结构的脚本
     * @param alterTable 修改表结构
     * @return SQL 脚本
     */
    IScript toAlterTable(IAlterTable alterTable);

    /**
     * 生成删除索引的脚本
     * @param tableIndex 表索引
     * @return SQL 脚本
     */
    IScript toDropIndex(ITableIndex tableIndex);

    /**
     * 生成删除表的脚本
     * @param tableName 表明
     * @return SQL 脚本
     */
    IScript toDropTable(IName tableName);
}
