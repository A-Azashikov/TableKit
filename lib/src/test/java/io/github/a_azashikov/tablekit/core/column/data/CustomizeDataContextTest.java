package io.github.a_azashikov.tablekit.core.column.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.Val;
import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.column.data.value.NumericValue;
import io.github.a_azashikov.tablekit.core.column.data.value.DateValue;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

class CustomizeDataContextTest {

    @Test
    void shouldSetTitleAndKey_whenTitleCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.title("Name");
        assertEquals("Name", column.getTitle());
        assertEquals("Name", column.getKey());
    }

    @Test
    void shouldSetKey_whenKeyCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.title("Name").key("custom_key");
        assertEquals("custom_key", column.getKey());
    }

    @Test
    void shouldSetSize_whenSizeCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.size(100);
        assertEquals(100, column.getSize());
    }

    @Test
    void shouldSetHeaderStyle_whenStyleCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        var style = new CellStyleDefinition();
        ctx.style(style);
        assertEquals(style, column.getHeaderStyle());
    }

    @Test
    void shouldSetCellStyle_whenCellStyleCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        var style = new CellStyleDefinition();
        ctx.cellStyle((row, index) -> style);
        assertEquals(style, column.getCellStyle("test", 0));
    }

    @Test
    void shouldSetStringValueGetter_whenValueCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.value(row -> row);
        var value = column.getValue("test");
        assertInstanceOf(StringValue.class, value);
        assertEquals("test", ((StringValue) value).getValue());
    }

    @Test
    void shouldSetStringValueGetter_whenStringCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.string(Function.identity());
        var value = column.getValue("test");
        assertInstanceOf(StringValue.class, value);
        assertEquals("test", ((StringValue) value).getValue());
    }

    @Test
    void shouldSetNumericValueGetter_whenNumberCalled() {
        var column = new DataColumn<Number>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.number(Function.identity());
        var value = column.getValue(42);
        assertInstanceOf(NumericValue.class, value);
        assertEquals(42, ((NumericValue) value).getValue());
    }

    @Test
    void shouldSetDateValueGetter_whenDateCalled() {
        var column = new DataColumn<Date>();
        var ctx = new CustomizeDataContext<>(column);
        var date = new Date();
        ctx.date(Function.identity());
        var value = column.getValue(date);
        assertInstanceOf(DateValue.class, value);
        assertEquals(date, ((DateValue) value).getValue());
    }

    @Test
    void shouldSetFormulaValueGetter_whenFormulaCalled() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        ctx.formula((fc, row) -> fc.val(row));
        var value = column.getValue("test");
        assertInstanceOf(FormulaValue.class, value);
    }

    @Test
    void shouldSetFormulaValueGetter_whenFormulaCalledWithDirectFormula() {
        var column = new DataColumn<String>();
        var ctx = new CustomizeDataContext<>(column);
        var formula = new Val<>("test");
        ctx.formula(formula);
        var value = column.getValue("test");
        assertInstanceOf(FormulaValue.class, value);
    }
}
