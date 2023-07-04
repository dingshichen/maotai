package com.enhe.sql;

import com.enhe.sql.generate.SQLGenerator;
import com.enhe.sql.generate.SQLGeneratorManager;
import com.enhe.sql.model.*;
import com.enhe.sql.model.impl.DDLScript;
import com.enhe.sql.parse.SQLParseAdapterManager;

/**
 * MySQL 的 SQL 转换器
 * @author ding.shichen
 */
public class MySQLTransfer implements SQLTransfer {

    @Override
    public IScript transform(String sql, DBProduct target) {
        SQLGenerator sqlGenerator = SQLGeneratorManager.getInstance(target);

        ParseResult result = SQLParseAdapterManager.MySQLParseAdapter.parse(sql);

        IScript script = new DDLScript();
        for (Ordered ddl : result.getDDLAsOrdered()) {
            if (ddl instanceof ITable) {
                script.addScript(sqlGenerator.toCreateTable((ITable) ddl));
            } else if (ddl instanceof IAlterTable) {
                script.addScript(sqlGenerator.toAlterTable((IAlterTable) ddl));
            } else if (ddl instanceof ITableIndex) {
                script.addScript(sqlGenerator.toDropIndex((ITableIndex) ddl));
            } else if (ddl instanceof IName) {
                script.addScript(sqlGenerator.toDropTable((IName) ddl));
            }
        }
        return script;
    }
}
