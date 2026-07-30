package io.github.a_azashikov.tablekit.core.style;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellStyleDefinitionTest {

    @Test
    void shouldReturnDefaultValues_whenConstructedWithNoArgs() {
        var def = new CellStyleDefinition();
        assertEquals("Arial", def.getFontName());
        assertEquals(11, def.getFontSize());
        assertFalse(def.isBold());
        assertFalse(def.isItalic());
        assertFalse(def.isUnderline());
        assertEquals("#000000", def.getFontColor());
        assertEquals("#FFFFFF", def.getBackgroundColor());
        assertEquals(Fill.Solid, def.getFill());
        assertEquals(Alignment.Center, def.getAlignment());
        assertEquals(Border.None, def.getBorder());
        assertEquals("#000000", def.getBorderColor());
        assertNull(def.getDataFormat());
    }

    @Test
    void shouldSetAndGetFontName() {
        var def = new CellStyleDefinition();
        def.setFontName("Times New Roman");
        assertEquals("Times New Roman", def.getFontName());
    }

    @Test
    void shouldSetAndGetFontSize() {
        var def = new CellStyleDefinition();
        def.setFontSize((short) 14);
        assertEquals(14, def.getFontSize());
    }

    @Test
    void shouldSetAndGetBold() {
        var def = new CellStyleDefinition();
        def.setBold(true);
        assertTrue(def.isBold());
    }

    @Test
    void shouldSetAndGetItalic() {
        var def = new CellStyleDefinition();
        def.setItalic(true);
        assertTrue(def.isItalic());
    }

    @Test
    void shouldSetAndGetUnderline() {
        var def = new CellStyleDefinition();
        def.setUnderline(true);
        assertTrue(def.isUnderline());
    }

    @Test
    void shouldSetAndGetFontColor() {
        var def = new CellStyleDefinition();
        def.setFontColor("#FF0000");
        assertEquals("#FF0000", def.getFontColor());
    }

    @Test
    void shouldSetAndGetBackgroundColor() {
        var def = new CellStyleDefinition();
        def.setBackgroundColor("#00FF00");
        assertEquals("#00FF00", def.getBackgroundColor());
    }

    @Test
    void shouldSetAndGetFill() {
        var def = new CellStyleDefinition();
        def.setFill(Fill.Solid);
        assertEquals(Fill.Solid, def.getFill());
    }

    @Test
    void shouldSetAndGetAlignment() {
        var def = new CellStyleDefinition();
        def.setAlignment(Alignment.Left);
        assertEquals(Alignment.Left, def.getAlignment());
    }

    @Test
    void shouldSetAndGetBorder() {
        var def = new CellStyleDefinition();
        def.setBorder(Border.Thin);
        assertEquals(Border.Thin, def.getBorder());
    }

    @Test
    void shouldSetAndGetBorderColor() {
        var def = new CellStyleDefinition();
        def.setBorderColor("#0000FF");
        assertEquals("#0000FF", def.getBorderColor());
    }

    @Test
    void shouldSetAndGetDataFormat() {
        var def = new CellStyleDefinition();
        def.setDataFormat("#,##0.00");
        assertEquals("#,##0.00", def.getDataFormat());
    }
}