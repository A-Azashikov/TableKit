package io.github.a_azashikov.tablekit.core.column;

import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

public interface Column<T> {
    String getTitle();

    CellStyleDefinition getHeaderStyle();

    int getWidth();
}
