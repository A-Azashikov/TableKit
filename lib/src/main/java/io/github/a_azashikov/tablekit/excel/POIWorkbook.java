package io.github.a_azashikov.tablekit.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.a_azashikov.tablekit.core.Table;

public class POIWorkbook {
    private List<Table<?>> tables = new ArrayList<>();
    
    public <T> void add(Table<T> table) {
        tables.add(table);
    }

    public void render(OutputStream out) throws IOException {
        var rowsCount = tables.stream().mapToInt(t -> t.getRows().size()).sum();

        try (
            var workbook = getWorkbook(rowsCount);
        ) {
            var renderer = new POIRenderer(workbook);

            for (var table : tables) {
                renderer.render(table);
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Workbook getWorkbook(int rowsCount) {
        if (rowsCount < 1000) {
            return new XSSFWorkbook();
        } else {
            return new SXSSFWorkbook(100);
        }
    }
}
