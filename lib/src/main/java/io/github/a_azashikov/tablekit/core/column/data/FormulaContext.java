package io.github.a_azashikov.tablekit.core.column.data;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.*;

public class FormulaContext {
    public Formula val(String value) {
        return new Val<>(value);
    }
    
    public CellReference ref(String columnKey) {
        return ref(columnKey, null);
    }
    
    public CellReference ref(String columnKey, String rowKey) {
        return new CellReference(columnKey, rowKey);
    }
    
    // Binary operations
    public Formula add(Formula left, Formula right) {
        return new Add(left, right);
    }
    
    public Formula sub(Formula left, Formula right) {
        return new Subtract(left, right);
    }
    
    public Formula mul(Formula left, Formula right) {
        return new Multiply(left, right);
    }
    
    public Formula div(Formula left, Formula right) {
        return new Divide(left, right);
    }
    
    public Formula range(CellReference start, CellReference end) {
        return new RangeReference(start, end);
    }

    // Ternary operations
    public Formula iif(Formula left, Formula middle, Formula right) {
        return new If(left, middle, right);
    }

}
