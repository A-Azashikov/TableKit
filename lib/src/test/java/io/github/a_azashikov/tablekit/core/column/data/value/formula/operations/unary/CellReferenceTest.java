package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

class CellReferenceTest {

    @Test
    void shouldReturnColumnKey_whenConstructedWithColumnKey() {
        var ref = new CellReference("col1", null);
        assertEquals("col1", ref.getColumnKey());
    }

    @Test
    void shouldReturnRowKey_whenConstructedWithRowKey() {
        var ref = new CellReference("col1", "row1");
        assertEquals("row1", ref.getRowKey());
    }

    @Test
    void shouldReturnNullRowKey_whenConstructedWithoutRowKey() {
        var ref = new CellReference("col1", null);
        assertNull(ref.getRowKey());
    }

    @Test
    void shouldCallVisitorVisit_whenAcceptCalled() {
        var ref = new CellReference("col1", "row1");
        var result = ref.accept(new FormulaBaseVisitor<String>() {
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