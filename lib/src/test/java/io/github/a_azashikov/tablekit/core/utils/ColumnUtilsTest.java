package io.github.a_azashikov.tablekit.core.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.configurations.HeadStyle;
import io.github.a_azashikov.tablekit.core.column.configurations.Name;
import io.github.a_azashikov.tablekit.core.column.data.DataColumn;

class ColumnUtilsTest {

    static class TestRow {
        @Name("Name")
        public String name = "John";

        @Name("Age")
        @HeadStyle(bold = true, backgroundColor = "#FF0000")
        public int age = 30;

        public String ignored = "ignored";
    }

    @Test
    void shouldGenerateColumnsFromAnnotatedFields() {
        var columns = ColumnUtils.generateColumns(TestRow.class).toList();
        assertEquals(2, columns.size());
    }

    @Test
    void shouldSetTitleFromNameAnnotation() {
        var columns = ColumnUtils.generateColumns(TestRow.class).toList();
        assertEquals("Name", columns.get(0).getTitle());
        assertEquals("Age", columns.get(1).getTitle());
    }

    @Test
    void shouldSetHeaderStyleFromHeadStyleAnnotation() {
        var columns = ColumnUtils.generateColumns(TestRow.class).toList();
        var ageColumn = (DataColumn<?>) columns.get(1);
        var style = ageColumn.getHeaderStyle();
        assertNotNull(style);
        assertTrue(style.isBold());
        assertEquals("#FF0000", style.getBackgroundColor());
    }

    @Test
    void shouldReturnEmptyStream_whenNoAnnotatedFields() {
        class NoAnnotations {
            public String field1 = "test";
        }
        var columns = ColumnUtils.generateColumns(NoAnnotations.class).toList();
        assertTrue(columns.isEmpty());
    }
}