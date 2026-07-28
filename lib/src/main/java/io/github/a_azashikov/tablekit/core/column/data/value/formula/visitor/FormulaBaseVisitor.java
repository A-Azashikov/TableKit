package io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.AggregateFunction;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.BinaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.TernaryOperation;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.UnaryOperation;

public interface FormulaBaseVisitor<R> {
    R visit(UnaryOperation operation);
    R visit(BinaryOperation operation);
    R visit(TernaryOperation operation);
    R visit(AggregateFunction aggregation);
}
