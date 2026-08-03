package io.github.a_azashikov.tablekit.core.column.group;

import io.github.a_azashikov.tablekit.core.column.ChildrenContextBase;
import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

public class CustomizeGroupContext<T, K> extends ChildrenContextBase<T, K, CustomizeGroupContext<T, K>> {
    private final GroupColumn<T> column;

    public CustomizeGroupContext(GroupColumn<T> column) {
        this.column = column;
    }

    public CustomizeGroupContext<T, K> style(CellStyleDefinition headerStyle) {
        this.column.setHeaderStyle(headerStyle);

        return this;
    }
    
    public CustomizeGroupContext<T, K> title(String title) {
        this.column.setTitle(title);

        return this;
    }
    
    @Override
    public CustomizeGroupContext<T, K> addChild(Column<T> child) {
        this.column.addChild(child);

        return this;
    }

}
