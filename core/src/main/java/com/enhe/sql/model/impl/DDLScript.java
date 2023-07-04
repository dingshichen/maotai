package com.enhe.sql.model.impl;

import com.enhe.sql.model.IScript;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ding.shichen
 */
public class DDLScript implements IScript {

    public List<String> texts;

    public DDLScript() {
        texts = new ArrayList<>();
    }

    @Override
    public List<String> getTexts() {
        return texts;
    }

    @Override
    public void addText(String text) {
        texts.add(text);
    }

    @Override
    public void addScript(IScript script) {
        texts.addAll(script.getTexts());
    }

    @Override
    public void addScript(List<IScript> scripts) {
        scripts.forEach(this::addScript);
    }

    @Override
    public String toString() {
        return String.join("\n", texts);
    }
}
