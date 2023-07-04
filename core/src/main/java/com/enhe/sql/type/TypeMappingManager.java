package com.enhe.sql.type;

import com.enhe.sql.DBProduct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ding.shichen
 */
public class TypeMappingManager {

    private static final Map<DBProduct, ITypeMapping> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put(DBProduct.MySQL, new MySQLTypeMapping());
        MAPPINGS.put(DBProduct.DM8, new DM8TypeMapping());
        MAPPINGS.put(DBProduct.GaussDB, new GaussDBTypeMapping());
    }

    public static ITypeMapping getInstance(DBProduct product) {
        ITypeMapping mapping = MAPPINGS.get(product);
        if (mapping == null) {
            throw new RuntimeException("获取不到 %s 的类型映射配置");
        }
        return mapping;
    }
}
