package io.github.a_azashikov.tablekit.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.aggregations.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.binary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.ternary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary.*;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class FormulaAstExcelVisitor<T> implements FormulaBaseVisitor<String> {
    private final Map<CellIndex, String> cellReferenceMap;
    private final T row;
    private final Table<T> table;

    public FormulaAstExcelVisitor(Map<CellIndex, String> cellReferenceMap, T row, Table<T> table) {
        this.cellReferenceMap = cellReferenceMap;
        this.row = row;
        this.table = table;
    }

    @Override
    public String visit(UnaryOperation operation) {
        if (operation instanceof Val) {
            return visit((Val<?>) operation);
        }
        if (operation instanceof CellReference) {
            return visit((CellReference) operation);
        }
        return "";
    }

    @Override
    public String visit(BinaryOperation operation) {
        if (operation instanceof Add) {
            return operation.getLeft().accept(this) + "+" + operation.getRight().accept(this);
        }
        if (operation instanceof Divide) {
            return operation.getLeft().accept(this) + "/" + operation.getRight().accept(this);
        }
        if (operation instanceof Subtract) {
            return operation.getLeft().accept(this) + "-" + operation.getRight().accept(this);
        }
        if (operation instanceof Multiply) {
            return operation.getLeft().accept(this) + "*" + operation.getRight().accept(this);
        }
        if (operation instanceof RangeReference) {
            var start = operation.getLeft().accept(this);
            if (start == null) {
                return null;
            }
            var end = operation.getRight().accept(this);
            if (end == null) {
                return null;
            }
            return start + ":" + end;
        }
        return "";
    }

    @Override
    public String visit(TernaryOperation operation) {
        if (operation instanceof If) {
            return String.format(
                "IF(%s, %s, %s)",
                operation.getLeft().accept(this),
                operation.getMiddle().accept(this),
                operation.getRight().accept(this)
            );
        }
        return "";
    }

    @Override
    public String visit(AggregateFunction aggregation) {
        List<String> parameters = new ArrayList<>();
        for (var argument : aggregation.getArguments()) {
            var parameter = argument.accept(this);
            if (parameter == null) {
                continue;
            }
            parameters.add(parameter);
        }
        if (aggregation instanceof Avg) {
            return String.format(
                "AVG(%s)",
                String.join(",", parameters)
            );
        }
        if (aggregation instanceof Sum) {
            return String.format(
                "SUM(%s)",
                String.join(",", parameters)
            );
        }
        if (aggregation instanceof Count) {
            return String.format(
                "COUNT(%s)",
                String.join(",", parameters)
            );
        }
        if (aggregation instanceof Min) {
            return String.format(
                "MIN(%s)",
                String.join(",", parameters)
            );
        }
        if (aggregation instanceof Max) {
            return String.format(
                "MAX(%s)",
                String.join(",", parameters)
            );
        }
        return "";
    }

    public String visit(Val<?> operation) {
        return operation.getValue().toString();
    }

    public String visit(CellReference reference) {
        return cellReferenceMap.get(
            new CellIndex(
                reference.getColumnKey(),
                Optional.ofNullable(reference.getRowKey()).orElse(table.getRowKeyGetter().apply(row))
            )
        );
    }

}
