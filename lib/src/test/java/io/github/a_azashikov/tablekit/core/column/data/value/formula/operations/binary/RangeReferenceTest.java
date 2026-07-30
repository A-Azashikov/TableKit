package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.CellReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class RangeReferenceTest {

    @Test
    void shouldReturnStartAndEnd_whenConstructedWithCellReferences() {
        var start = new CellReference("A", "1");
        var end = new CellReference("A", "10");
        var range = new RangeReference(start, end);
        assertEquals(start, range.getLeft());
        assertEquals(end, range.getRight());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var range = new RangeReference(new CellReference("A", "1"), new CellReference("A", "10"));
        var result = range.accept(new FormulaBaseVisitor<String>() {
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