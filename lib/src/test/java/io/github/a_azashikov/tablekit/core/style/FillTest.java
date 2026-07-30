package io.github.a_azashikov.tablekit.core.style;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.ss.usermodel.FillPatternType;

class FillTest {

    @Test
    void shouldHaveAllEnumValues() {
        assertEquals(1, Fill.values().length);
    }

    @Test
    void shouldReturnSolidFillPattern() {
        assertEquals(FillPatternType.SOLID_FOREGROUND, Fill.Solid.getFillPatternType());
    }
}