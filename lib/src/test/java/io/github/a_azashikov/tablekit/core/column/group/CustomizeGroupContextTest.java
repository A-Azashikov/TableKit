package io.github.a_azashikov.tablekit.core.column.group;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

class CustomizeGroupContextTest {

    @Test
    void shouldSetTitle_whenTitleCalled() {
        var column = new GroupColumn<String>();
        var ctx = new CustomizeGroupContext<>(column);
        ctx.title("Group1");
        assertEquals("Group1", column.getTitle());
    }

    @Test
    void shouldSetHeaderStyle_whenStyleCalled() {
        var column = new GroupColumn<String>();
        var ctx = new CustomizeGroupContext<>(column);
        var style = new CellStyleDefinition();
        ctx.style(style);
        assertEquals(style, column.getHeaderStyle());
    }

    @Test
    void shouldAddChild_whenAddChildCalled() {
        var column = new GroupColumn<String>();
        var ctx = new CustomizeGroupContext<>(column);
        var child = new DataColumn<String>();
        ctx.addChild(child);
        assertEquals(1, column.getChildren().size());
        assertSame(child, column.getChildren().get(0));
    }
}