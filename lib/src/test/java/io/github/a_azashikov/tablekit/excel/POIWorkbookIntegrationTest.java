package io.github.a_azashikov.tablekit.excel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.a_azashikov.tablekit.core.TableBuilder;
import io.github.a_azashikov.tablekit.core.style.Alignment;
import io.github.a_azashikov.tablekit.core.style.Border;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

class POIWorkbookIntegrationTest {

    static class Person {
        String name;
        int age;
        double salary;
        Date birthDate;

        Person(String name, int age, double salary, Date birthDate) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.birthDate = birthDate;
        }
    }

    @Test
    void shouldRenderSimpleTableWithStringData() throws Exception {
        var table = new TableBuilder<>(String.class)
            .name("SimpleTable")
            .column("Name", row -> row)
            .addRow("John")
            .addRow("Jane")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("SimpleTable");
            assertNotNull(sheet);
            assertEquals("Name", sheet.getRow(0).getCell(0).getStringCellValue());
            assertEquals("John", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals("Jane", sheet.getRow(2).getCell(0).getStringCellValue());
        }
    }

    @Test
    void shouldRenderTableWithNumericData() throws Exception {
        var table = new TableBuilder<>(Person.class)
            .name("NumericTable")
            .column("Name", row -> row.name)
            .column("Age", row -> row.age)
            .column("Salary", row -> row.salary)
            .addRow(new Person("John", 30, 50000.5, new Date()))
            .addRow(new Person("Jane", 25, 60000.0, new Date()))
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("NumericTable");
            assertNotNull(sheet);
            assertEquals("John", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals(30.0, sheet.getRow(1).getCell(1).getNumericCellValue(), 0.001);
            assertEquals(50000.5, sheet.getRow(1).getCell(2).getNumericCellValue(), 0.001);
            assertEquals("Jane", sheet.getRow(2).getCell(0).getStringCellValue());
            assertEquals(25.0, sheet.getRow(2).getCell(1).getNumericCellValue(), 0.001);
        }
    }

    @Test
    void shouldRenderTableWithGroupedColumns() throws Exception {
        var table = new TableBuilder<>(String.class)
            .name("GroupedTable")
            .group("Personal", g -> g
                .column("Name", row -> row)
                .column("Age", row -> 25)
            )
            .column("Score", row -> 100)
            .addRow("John")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("GroupedTable");
            assertNotNull(sheet);
            // Row 0: Group header "Personal" in col 0, merged across cols 0-1
            assertEquals("Personal", sheet.getRow(0).getCell(0).getStringCellValue());
            // Row 1: Data column headers
            assertEquals("Name", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals("Age", sheet.getRow(1).getCell(1).getStringCellValue());
            assertEquals("Score", sheet.getRow(1).getCell(2).getStringCellValue());
            // Row 2: Data
            assertEquals("John", sheet.getRow(2).getCell(0).getStringCellValue());
            assertEquals(25.0, sheet.getRow(2).getCell(1).getNumericCellValue(), 0.001);
            assertEquals(100.0, sheet.getRow(2).getCell(2).getNumericCellValue(), 0.001);
        }
    }

    @Test
    void shouldRenderTableWithCollapsibleColumns() throws Exception {
        var table = new TableBuilder<>(String.class)
            .name("CollapsibleTable")
            .collapsible(c -> c
                .column("Hidden1", row -> "val1")
                .column("Hidden2", row -> "val2")
            )
            .column("Visible", row -> "val3")
            .addRow("test")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("CollapsibleTable");
            assertNotNull(sheet);
            assertEquals("Hidden1", sheet.getRow(0).getCell(0).getStringCellValue());
            assertEquals("Hidden2", sheet.getRow(0).getCell(1).getStringCellValue());
            assertEquals("Visible", sheet.getRow(0).getCell(2).getStringCellValue());
            assertEquals("val1", sheet.getRow(1).getCell(0).getStringCellValue());
            assertEquals("val3", sheet.getRow(1).getCell(2).getStringCellValue());
        }
    }

    @Test
    void shouldRenderTableWithCustomStyles() throws Exception {
        var headerStyle = new CellStyleDefinition();
        headerStyle.setBold(true);
        headerStyle.setFontColor("#FF0000");
        headerStyle.setBackgroundColor("#0000FF");
        headerStyle.setAlignment(Alignment.Center);
        headerStyle.setBorder(Border.Thin);

        var table = new TableBuilder<>(String.class)
            .name("StyledTable")
            .column(ctx -> ctx
                .title("Name")
                .value(row -> row)
                .style(headerStyle)
            )
            .addRow("John")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("StyledTable");
            assertNotNull(sheet);
            var cell = sheet.getRow(0).getCell(0);
            assertEquals("Name", cell.getStringCellValue());
            var font = wb.getFontAt(cell.getCellStyle().getFontIndex());
            assertTrue(font.getBold());
        }
    }

    @Test
    void shouldRenderMultipleTables() throws Exception {
        var table1 = new TableBuilder<>(String.class)
            .name("Table1")
            .column("Col1", row -> row)
            .addRow("A")
            .build();

        var table2 = new TableBuilder<>(String.class)
            .name("Table2")
            .column("ColA", row -> row)
            .addRow("B")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table1);
        workbook.add(table2);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            assertNotNull(wb.getSheet("Table1"));
            assertNotNull(wb.getSheet("Table2"));
            assertEquals("A", wb.getSheet("Table1").getRow(1).getCell(0).getStringCellValue());
            assertEquals("B", wb.getSheet("Table2").getRow(1).getCell(0).getStringCellValue());
        }
    }

    @Test
    void shouldRenderTableWithFormulaColumn() throws Exception {
        var table = new TableBuilder<>(String.class)
            .name("FormulaTable")
            .column("Value", row -> 10)
            .column(ctx -> ctx
                .title("Doubled")
                .formula((fc, row) -> fc.mul(fc.ref("Value"), fc.val("2")))
            )
            .addRow("test")
            .build();

        var workbook = new POIWorkbook();
        workbook.add(table);

        var out = new ByteArrayOutputStream();
        workbook.render(out);

        try (var wb = new XSSFWorkbook(new ByteArrayInputStream(out.toByteArray()))) {
            var sheet = wb.getSheet("FormulaTable");
            assertNotNull(sheet);
            assertEquals(10.0, sheet.getRow(1).getCell(0).getNumericCellValue(), 0.001);
        }
    }
}