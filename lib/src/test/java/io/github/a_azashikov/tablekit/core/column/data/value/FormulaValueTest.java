package io.github.a_azashikov.tablekit.core.column.data.value;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;

class FormulaValueTest {

    @Test
    void shouldReturnFormula_whenConstructedWithFormula() {
        var formula = new Val<>("test");
        var value = new FormulaValue(formula);
        assertEquals(formula, value.getFormula());
    }

    @Test
    void shouldReturnNull_whenConstructedWithNull() {
        var value = new FormulaValue(null);
        assertNull(value.getFormula());
    }
}