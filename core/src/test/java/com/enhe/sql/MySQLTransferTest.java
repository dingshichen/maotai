package com.enhe.sql;

import com.enhe.sql.model.IScript;
import com.enhe.sql.util.PrintUtil;
import com.enhe.sql.util.ScriptReader;
import org.junit.Test;

public class MySQLTransferTest {

    @Test
    public void transform() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/create_table.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void addColumn() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/add_column.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void addColumns() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/add_columns.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void dropColumns() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/drop_column.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void all() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/all.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void roleName() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/role_name.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void modifyColumns() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/modify_column.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void createIndex() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/create_index.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    @Test
    public void dropIndex() {
        SQLTransfer transfer = new MySQLTransfer();
        String sql = ScriptReader.read("/MySQL/drop_index.sql");
        IScript script = transfer.transform(sql, DBProduct.DM8);
        PrintUtil.println(script);
    }

    
}