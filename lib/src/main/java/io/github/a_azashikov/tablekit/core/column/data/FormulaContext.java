package io.github.a_azashikov.tablekit.core.column.data;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.*;

public class FormulaContext {
    public <V> Formula val(V value) {
        return new Val<V>(value);
    }
    
    public CellReference ref(String columnKey) {
        return ref(columnKey, null);
    }
    
    public CellReference ref(String columnKey, String rowKey) {
        return ref(columnKey, rowKey, null);
    }
    
    public CellReference ref(String columnKey, String rowKey, String tableName) {
        return new CellReference(columnKey, rowKey, tableName);
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

    // Aggregations
    public Formula avg(Formula ...arguments) {
        return new Avg(arguments);
    }
    
    public Formula avg(Collection<Formula> arguments) {
        return new Avg(arguments);
    }

    public Formula count(Formula ...arguments) {
        return new Count(arguments);
    }
    
    public Formula count(Collection<Formula> arguments) {
        return new Count(arguments);
    }

    public Formula max(Formula ...arguments) {
        return new Max(arguments);
    }
    
    public Formula max(Collection<Formula> arguments) {
        return new Max(arguments);
    }

    public Formula min(Formula ...arguments) {
        return new Min(arguments);
    }
    
    public Formula min(Collection<Formula> arguments) {
        return new Min(arguments);
    }

    public Formula sum(Formula ...arguments) {
        return new Sum(arguments);
    }
    
    public Formula sum(Collection<Formula> arguments) {
        return new Sum(arguments);
    }

}
