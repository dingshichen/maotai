package com.enhe.sql.util;

import com.enhe.sql.model.IScript;

/**
 * @author ding.shichen
 */
public abstract class PrintUtil {

    public static void println(IScript script) {
        script.getTexts().forEach(e -> {
            System.out.println(e);
            System.out.println();
        });
    }

}
