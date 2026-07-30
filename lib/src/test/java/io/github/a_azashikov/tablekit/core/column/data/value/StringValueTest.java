package io.github.a_azashikov.tablekit.core.column.data.value;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void shouldReturnValue_whenConstructedWithString() {
        var value = new StringValue("test");
        assertEquals("test", value.getValue());
    }

    @Test
    void shouldReturnEmptyString_whenConstructedWithEmptyString() {
        var value = new StringValue("");
        assertEquals("", value.getValue());
    }

    @Test
    void shouldReturnNull_whenConstructedWithNull() {
        var value = new StringValue(null);
        assertNull(value.getValue());
    }
}