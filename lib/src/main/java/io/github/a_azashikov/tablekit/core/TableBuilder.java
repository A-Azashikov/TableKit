package io.github.a_azashikov.tablekit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.ChildrenContextBase;
import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.utils.ColumnUtils;

public final class TableBuilder<T, K> extends ChildrenContextBase<T, K, TableBuilder<T, K>> {
    private String name = "";
    private Integer defaultColumnSize = null;
    private Function<T, String> rowKeyGetter = row -> row.toString();
    private final List<Column<T>> columns = new ArrayList<>();
    private final List<T> rows = new ArrayList<>();
    private final Class<T> classz;

    public TableBuilder(Class<T> classz) {
        this.classz = classz;
    }

    public TableBuilder<T, K> name(String name) {
        this.name = name;

        return this;
    }

    public TableBuilder<T, K> defaultColumnSize(Integer size) {
        this.defaultColumnSize = size;

        return this;
    }

    public TableBuilder<T, K> rowKey(Function<T, String> rowKeyGetter) {
        this.rowKeyGetter = rowKeyGetter;

        return this;
    }

    public TableBuilder<T, K> addRow(T row) {
        this.rows.add(row);

        return this;
    }

    public TableBuilder<T, K> addRows(List<T> rows) {
        this.rows.addAll(rows);

        return this;
    }

    public TableBuilder<T, K> autoColumns() {
        ColumnUtils.generateColumns(classz).forEach(this.columns::add);

        return this;
    }

    @SuppressWarnings("unchecked")
    public <CK> TableBuilder<T, CK> withKeyType(Class<CK> keyClassz) {

        return (TableBuilder<T, CK>) this;
    }

    public Table<T> build() {
        Table<T> table = new Table<>(name, rowKeyGetter, defaultColumnSize);

        for (Column<T> column : columns) {
            table.addColumn(column);
        }

        for (T row : rows) {
            table.addRow(row);
        }

        return table;
    }

    @Override
    public TableBuilder<T, K> addChild(Column<T> child) {
        columns.add(child);

        return this;
    }
}