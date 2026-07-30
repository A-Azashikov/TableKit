package io.github.a_azashikov.tablekit.core.column.data.value;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class DateValueTest {

    @Test
    void shouldReturnValue_whenConstructedWithDate() {
        var date = new Date();
        var value = new DateValue(date);
        assertEquals(date, value.getValue());
    }

    @Test
    void shouldReturnNull_whenConstructedWithNull() {
        var value = new DateValue(null);
        assertNull(value.getValue());
    }
}