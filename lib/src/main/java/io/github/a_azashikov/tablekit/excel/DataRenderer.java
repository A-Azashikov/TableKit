package io.github.a_azashikov.tablekit.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.column.Column;
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
    private FormulaAstExcelVisitor formulaAstExcelVisitor;

    public DataRenderer(CellStyleCache cellStyleCache) {
        this.cellStyleCache = cellStyleCache;
        this.formulaAstExcelVisitor = new FormulaAstExcelVisitor();
    }

    public <T> void render(Sheet sheet, Table<T> table) {
        List<DataColumn<T>> columns = new ArrayList<>();
        flatColumns(table.getColumns(), columns);

        for (var tableRow : table.getRows()) {
            var row = sheet.createRow(sheet.getLastRowNum() + 1);

            for (int i = 0; i < columns.size(); i++) {
                DataColumn<T> dataColumn = columns.get(i);
                var cell = row.createCell(i);
                var value = dataColumn.getValue(tableRow);
                setValue(cell, value);
                setStyle(tableRow, dataColumn, cell);
            }
        }
    }

    private <T> void setStyle(T tableRow, DataColumn<T> dataColumn, Cell cell) {
        cell.setCellStyle(
            cellStyleCache.getOrCreateCellStyle(
                dataColumn.getCellStyle(tableRow)
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
        cell.setCellValue(
            value.getFormula().accept(formulaAstExcelVisitor)
        );
    }

    private <T> void flatColumns(List<Column<T>> columns, List<DataColumn<T>> result) {
        for (Column<T> column : columns) {
            if (column instanceof DataColumn<T>) {
                result.add((DataColumn<T>) column);
            } else if (column instanceof GroupColumn<T>) {
                flatColumns(((GroupColumn<T>) column).getChildren(), result);
            }
        }
    }
}
