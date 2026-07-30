package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class ValTest {

    @Test
    void shouldReturnValue_whenConstructedWithString() {
        var val = new Val<>("hello");
        assertEquals("hello", val.getValue());
    }

    @Test
    void shouldReturnValue_whenConstructedWithInteger() {
        var val = new Val<>(42);
        assertEquals(42, val.getValue());
    }

    @Test
    void shouldReturnValue_whenConstructedWithNull() {
        var val = new Val<>(null);
        assertNull(val.getValue());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var val = new Val<>("test");
        var result = val.accept(new FormulaBaseVisitor<String>() {
            @Override
            public String visit(UnaryOperation operation) { return "visited"; }
            @Override
            public String visit(BinaryOperation operation) { return null; }
            @Override
            public String visit(TernaryOperation operation) { return null; }
            @Override
            public String visit(AggregateFunction aggregation) { return null; }
        });
        assertEquals("visited", result);
    }
}