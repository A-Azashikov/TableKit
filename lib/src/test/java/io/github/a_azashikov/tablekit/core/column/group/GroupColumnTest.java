package io.github.a_azashikov.tablekit.core.column.group;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

class GroupColumnTest {

    @Test
    void shouldReturnDefaultValues_whenConstructedWithNoArgs() {
        var column = new GroupColumn<String>();
        assertEquals("", column.getTitle());
        assertNull(column.getHeaderStyle());
        assertTrue(column.getChildren().isEmpty());
    }

    @Test
    void shouldReturnTitle_whenSet() {
        var column = new GroupColumn<String>();
        column.setTitle("Group1");
        assertEquals("Group1", column.getTitle());
    }

    @Test
    void shouldReturnHeaderStyle_whenSet() {
        var column = new GroupColumn<String>();
        var style = new CellStyleDefinition();
        column.setHeaderStyle(style);
        assertEquals(style, column.getHeaderStyle());
    }

    @Test
    void shouldReturnWidth_whenNoChildren() {
        var column = new GroupColumn<String>();
        assertEquals(1, column.getWidth());
    }

    @Test
    void shouldReturnSumOfChildrenWidth_whenHasChildren() {
        var column = new GroupColumn<String>();
        var child1 = new DataColumn<String>();
        var child2 = new DataColumn<String>();
        column.addChild(child1);
        column.addChild(child2);
        assertEquals(2, column.getWidth());
    }

    @Test
    void shouldAddChild_whenAddChildCalled() {
        var column = new GroupColumn<String>();
        var child = new DataColumn<String>();
        column.addChild(child);
        assertEquals(1, column.getChildren().size());
        assertSame(child, column.getChildren().get(0));
    }
}