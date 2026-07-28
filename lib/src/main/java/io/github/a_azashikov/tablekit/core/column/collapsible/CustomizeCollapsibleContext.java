package io.github.a_azashikov.tablekit.core.column.collapsible;

import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.ChildrenContextBase;

public class CustomizeCollapsibleContext<T> extends ChildrenContextBase<T, CustomizeCollapsibleContext<T>> {
    private final CollapsibleColumn<T> column;

    public CustomizeCollapsibleContext(CollapsibleColumn<T> column) {
        this.column = column;
    }

    @Override
    public CustomizeCollapsibleContext<T> addChild(Column<T> child) {
        column.addChild(child);

        return this;
    }
}