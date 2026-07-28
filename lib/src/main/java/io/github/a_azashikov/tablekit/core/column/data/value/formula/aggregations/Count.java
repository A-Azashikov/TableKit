package io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations;

import java.util.Collection;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Count extends AggregateFunction {
    public Count(Formula ...arguments) {
        super(arguments);
    }

    public Count(Collection<Formula> arguments) {
        super(arguments);
    }

    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
