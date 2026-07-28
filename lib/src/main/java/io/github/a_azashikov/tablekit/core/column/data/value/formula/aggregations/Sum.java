package io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations;

import java.util.Collection;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Sum extends AggregateFunction {
    public Sum(Formula ...arguments) {
        super(arguments);
    }

    public Sum(Collection<Formula> arguments) {
        super(arguments);
    }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
