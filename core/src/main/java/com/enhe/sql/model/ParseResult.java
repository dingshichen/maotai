package com.enhe.sql.model;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author ding.shichen
 */
public class ParseResult {

    private List<ITable> createTables;

    private List<IAlterTable> alterTables;

    private List<ITableIndex> dropIndex;

    private List<IName> dropTables;

    public ParseResult(List<ITable> createTables, List<IAlterTable> alterTables, List<ITableIndex> dropIndex, List<IName> dropTables) {
        this.createTables = createTables;
        this.alterTables = alterTables;
        this.dropIndex = dropIndex;
        this.dropTables = dropTables;
    }

    public @Nullable List<ITable> getCreateTables() {
        return createTables;
    }

    public @Nullable List<IAlterTable> getAlterTables() {
        return alterTables;
    }

    public @Nullable List<ITableIndex> getDropIndex() {
        return dropIndex;
    }

    public @Nullable List<IName> getDropTables() {
        return dropTables;
    }

    public List<Ordered> getDDLAsOrdered() {
        List<Ordered> ddls = new ArrayList<>();
        ddls.addAll(createTables);
        ddls.addAll(alterTables);
        ddls.addAll(dropIndex);
        ddls.addAll(dropTables);
        ddls.sort(Comparator.comparingInt(Ordered::getOrder));
        return ddls;
    }
}
