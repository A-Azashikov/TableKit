package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class AddTest {

    @Test
    void shouldReturnLeftAndRight_whenConstructed() {
        var left = new Val<>(1);
        var right = new Val<>(2);
        var add = new Add(left, right);
        assertEquals(left, add.getLeft());
        assertEquals(right, add.getRight());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var add = new Add(new Val<>(1), new Val<>(2));
        var result = add.accept(new FormulaBaseVisitor<String>() {
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