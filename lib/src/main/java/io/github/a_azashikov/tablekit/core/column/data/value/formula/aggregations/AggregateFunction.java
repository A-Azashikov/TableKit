package io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations;

import java.util.Arrays;
import java.util.Collection;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;

public abstract class AggregateFunction implements Formula {
    protected final Collection<Formula> arguments;
    
    public AggregateFunction(Formula ...arguments) {
        this.arguments = Arrays.asList(arguments);
    }

    public AggregateFunction(Collection<Formula> arguments) {
        this.arguments = arguments;
    }

    public Collection<Formula> getArguments() {
        return arguments;
    }

}
