package io.github.a_azashikov.tablekit.core.renderer;

import io.github.a_azashikov.tablekit.core.Table;

public interface Renderer {
    public <T> void render(Table<T> table);
}
