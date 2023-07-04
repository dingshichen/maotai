package com.enhe.sql;

import com.enhe.sql.model.IScript;
import com.enhe.sql.util.PrintUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ding.shichen
 */
public class FileTransferTest {

    @Test
    public void transToFile() {
//        File file = new File("/Users/dingshichen/IdeaProjects/enhe/ef-script/dbinstaller/src/main/resources/db/mysql/patch");
        File file = new File("/Users/dingshichen/IdeaProjects/enhe/ef-script/dbinstaller/src/main/resources/db/mysql/table");
        toFile(file);
    }
    private void toFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.stream(files).sorted(Comparator.comparing(File::getPath)).forEach(this::toFile);
            }
        } else if (file.getPath().endsWith(".sql") && file.getPath().contains("/table/")) {
            try {
                String text = Files.readString(file.toPath());
                SQLTransfer transfer = new MySQLTransfer();
                IScript script = transfer.transform(text, DBProduct.DM8);
                System.out.printf("-- %s%n", file.getPath());
                PrintUtil.println(script);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
