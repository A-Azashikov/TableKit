package io.github.a_azashikov.tablekit.core.column.collapsible;

import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.ChildrenContextBase;

public class CustomizeCollapsibleContext<T, K> extends ChildrenContextBase<T, K, CustomizeCollapsibleContext<T, K>> {
    private final CollapsibleColumn<T> column;

    public CustomizeCollapsibleContext(CollapsibleColumn<T> column) {
        this.column = column;
    }

    @Override
    public CustomizeCollapsibleContext<T, K> addChild(Column<T> child) {
        column.addChild(child);

        return this;
    }
}