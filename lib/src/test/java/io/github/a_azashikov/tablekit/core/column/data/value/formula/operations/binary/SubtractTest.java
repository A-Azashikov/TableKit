package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class SubtractTest {

    @Test
    void shouldReturnLeftAndRight_whenConstructed() {
        var left = new Val<>(5);
        var right = new Val<>(3);
        var sub = new Subtract(left, right);
        assertEquals(left, sub.getLeft());
        assertEquals(right, sub.getRight());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var sub = new Subtract(new Val<>(5), new Val<>(3));
        var result = sub.accept(new FormulaBaseVisitor<String>() {
            @Override
            public String visit(BinaryOperation operation) { return "visited"; }
            @Override
            public String visit(UnaryOperation operation) { return null; }
            @Override
            public String visit(TernaryOperation operation) { return null; }
            @Override
            public String visit(AggregateFunction aggregation) { return null; }
        });
        assertEquals("visited", result);
    }
}