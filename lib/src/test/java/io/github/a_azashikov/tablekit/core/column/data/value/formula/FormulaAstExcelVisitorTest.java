package io.github.a_azashikov.tablekit.core.column.data.value.formula;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.excel.FormulaAstExcelVisitor;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.CellReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Add;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Subtract;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Multiply;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Divide;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.RangeReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.If;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.Sum;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.Avg;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.Count;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.Min;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.Max;

class FormulaAstExcelVisitorTest {

    private final FormulaAstExcelVisitor visitor = new FormulaAstExcelVisitor();

    @Test
    void shouldConvertValToString() {
        var formula = new Val<>("42");
        assertEquals("42", formula.accept(visitor));
    }

    @Test
    void shouldConvertAddToString() {
        var formula = new Add(new Val<>(1), new Val<>(2));
        assertEquals("1+2", formula.accept(visitor));
    }

    @Test
    void shouldConvertSubtractToString() {
        var formula = new Subtract(new Val<>(5), new Val<>(3));
        assertEquals("5-3", formula.accept(visitor));
    }

    @Test
    void shouldConvertMultiplyToString() {
        var formula = new Multiply(new Val<>(4), new Val<>(5));
        assertEquals("4*5", formula.accept(visitor));
    }

    @Test
    void shouldConvertDivideToString() {
        var formula = new Divide(new Val<>(10), new Val<>(2));
        assertEquals("10/2", formula.accept(visitor));
    }

    @Test
    void shouldConvertRangeReferenceToString() {
        var formula = new RangeReference(new CellReference("A", "1"), new CellReference("A", "10"));
        assertEquals("TODO: Add get cell by keys:TODO: Add get cell by keys", formula.accept(visitor));
    }

    @Test
    void shouldConvertIfToString() {
        var formula = new If(new Val<>(true), new Val<>("yes"), new Val<>("no"));
        assertEquals("IF(true, yes, no)", formula.accept(visitor));
    }

    @Test
    void shouldConvertSumToString() {
        var formula = new Sum(new Val<>(1), new Val<>(2), new Val<>(3));
        assertEquals("SUM(1,2,3)", formula.accept(visitor));
    }

    @Test
    void shouldConvertAvgToString() {
        var formula = new Avg(new Val<>(1), new Val<>(2));
        assertEquals("AVG(1,2)", formula.accept(visitor));
    }

    @Test
    void shouldConvertCountToString() {
        var formula = new Count(new Val<>(1), new Val<>(2));
        assertEquals("COUNT(1,2)", formula.accept(visitor));
    }

    @Test
    void shouldConvertMinToString() {
        var formula = new Min(new Val<>(1), new Val<>(2));
        assertEquals("MIN(1,2)", formula.accept(visitor));
    }

    @Test
    void shouldConvertMaxToString() {
        var formula = new Max(new Val<>(1), new Val<>(2));
        assertEquals("MAX(1,2)", formula.accept(visitor));
    }

    @Test
    void shouldHandleNestedOperations() {
        // (1+2)*(3+4)
        var formula = new Multiply(
            new Add(new Val<>(1), new Val<>(2)),
            new Add(new Val<>(3), new Val<>(4))
        );
        assertEquals("1+2*3+4", formula.accept(visitor));
    }
}