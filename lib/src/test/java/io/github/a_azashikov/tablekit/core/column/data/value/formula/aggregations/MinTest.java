package io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

import java.util.List;

class MinTest {

    @Test
    void shouldReturnArguments_whenConstructedWithVarargs() {
        var arg1 = new Val<>(1);
        var arg2 = new Val<>(2);
        var min = new Min(arg1, arg2);
        assertEquals(2, min.getArguments().size());
        assertTrue(min.getArguments().containsAll(List.of(arg1, arg2)));
    }

    @Test
    void shouldReturnArguments_whenConstructedWithCollection() {
        var arg1 = new Val<>(1);
        var arg2 = new Val<>(2);
        var min = new Min(List.of(arg1, arg2));
        assertEquals(2, min.getArguments().size());
        assertTrue(min.getArguments().containsAll(List.of(arg1, arg2)));
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var min = new Min(new Val<>(1), new Val<>(2));
        var result = min.accept(new FormulaBaseVisitor<String>() {
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