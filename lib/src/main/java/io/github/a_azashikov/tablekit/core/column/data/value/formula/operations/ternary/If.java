package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class If extends TernaryOperation {
    public If(Formula left, Formula middle, Formula right) {
        super(left, middle, right);
    }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
