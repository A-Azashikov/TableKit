package io.github.a_azashikov.tablekit.core.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import io.github.a_azashikov.tablekit.core.column.data.value.DateValue;
import io.github.a_azashikov.tablekit.core.column.data.value.NumericValue;
import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;

class ValueUtilsTest {

    @Test
    void shouldReturnStringValue_whenInputIsString() {
        var result = ValueUtils.mapValue("hello");
        assertInstanceOf(StringValue.class, result);
        assertEquals("hello", ((StringValue) result).getValue());
    }

    @Test
    void shouldReturnNumericValue_whenInputIsInteger() {
        var result = ValueUtils.mapValue(42);
        assertInstanceOf(NumericValue.class, result);
        assertEquals(42.0, ((NumericValue) result).getValue());
    }

    @Test
    void shouldReturnNumericValue_whenInputIsDouble() {
        var result = ValueUtils.mapValue(3.14);
        assertInstanceOf(NumericValue.class, result);
        assertEquals(3.14, ((NumericValue) result).getValue());
    }

    @Test
    void shouldReturnDateValue_whenInputIsDate() {
        var date = new Date();
        var result = ValueUtils.mapValue(date);
        assertInstanceOf(DateValue.class, result);
        assertEquals(date, ((DateValue) result).getValue());
    }

    @Test
    void shouldReturnFormulaValue_whenInputIsFormula() {
        var formula = new Val<>("test");
        var result = ValueUtils.mapValue(formula);
        assertInstanceOf(FormulaValue.class, result);
        assertEquals(formula, ((FormulaValue) result).getFormula());
    }

    @Test
    void shouldReturnValueAsIs_whenInputIsValue() {
        var stringValue = new StringValue("test");
        var result = ValueUtils.mapValue(stringValue);
        assertSame(stringValue, result);
    }

    @Test
    void shouldThrowException_whenInputIsNull() {
        assertThrows(NullPointerException.class, () -> ValueUtils.mapValue(null));
    }
}