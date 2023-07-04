package com.enhe.sql.parse;

import com.enhe.sql.lang.mysql.MySqlLexer;
import com.enhe.sql.lang.mysql.MySqlParser;
import com.enhe.sql.lang.mysql.visitor.MySqlSqlParserVisitor;
import com.enhe.sql.model.ParseResult;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author ding.shichen
 */
public class MySQLParseAdapter implements SQLParseAdapter {

    @Override
    public ParseResult parse(String sql) {
        MySqlLexer lexer = new MySqlLexer(CharStreams.fromString(sql));
        MySqlParser parser = new MySqlParser(new CommonTokenStream(lexer));

        MySqlSqlParserVisitor visitor = new MySqlSqlParserVisitor();
        parser.sqlStatements().accept(visitor);
        return new ParseResult(visitor.getCreateTables(), visitor.getAlterTables(), visitor.getDropIndex(), visitor.getDropTables());
    }

}
