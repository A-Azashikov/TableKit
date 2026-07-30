package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import java.util.Objects;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.CellReference;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class RangeReference extends BinaryOperation {
    public RangeReference(CellReference start, CellReference end) {
        super(start, end);

        if (!Objects.equals(start.getTableName(), end.getTableName())) {
            throw new RuntimeException("Start and end should lay in same table");
        }
    }

    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
