package io.github.a_azashikov.tablekit.core.column.collapsible;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;

class CollapsibleColumnTest {

    @Test
    void shouldReturnNullTitle_whenCalled() {
        var column = new CollapsibleColumn<String>();
        assertNull(column.getTitle());
    }

    @Test
    void shouldReturnNullHeaderStyle_whenCalled() {
        var column = new CollapsibleColumn<String>();
        assertNull(column.getHeaderStyle());
    }

    @Test
    void shouldReturnWidth_whenNoChildren() {
        var column = new CollapsibleColumn<String>();
        assertEquals(1, column.getWidth());
    }

    @Test
    void shouldReturnSumOfChildrenWidth_whenHasChildren() {
        var column = new CollapsibleColumn<String>();
        var child1 = new DataColumn<String>();
        var child2 = new DataColumn<String>();
        column.addChild(child1);
        column.addChild(child2);
        assertEquals(2, column.getWidth());
    }

    @Test
    void shouldAddChild_whenAddChildCalled() {
        var column = new CollapsibleColumn<String>();
        var child = new DataColumn<String>();
        column.addChild(child);
        assertEquals(1, column.getChildren().size());
        assertSame(child, column.getChildren().get(0));
    }
}