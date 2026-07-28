package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Divide extends BinaryOperation {
    public Divide(Formula left, Formula right) { super(left, right); }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
