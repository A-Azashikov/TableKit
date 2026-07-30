package io.github.a_azashikov.tablekit.core.column.collapsible;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.DataColumn;

class CustomizeCollapsibleContextTest {

    @Test
    void shouldAddChild_whenAddChildCalled() {
        var column = new CollapsibleColumn<String>();
        var ctx = new CustomizeCollapsibleContext<>(column);
        var child = new DataColumn<String>();
        ctx.addChild(child);
        assertEquals(1, column.getChildren().size());
        assertSame(child, column.getChildren().get(0));
    }
}