package io.github.a_azashikov.tablekit.core.column.data.value;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumericValueTest {

    @Test
    void shouldReturnValue_whenConstructedWithInteger() {
        var value = new NumericValue(42);
        assertEquals(42, value.getValue());
    }

    @Test
    void shouldReturnValue_whenConstructedWithDouble() {
        var value = new NumericValue(3.14);
        assertEquals(3.14, value.getValue());
    }

    @Test
    void shouldReturnValue_whenConstructedWithLong() {
        var value = new NumericValue(100L);
        assertEquals(100L, value.getValue());
    }
}