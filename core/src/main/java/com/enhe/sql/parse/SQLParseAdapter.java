package com.enhe.sql.parse;

import com.enhe.sql.model.ParseResult;

/**
 * @author ding.shichen
 */
public interface SQLParseAdapter {

    ParseResult parse(String sql);

}
