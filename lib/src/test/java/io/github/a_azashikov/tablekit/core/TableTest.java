package io.github.a_azashikov.tablekit.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;

class TableTest {

    @Test
    void shouldReturnName_whenConstructed() {
        var table = new Table<String>("TestTable", row -> row.toString());
        assertEquals("TestTable", table.getName());
    }

    @Test
    void shouldReturnRowKeyGetter_whenConstructed() {
        var table = new Table<String>("Test", row -> "key:" + row);
        assertEquals("key:test", table.getRowKeyGetter().apply("test"));
    }

    @Test
    void shouldReturnDefaultColumnSize_whenConstructedWithSize() {
        var table = new Table<String>("Test", row -> row.toString(), 100);
        assertEquals(100, table.getDefaultColumnSize());
    }

    @Test
    void shouldReturnNullDefaultColumnSize_whenConstructedWithoutSize() {
        var table = new Table<String>("Test", row -> row.toString());
        assertNull(table.getDefaultColumnSize());
    }

    @Test
    void shouldAddColumn_whenAddColumnCalled() {
        var table = new Table<String>("Test", row -> row.toString());
        var column = new DataColumn<String>();
        table.addColumn(column);
        assertEquals(1, table.getColumns().size());
        assertSame(column, table.getColumns().get(0));
    }

    @Test
    void shouldAddRow_whenAddRowCalled() {
        var table = new Table<String>("Test", row -> row.toString());
        table.addRow("row1");
        assertEquals(1, table.getRows().size());
        assertEquals("row1", table.getRows().get(0));
    }

    @Test
    void shouldReturnEmptyColumns_whenNoColumnsAdded() {
        var table = new Table<String>("Test", row -> row.toString());
        assertTrue(table.getColumns().isEmpty());
    }

    @Test
    void shouldReturnEmptyRows_whenNoRowsAdded() {
        var table = new Table<String>("Test", row -> row.toString());
        assertTrue(table.getRows().isEmpty());
    }

    @Test
    void shouldCreateTableFromList_whenFromCalled() {
        var rows = List.of("row1", "row2");
        var builder = Table.from(rows);
        assertNotNull(builder);
        var table = builder.build();
        assertEquals(2, table.getRows().size());
        assertEquals("row1", table.getRows().get(0));
        assertEquals("row2", table.getRows().get(1));
    }

    @Test
    void shouldCreateBuilder_whenOfCalled() {
        var builder = Table.of(String.class);
        assertNotNull(builder);
        var table = builder.build();
        assertEquals("", table.getName());
        assertTrue(table.getColumns().isEmpty());
        assertTrue(table.getRows().isEmpty());
    }
}
