package io.github.a_azashikov.tablekit.benchmark;

import io.github.a_azashikov.tablekit.benchmark.TestDataGenerator.SimpleRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for styled Apache POI Excel generation.
 * Measures throughput and average time for creating styled Excel files
 * directly using Apache POI API (no TableKit wrapper).
 * Styles: bold header with blue background, alternating row colors, thin borders.
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PoiStyledWriteBenchmark {

    @Benchmark
    public void writeStyledExcel(BenchmarkState state, Blackhole blackhole) throws Exception {
        int rowCount = state.rows.size();
        Workbook wb;
        if (rowCount < 1000) {
            wb = new XSSFWorkbook();
        } else {
            wb = new SXSSFWorkbook(100);
        }

        try (wb) {
            Sheet sheet = wb.createSheet("Benchmark");

            // Header style: bold, blue background, white text, thin border
            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Even row style: white background, thin border
            CellStyle evenStyle = wb.createCellStyle();
            evenStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            evenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            evenStyle.setBorderTop(BorderStyle.THIN);
            evenStyle.setBorderBottom(BorderStyle.THIN);
            evenStyle.setBorderLeft(BorderStyle.THIN);
            evenStyle.setBorderRight(BorderStyle.THIN);

            // Odd row style: light blue background, thin border
            CellStyle oddStyle = wb.createCellStyle();
            oddStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            // Use a lighter blue via custom color
            XSSFWorkbook xssfWb = (XSSFWorkbook) (wb instanceof SXSSFWorkbook
                    ? ((SXSSFWorkbook) wb).getXSSFWorkbook() : wb);
            IndexedColorMap colorMap = xssfWb.getStylesSource().getIndexedColors();
            Font oddFont = wb.createFont();
            oddStyle.setFont(oddFont);
            oddStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 0xD9, (byte) 0xE2, (byte) 0xF3}, colorMap));
            oddStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            oddStyle.setBorderTop(BorderStyle.THIN);
            oddStyle.setBorderBottom(BorderStyle.THIN);
            oddStyle.setBorderLeft(BorderStyle.THIN);
            oddStyle.setBorderRight(BorderStyle.THIN);

            // Header row
            Row header = sheet.createRow(0);
            String[] headers = {"name", "date", "category", "value1", "value2", "value3"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (SimpleRow row : state.rows) {
                Row excelRow = sheet.createRow(rowIdx);
                CellStyle rowStyle = (rowIdx % 2 == 0) ? evenStyle : oddStyle;

                Cell c0 = excelRow.createCell(0);
                c0.setCellValue(row.name());
                c0.setCellStyle(rowStyle);

                Cell c1 = excelRow.createCell(1);
                c1.setCellValue(row.date());
                c1.setCellStyle(rowStyle);

                Cell c2 = excelRow.createCell(2);
                c2.setCellValue(row.category());
                c2.setCellStyle(rowStyle);

                Cell c3 = excelRow.createCell(3);
                c3.setCellValue(row.value1());
                c3.setCellStyle(rowStyle);

                Cell c4 = excelRow.createCell(4);
                c4.setCellValue(row.value2());
                c4.setCellStyle(rowStyle);

                Cell c5 = excelRow.createCell(5);
                c5.setCellValue(row.value3());
                c5.setCellStyle(rowStyle);

                rowIdx++;
            }

            // Write to byte array
            try (var baos = new ByteArrayOutputStream()) {
                wb.write(baos);
                blackhole.consume(baos.toByteArray());
            }
        }
    }
}