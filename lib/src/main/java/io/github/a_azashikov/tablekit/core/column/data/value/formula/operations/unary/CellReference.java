package io.github.a_azashikov.tablekit.core.column.data.value.formula.operations.unary;

import io.github.a_azashikov.tablekit.core.column.data.value.formula.visitor.FormulaBaseVisitor;

public class CellReference extends UnaryOperation {
    private final String columnKey;
    private final String rowKey;
    
    public CellReference(String columnKey, String rowKey) {
        super(null);

        this.columnKey = columnKey;
        this.rowKey = rowKey;
    }
    
    @Override
    public <R> R accept(FormulaBaseVisitor<R> visitor) {
        return visitor.visit(this);
    }

    public String getColumnKey() {
        return columnKey;
    }

    public String getRowKey() {
        return rowKey;
    }

}
