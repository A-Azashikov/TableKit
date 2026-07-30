package io.github.a_azashikov.tablekit.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.collapsible.CollapsibleColumn;
import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.column.group.GroupColumn;
import io.github.a_azashikov.tablekit.core.style.CellStyleCache;

class HeaderRenderer {
    private CellStyleCache cellStyleCache;

    public HeaderRenderer(CellStyleCache cellStyleCache) {
        this.cellStyleCache = cellStyleCache;
    }

    public <T> void render(Sheet sheet, Table<T> table) {
        var colIndex = 0;
        var rowIndex = 0;
        var maxDepth = getMaxDepth(table.getColumns(), 0);

        if (table.getDefaultColumnSize() != null) {
            sheet.setDefaultColumnWidth(maxDepth);
        }

        for (var column : table.getColumns()) {
            renderColumn(sheet, column, rowIndex, colIndex, maxDepth);
            colIndex += column.getWidth();
        }
    }

    private <T> void renderColumn(Sheet sheet, Column<T> column, int rowIndex, int colIndex, int maxDepth) {
        if (column instanceof GroupColumn<T>) {
            renderColumn(sheet, (GroupColumn<T>) column, rowIndex, colIndex, maxDepth);
        }
        if (column instanceof CollapsibleColumn<T>) {
            renderColumn(sheet, (CollapsibleColumn<T>) column, rowIndex, colIndex, maxDepth);
        }
        if (column instanceof DataColumn<T>) {
            renderColumn(sheet, (DataColumn<T>) column, colIndex, maxDepth);
        }
    }

    private <T> void renderColumn(Sheet sheet, GroupColumn<T> column, int rowIndex, int colIndex, int maxDepth) {
        var row = getOrCreateRow(sheet, rowIndex);
        var cell = row.createCell(colIndex);
        cell.setCellValue(column.getTitle());
        cell.setCellStyle(
            cellStyleCache.getOrCreateCellStyle(
                column.getHeaderStyle()
            )
        );
        
        var childColPos = colIndex;
        for (var child : column.getChildren()) {
            renderColumn(sheet, child, rowIndex + 1, childColPos, maxDepth);
            childColPos += child.getWidth();
        }

        if (column.getWidth() > 1) {
            sheet.addMergedRegion(
                new CellRangeAddress(
                    rowIndex, rowIndex,
                    colIndex, childColPos - 1
                )
            );
        }
    }
    
    private <T> void renderColumn(Sheet sheet, CollapsibleColumn<T> column, int rowIndex, int colIndex, int maxDepth) {
        var childColPos = colIndex;
        for (var child : column.getChildren()) {
            renderColumn(sheet, child, rowIndex, childColPos, maxDepth);
            childColPos += child.getWidth();
        }

        sheet.groupColumn(colIndex, childColPos - 1);
        sheet.setColumnGroupCollapsed(colIndex, true);
    }
    
    private <T> void renderColumn(Sheet sheet, DataColumn<T> column, int colIndex, int maxDepth) {
        var row = getOrCreateRow(sheet, maxDepth);
        var cell = row.createCell(colIndex);
        cell.setCellValue(column.getTitle());
        cell.setCellStyle(
            cellStyleCache.getOrCreateCellStyle(
                column.getHeaderStyle()
            )
        );
        if (column.getSize() != null) {
            sheet.setColumnWidth(colIndex, column.getSize()*256);
        }
    }

    private Row getOrCreateRow(Sheet sheet, int rowIndex) {
        var row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        return row;
    }

    private <T> int getMaxDepth(List<Column<T>> columns, int depth) {
        var result = depth;
        for (Column<T> column : columns) {
            var currentDepth = switch (column) {
                case GroupColumn<T> g -> getMaxDepth(g.getChildren(), depth + 1);
                case DataColumn<T> d -> result;
                case CollapsibleColumn<T> g -> getMaxDepth(g.getChildren(), depth);
                default -> result;
            };

            result = Math.max(result, currentDepth);
        }

        return result;
    }
}
