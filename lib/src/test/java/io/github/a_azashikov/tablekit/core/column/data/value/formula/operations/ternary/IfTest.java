package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class IfTest {

    @Test
    void shouldReturnLeftMiddleRight_whenConstructed() {
        var left = new Val<>(true);
        var middle = new Val<>("yes");
        var right = new Val<>("no");
        var ifOp = new If(left, middle, right);
        assertEquals(left, ifOp.getLeft());
        assertEquals(middle, ifOp.getMiddle());
        assertEquals(right, ifOp.getRight());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var ifOp = new If(new Val<>(true), new Val<>("yes"), new Val<>("no"));
        var result = ifOp.accept(new FormulaBaseVisitor<String>() {
            @Override
            public String visit(TernaryOperation operation) { return "visited"; }
            @Override
            public String visit(UnaryOperation operation) { return null; }
            @Override
            public String visit(BinaryOperation operation) { return null; }
            @Override
            public String visit(AggregateFunction aggregation) { return null; }
        });
        assertEquals("visited", result);
    }
}