package io.github.a_azashikov.tablekit.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;

class TableBuilderTest {

    @Test
    void shouldBuildTableWithDefaultValues() {
        var table = new TableBuilder<String>().build();
        assertEquals("", table.getName());
        assertTrue(table.getColumns().isEmpty());
        assertTrue(table.getRows().isEmpty());
    }

    @Test
    void shouldSetName_whenNameCalled() {
        var table = new TableBuilder<String>()
            .name("TestTable")
            .build();
        assertEquals("TestTable", table.getName());
    }

    @Test
    void shouldSetDefaultColumnSize_whenDefaultColumnSizeCalled() {
        var table = new TableBuilder<String>()
            .defaultColumnSize(100)
            .build();
        assertEquals(100, table.getDefaultColumnSize());
    }

    @Test
    void shouldSetRowKey_whenRowKeyCalled() {
        var table = new TableBuilder<String>()
            .rowKey(row -> "key:" + row)
            .build();
        assertEquals("key:test", table.getRowKeyGetter().apply("test"));
    }

    @Test
    void shouldAddRow_whenAddRowCalled() {
        var table = new TableBuilder<String>()
            .addRow("row1")
            .addRow("row2")
            .build();
        assertEquals(2, table.getRows().size());
        assertEquals("row1", table.getRows().get(0));
        assertEquals("row2", table.getRows().get(1));
    }

    @Test
    void shouldAddRows_whenAddRowsCalled() {
        var table = new TableBuilder<String>()
            .addRows(List.of("row1", "row2"))
            .build();
        assertEquals(2, table.getRows().size());
    }

    @Test
    void shouldAddColumn_whenColumnCalled() {
        var table = new TableBuilder<String>()
            .column(ctx -> ctx.title("Name").value(row -> row))
            .build();
        assertEquals(1, table.getColumns().size());
        assertEquals("Name", table.getColumns().get(0).getTitle());
    }

    @Test
    void shouldAddColumnWithTitleAndValue_whenColumnShortcutCalled() {
        var table = new TableBuilder<String>()
            .column("Name", row -> row)
            .build();
        assertEquals(1, table.getColumns().size());
        assertEquals("Name", table.getColumns().get(0).getTitle());
    }

    @Test
    void shouldAddGroup_whenGroupCalled() {
        var table = new TableBuilder<String>()
            .group("Group1", g -> g
                .column("Name", row -> row)
            )
            .build();
        assertEquals(1, table.getColumns().size());
        assertEquals("Group1", table.getColumns().get(0).getTitle());
    }

    @Test
    void shouldAddCollapsible_whenCollapsibleCalled() {
        var table = new TableBuilder<String>()
            .collapsible(c -> c
                .column("Name", row -> row)
            )
            .build();
        assertEquals(1, table.getColumns().size());
    }

    @Test
    void shouldAddChild_whenAddChildCalled() {
        var table = new TableBuilder<String>()
            .addChild(new DataColumn<>())
            .build();
        assertEquals(1, table.getColumns().size());
    }
}