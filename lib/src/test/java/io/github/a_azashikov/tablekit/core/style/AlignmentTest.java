package io.github.a_azashikov.tablekit.core.style;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

class AlignmentTest {

    @Test
    void shouldHaveAllEnumValues() {
        assertEquals(9, Alignment.values().length);
    }

    @Test
    void shouldReturnCorrectAlignments_forCenter() {
        assertEquals(HorizontalAlignment.CENTER, Alignment.Center.getHorizontalAlignment());
        assertEquals(VerticalAlignment.CENTER, Alignment.Center.getVerticalAlignment());
    }

    @Test
    void shouldReturnCorrectAlignments_forTopLeft() {
        assertEquals(HorizontalAlignment.LEFT, Alignment.TopLeft.getHorizontalAlignment());
        assertEquals(VerticalAlignment.TOP, Alignment.TopLeft.getVerticalAlignment());
    }

    @Test
    void shouldReturnCorrectAlignments_forBottomRight() {
        assertEquals(HorizontalAlignment.RIGHT, Alignment.BottomRight.getHorizontalAlignment());
        assertEquals(VerticalAlignment.BOTTOM, Alignment.BottomRight.getVerticalAlignment());
    }
}