package com.enhe.sql.model;

import java.util.List;

/**
 * @author ding.shichen
 */
public interface IScript {

    List<String> getTexts();

    void addText(String text);

    void addScript(IScript script);

    void addScript(List<IScript> scripts);
}
