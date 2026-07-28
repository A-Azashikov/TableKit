package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;

public abstract class BinaryOperation implements Formula {
    protected final Formula left;
    protected final Formula right;
    
    public BinaryOperation(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }
    
    public Formula getLeft() {
        return left;
    }

    public Formula getRight() {
        return right;
    }

}
