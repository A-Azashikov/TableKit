package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;

public abstract class TernaryOperation implements Formula {
    protected final Formula left;
    protected final Formula middle;
    protected final Formula right;
    
    public TernaryOperation(Formula left, Formula middle, Formula right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public Formula getLeft() {
        return left;
    }

    public Formula getMiddle() {
        return middle;
    }

    public Formula getRight() {
        return right;
    }

}
