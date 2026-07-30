package io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

import java.util.List;

class SumTest {

    @Test
    void shouldReturnArguments_whenConstructedWithVarargs() {
        var arg1 = new Val<>(1);
        var arg2 = new Val<>(2);
        var sum = new Sum(arg1, arg2);
        assertEquals(2, sum.getArguments().size());
        assertTrue(sum.getArguments().containsAll(List.of(arg1, arg2)));
    }

    @Test
    void shouldReturnArguments_whenConstructedWithCollection() {
        var arg1 = new Val<>(1);
        var arg2 = new Val<>(2);
        var sum = new Sum(List.of(arg1, arg2));
        assertEquals(2, sum.getArguments().size());
        assertTrue(sum.getArguments().containsAll(List.of(arg1, arg2)));
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var sum = new Sum(new Val<>(1), new Val<>(2));
        var result = sum.accept(new FormulaBaseVisitor<String>() {
            @Override
            public String visit(AggregateFunction aggregation) { return "visited"; }
            @Override
            public String visit(UnaryOperation operation) { return null; }
            @Override
            public String visit(BinaryOperation operation) { return null; }
            @Override
            public String visit(TernaryOperation operation) { return null; }
        });
        assertEquals("visited", result);
    }
}