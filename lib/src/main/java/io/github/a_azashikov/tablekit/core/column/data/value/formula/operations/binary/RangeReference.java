package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.CellReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class RangeReference extends BinaryOperation {
    public RangeReference(CellReference start, CellReference end) {
        super(start, end);
    }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
