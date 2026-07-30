package io.github.a_azashikov.tablekit.core.column.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.CellReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Add;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Subtract;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Multiply;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.Divide;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.RangeReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.If;

class FormulaContextTest {

    private final FormulaContext ctx = new FormulaContext();

    @Test
    void shouldCreateVal_whenValCalled() {
        var result = ctx.val("test");
        assertInstanceOf(Val.class, result);
        assertEquals("test", ((Val<?>) result).getValue());
    }

    @Test
    void shouldCreateCellReference_whenRefCalledWithColumnKey() {
        var result = ctx.ref("col1");
        assertInstanceOf(CellReference.class, result);
        assertEquals("col1", ((CellReference) result).getColumnKey());
        assertNull(((CellReference) result).getRowKey());
    }

    @Test
    void shouldCreateCellReference_whenRefCalledWithColumnAndRowKey() {
        var result = ctx.ref("col1", "row1");
        assertInstanceOf(CellReference.class, result);
        assertEquals("col1", ((CellReference) result).getColumnKey());
        assertEquals("row1", ((CellReference) result).getRowKey());
    }

    @Test
    void shouldCreateAdd_whenAddCalled() {
        var result = ctx.add(ctx.val("1"), ctx.val("2"));
        assertInstanceOf(Add.class, result);
    }

    @Test
    void shouldCreateSubtract_whenSubCalled() {
        var result = ctx.sub(ctx.val("5"), ctx.val("3"));
        assertInstanceOf(Subtract.class, result);
    }

    @Test
    void shouldCreateMultiply_whenMulCalled() {
        var result = ctx.mul(ctx.val("4"), ctx.val("5"));
        assertInstanceOf(Multiply.class, result);
    }

    @Test
    void shouldCreateDivide_whenDivCalled() {
        var result = ctx.div(ctx.val("10"), ctx.val("2"));
        assertInstanceOf(Divide.class, result);
    }

    @Test
    void shouldCreateRangeReference_whenRangeCalled() {
        var start = ctx.ref("A", "1");
        var end = ctx.ref("A", "10");
        var result = ctx.range((CellReference) start, (CellReference) end);
        assertInstanceOf(RangeReference.class, result);
    }

    @Test
    void shouldCreateIf_whenIifCalled() {
        var result = ctx.iif(ctx.val("true"), ctx.val("yes"), ctx.val("no"));
        assertInstanceOf(If.class, result);
    }
}