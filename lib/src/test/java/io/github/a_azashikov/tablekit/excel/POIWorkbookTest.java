package io.github.a_azashikov.tablekit.excel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import io.github.a_azashikov.tablekit.core.TableBuilder;

class POIWorkbookTest {

    @Test
    void shouldAddTable_whenAddCalled() {
        var workbook = new POIWorkbook();
        var table = new TableBuilder<String>().name("Test").build();
        workbook.add(table);
    }

    @Test
    void shouldRenderWithoutError_whenRenderCalled() throws Exception {
        var workbook = new POIWorkbook();
        var table = new TableBuilder<String>()
            .name("Test")
            .column("Name", row -> row)
            .addRow("John")
            .build();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);
        assertTrue(out.size() > 0);
    }

    @Test
    void shouldReturnXSSFWorkbook_whenRowsLessThan1000() {
        var poiWorkbook = new POIWorkbook();
        var wb = poiWorkbook.getWorkbook(500);
        assertEquals("XSSFWorkbook", wb.getClass().getSimpleName());
    }

    @Test
    void shouldReturnSXSSFWorkbook_whenRowsMoreThan1000() {
        var poiWorkbook = new POIWorkbook();
        var wb = poiWorkbook.getWorkbook(1500);
        assertEquals("SXSSFWorkbook", wb.getClass().getSimpleName());
    }
}