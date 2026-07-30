package io.github.a_azashikov.tablekit.core.column.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

class DataColumnTest {

    @Test
    void shouldReturnDefaultValues_whenConstructedWithNoArgs() {
        var column = new DataColumn<String>();
        assertEquals("", column.getTitle());
        assertEquals("", column.getKey());
        assertNull(column.getValue("test"));
        assertNull(column.getCellStyle("test", 0));
        assertNull(column.getHeaderStyle());
        assertEquals(1, column.getWidth());
        assertNull(column.getSize());
    }

    @Test
    void shouldReturnTitle_whenSet() {
        var column = new DataColumn<String>();
        column.setTitle("Name");
        assertEquals("Name", column.getTitle());
    }

    @Test
    void shouldReturnKey_whenSet() {
        var column = new DataColumn<String>();
        column.setKey("name");
        assertEquals("name", column.getKey());
    }

    @Test
    void shouldReturnValue_whenValueGetterSet() {
        var column = new DataColumn<String>();
        column.setValueGetter(StringValue::new);
        assertEquals("test", ((StringValue) column.getValue("test")).getValue());
    }

    @Test
    void shouldReturnWidth_whenCalled() {
        var column = new DataColumn<String>();
        assertEquals(1, column.getWidth());
    }

    @Test
    void shouldReturnHeaderStyle_whenSet() {
        var column = new DataColumn<String>();
        var style = new CellStyleDefinition();
        column.setHeaderStyle(style);
        assertEquals(style, column.getHeaderStyle());
    }

    @Test
    void shouldReturnCellStyle_whenStyleGetterSet() {
        var column = new DataColumn<String>();
        var style = new CellStyleDefinition();
        column.setStyleGetter((row, index) -> style);
        assertEquals(style, column.getCellStyle("test", 0));
    }

    @Test
    void shouldReturnSize_whenSet() {
        var column = new DataColumn<String>();
        column.setSize(100);
        assertEquals(100, column.getSize());
    }
}