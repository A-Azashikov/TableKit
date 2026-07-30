package io.github.a_azashikov.tablekit.core.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.configurations.HeadStyle;
import io.github.a_azashikov.tablekit.core.style.Alignment;
import io.github.a_azashikov.tablekit.core.style.Border;
import io.github.a_azashikov.tablekit.core.style.Fill;

class StyleUtilsTest {

    @Test
    void shouldReturnDefaultStyle_whenAnnotationIsNull() {
        var result = StyleUtils.map(null);
        assertEquals("Arial", result.getFontName());
        assertEquals(11, result.getFontSize());
        assertFalse(result.isBold());
        assertEquals(Alignment.Center, result.getAlignment());
    }

    @Test
    void shouldMapAnnotationToStyleDefinition() {
        var annotation = new HeadStyle() {
            @Override public Class<? extends java.lang.annotation.Annotation> annotationType() { return HeadStyle.class; }
            @Override public String fontName() { return "Times New Roman"; }
            @Override public short fontSize() { return 14; }
            @Override public boolean bold() { return true; }
            @Override public boolean italic() { return true; }
            @Override public boolean underline() { return true; }
            @Override public String fontColor() { return "#FF0000"; }
            @Override public String backgroundColor() { return "#00FF00"; }
            @Override public Fill fill() { return Fill.Solid; }
            @Override public Alignment alignment() { return Alignment.Left; }
            @Override public Border border() { return Border.Thin; }
            @Override public String borderColor() { return "#0000FF"; }
            @Override public String dataFormat() { return "#,##0.00"; }
        };

        var result = StyleUtils.map(annotation);
        assertEquals("Times New Roman", result.getFontName());
        assertEquals(14, result.getFontSize());
        assertTrue(result.isBold());
        assertTrue(result.isItalic());
        assertTrue(result.isUnderline());
        assertEquals("#FF0000", result.getFontColor());
        assertEquals("#00FF00", result.getBackgroundColor());
        assertEquals(Fill.Solid, result.getFill());
        assertEquals(Alignment.Left, result.getAlignment());
        assertEquals(Border.Thin, result.getBorder());
        assertEquals("#0000FF", result.getBorderColor());
        assertEquals("#,##0.00", result.getDataFormat());
    }
}