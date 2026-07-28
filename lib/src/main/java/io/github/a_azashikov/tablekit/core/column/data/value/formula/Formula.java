package io.github.a_azashikov.tablekit.core.column.data.value.formula;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public interface Formula {
    <R> R accept(FormulaBaseVisitor<R> visitor);
}
