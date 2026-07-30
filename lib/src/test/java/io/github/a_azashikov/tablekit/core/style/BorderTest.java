package io.github.a_azashikov.tablekit.core.style;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.ss.usermodel.BorderStyle;

class BorderTest {

    @Test
    void shouldHaveAllEnumValues() {
        assertEquals(4, Border.values().length);
    }

    @Test
    void shouldReturnNoneBorderStyle() {
        assertEquals(BorderStyle.NONE, Border.None.getBorderStyle());
    }

    @Test
    void shouldReturnThinBorderStyle() {
        assertEquals(BorderStyle.THIN, Border.Thin.getBorderStyle());
    }

    @Test
    void shouldReturnMediumBorderStyle() {
        assertEquals(BorderStyle.MEDIUM, Border.Medium.getBorderStyle());
    }

    @Test
    void shouldReturnDashedBorderStyle() {
        assertEquals(BorderStyle.DASHED, Border.Dashed.getBorderStyle());
    }
}