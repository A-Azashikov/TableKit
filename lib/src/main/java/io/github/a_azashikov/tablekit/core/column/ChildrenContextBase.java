package io.github.a_azashikov.tablekit.core.column;

import java.util.function.Consumer;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.collapsible.CollapsibleColumn;
import io.github.a_azashikov.tablekit.core.column.collapsible.CustomizeCollapsibleContext;
import io.github.a_azashikov.tablekit.core.column.data.CustomizeDataContext;
import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.column.group.CustomizeGroupContext;
import io.github.a_azashikov.tablekit.core.column.group.GroupColumn;

@SuppressWarnings("unchecked")
public abstract class ChildrenContextBase<T, K, Self extends ChildrenContextBase<T, K, Self>> {
    public Self column(Consumer<CustomizeDataContext<T, K>> customizer) {
        var column = new DataColumn<T>();
        customizer.accept(new CustomizeDataContext<T, K>(column));
        addChild(column);

        return (Self) this;
    }

    public Self column(String title, Function<T, Object> valueGetter) {
        return column(ctx -> ctx.title(title).value(valueGetter));
    }

    public Self column(K key, String title, Function<T, Object> valueGetter) {
        return column(ctx -> ctx.key(key).title(title).value(valueGetter));
    }

    public Self group(Consumer<CustomizeGroupContext<T, K>> customizer) {
        var group = new GroupColumn<T>();
        addChild(group);
        customizer.accept(new CustomizeGroupContext<>(group));

        return (Self) this;
    }

    public Self group(String title, Consumer<CustomizeGroupContext<T, K>> customizer) {
        return group(c -> customizer.accept(c.title(title)));
    }

    public Self collapsible(Consumer<CustomizeCollapsibleContext<T, K>> customizer) {
        var collapsible = new CollapsibleColumn<T>();
        customizer.accept(new CustomizeCollapsibleContext<>(collapsible));
        addChild(collapsible);

        return (Self) this;
    }

    public abstract Self addChild(Column<T> child);
}
