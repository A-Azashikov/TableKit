package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;

public abstract class UnaryOperation implements Formula {
    protected final Formula formula;
    
    public UnaryOperation(Formula formula) {
        this.formula = formula;
    }
    
    public Formula getFormula() {
        return formula;
    }

}
