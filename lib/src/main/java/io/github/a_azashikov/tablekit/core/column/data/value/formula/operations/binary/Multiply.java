package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Multiply extends BinaryOperation {
    public Multiply(Formula left, Formula right) { super(left, right); }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
