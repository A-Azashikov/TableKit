package io.github.a_azashikov.tablekit.core.column.collapsible;

import java.util.ArrayList;
import java.util.List;

import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;


public class CollapsibleColumn<T> implements Column<T> {
    private final List<Column<T>> children = new ArrayList<>();

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public CellStyleDefinition getHeaderStyle() {
        return null;
    }

    @Override
    public int getWidth() {
        int childrenWidth = 0;

        for (Column<T> column : children) {
            childrenWidth += column.getWidth();
        }

        return Math.max(childrenWidth, 1);
    }

    public List<Column<T>> getChildren() {
        return children;
    }

    public void addChild(Column<T> child) {
        this.children.add(child);
    }

}
