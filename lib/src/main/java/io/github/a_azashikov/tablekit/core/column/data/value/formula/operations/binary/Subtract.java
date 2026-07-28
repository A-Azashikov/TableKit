package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Subtract extends BinaryOperation {
    public Subtract(Formula left, Formula right) { super(left, right); }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
