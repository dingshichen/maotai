package com.enhe.sql.generate;

import com.enhe.sql.DBProduct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ding.shichen
 */
public class SQLGeneratorManager {

    private static final Map<DBProduct, SQLGenerator> GENERATORS = new HashMap<>();

    static {
        GENERATORS.put(DBProduct.DM8, new DM8Generator());
        GENERATORS.put(DBProduct.GaussDB, new GaussDBGenerator());
    }

    public static SQLGenerator getInstance(DBProduct product) {
        SQLGenerator generator = GENERATORS.get(product);
        if (generator == null) {
            throw new RuntimeException(String.format("获取不到 %s 的 SQL 生成器", product.name()));
        }
        return generator;
    }
}
