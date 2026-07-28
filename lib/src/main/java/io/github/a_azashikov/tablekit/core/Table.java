package io.github.a_azashikov.tablekit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.Column;

public class Table<T> {
    private final List<Column<T>> columns = new ArrayList<>();
    private final List<T> rows = new ArrayList<>();
    private final String name;
    private final Function<T, String> rowKeyGetter;

    public Table(String name, Function<T, String> rowKeyGetter) {
        this.name = name;
        this.rowKeyGetter = rowKeyGetter;
    }

    public String getName() {
        return name;
    }

    public List<Column<T>> getColumns() {
        return columns;
    }
    
    public List<T> getRows() {
        return rows;
    }

    public void addColumn(Column<T> c) {
        this.columns.add(c);
    }

    public void addRow(T r) {
        this.rows.add(r);
    }

    public Function<T, String> getRowKeyGetter() {
        return rowKeyGetter;
    }

    public static <T> TableBuilder<T> of(Class<T> row) {
        var builder = new TableBuilder<T>(row);

        return builder;
    }

    @SuppressWarnings("unchecked")
    public static <T> TableBuilder<T> from(List<T> rows) {
        var row = rows.getFirst();
        var builder = new TableBuilder<T>((Class<T>) row.getClass());

        builder.addRows(rows);

        return builder;
    }
}
