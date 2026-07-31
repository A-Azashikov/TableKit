package io.github.a_azashikov.tablekit.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.util.CellReference;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.collapsible.CollapsibleColumn;
import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.column.data.value.DateValue;
import io.github.a_azashikov.tablekit.core.column.data.value.NumericValue;
import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.column.data.value.Value;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;
import io.github.a_azashikov.tablekit.core.column.group.GroupColumn;
import io.github.a_azashikov.tablekit.core.style.CellStyleCache;

class DataRenderer {
    private CellStyleCache cellStyleCache;
    private Map<CellIndex, String> cellReferenceMap;
    private FormulaAstExcelVisitor formulaAstExcelVisitor;

    public DataRenderer(CellStyleCache cellStyleCache) {
        this.cellStyleCache = cellStyleCache;
        this.cellReferenceMap = new HashMap<>();
        this.formulaAstExcelVisitor = new FormulaAstExcelVisitor(this.cellReferenceMap);
    }

    public <T> void render(Sheet sheet, Table<T> table) {
        List<DataColumn<T>> columns = new ArrayList<>();
        flatColumns(table.getColumns(), columns);

        fillCellReferences(table, columns);

        var firstRowIndex = sheet.getLastRowNum() + 1;
        for (int i = 0; i < table.getRows().size(); i++) {
            var tableRow = table.getRows().get(i);
            var row = sheet.createRow(firstRowIndex + i);
            
            for (int j = 0; j < columns.size(); j++) {
                DataColumn<T> dataColumn = columns.get(j);
                var cell = row.createCell(j);
                var value = dataColumn.getValue(tableRow);
                setValue(cell, value);
                setStyle(tableRow, dataColumn, cell, i);
            }
        }
    }

    private <T> void fillCellReferences(Table<T> table, List<DataColumn<T>> columns) {
        for (int i = 0; i < table.getRows().size(); i++) {
            var row = table.getRows().get(i);
            for (int j = 0; j < columns.size(); j++) {
                var column = columns.get(j);
                cellReferenceMap.put(
                    new CellIndex(column.getKey(), null),
                    String.format("'%s'!%s%s", table.getName(), CellReference.convertNumToColString(j), j + 1)
                );
                cellReferenceMap.put(
                    new CellIndex(column.getKey(), table.getRowKeyGetter().apply(row)),
                    String.format("'%s'!%s%s", table.getName(), CellReference.convertNumToColString(j), j + 1)
                );
            }
        }
    }

    private <T> void setStyle(T tableRow, DataColumn<T> dataColumn, Cell cell, Integer rowIndex) {
        cell.setCellStyle(
            cellStyleCache.getOrCreateCellStyle(
                dataColumn.getCellStyle(tableRow, rowIndex)
            )
        );
    }

    private void setValue(Cell cell, Value value) {
        if (value instanceof NumericValue) {
            setValue(cell, (NumericValue) value);
        }
        if (value instanceof StringValue) {
            setValue(cell, (StringValue) value);
        }
        if (value instanceof DateValue) {
            setValue(cell, (DateValue) value);
        }
        if (value instanceof FormulaValue) {
            setValue(cell, (FormulaValue) value);
        }
    }

    private void setValue(Cell cell, NumericValue value) {
        cell.setCellValue(value.getValue().doubleValue());
    }

    private void setValue(Cell cell, StringValue value) {
        cell.setCellValue(value.getValue());
    }

    private void setValue(Cell cell, DateValue value) {
        cell.setCellValue(value.getValue());
    }

    private void setValue(Cell cell, FormulaValue value) {
        var formula = value.getFormula().accept(formulaAstExcelVisitor);
        if (formula == null) {
            return;
        }
        cell.setCellFormula(formula);
    }

    private <T> void flatColumns(List<Column<T>> columns, List<DataColumn<T>> result) {
        for (Column<T> column : columns) {
            if (column instanceof DataColumn<T>) {
                result.add((DataColumn<T>) column);
            } else if (column instanceof GroupColumn<T>) {
                flatColumns(((GroupColumn<T>) column).getChildren(), result);
            } else if (column instanceof CollapsibleColumn<T>) {
                flatColumns(((CollapsibleColumn<T>) column).getChildren(), result);
            }
        }
    }
}
