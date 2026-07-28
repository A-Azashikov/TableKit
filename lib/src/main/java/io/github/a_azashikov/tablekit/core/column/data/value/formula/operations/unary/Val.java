package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class Val<T> extends UnaryOperation {
    private final T value;

    public Val(T value) {
        super(null);

        this.value = value;
    }
    
    public T getValue() {
        return value;
    }

    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
