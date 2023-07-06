package com.enhe.sql.model.impl;

/**
 * @author ding.shichen
 */
public class DefaultBitExpression extends DefaultExpression {

    public DefaultBitExpression(String text) {
        super(text);
    }

    @Override
    public String getText() {
        String text = super.getText();
        switch (text) {
            case "b'0'" :
                return "0";
            case "b'1'" :
                return "1";
            default:
                return text;
        }
    }
}
