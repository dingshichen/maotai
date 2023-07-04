package com.enhe.sql.util;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author ding.shichen
 */
public abstract class ScriptReader {

    public static String read(String path) {
        try {
            return Files.readString(Path.of(URI.create("file:/Users/dingshichen/IdeaProjects/enhe/sql-parser/src/test/resources" + path)));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
