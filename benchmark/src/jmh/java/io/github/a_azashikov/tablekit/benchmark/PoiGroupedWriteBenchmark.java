package io.github.a_azashikov.tablekit.benchmark;

import io.github.a_azashikov.tablekit.benchmark.TestDataGenerator.SimpleRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for grouped Apache POI Excel generation.
 * Measures throughput and average time for creating Excel files with grouped headers
 * directly using Apache POI API (no TableKit wrapper).
 * Two groups: "General Info" (name, date, category) and "Values" (value1, value2, value3).
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PoiGroupedWriteBenchmark {

    @Benchmark
    public void writeGroupedExcel(BenchmarkState state, Blackhole blackhole) throws Exception {
        int rowCount = state.rows.size();
        Workbook wb;
        if (rowCount < 1000) {
            wb = new XSSFWorkbook();
        } else {
            wb = new SXSSFWorkbook(100);
        }

        try (wb) {
            Sheet sheet = wb.createSheet("Benchmark");

            // Group header row (row 0)
            Row groupHeader = sheet.createRow(0);
            Cell g0 = groupHeader.createCell(0);
            g0.setCellValue("General Info");
            Cell g1 = groupHeader.createCell(3);
            g1.setCellValue("Values");

            // Merge cells for group headers
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); // General Info: cols 0-2
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5)); // Values: cols 3-5

            // Column header row (row 1)
            Row header = sheet.createRow(1);
            String[] headers = {"name", "date", "category", "value1", "value2", "value3"};
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows
            int rowIdx = 2;
            for (SimpleRow row : state.rows) {
                Row excelRow = sheet.createRow(rowIdx++);
                excelRow.createCell(0).setCellValue(row.name());
                excelRow.createCell(1).setCellValue(row.date());
                excelRow.createCell(2).setCellValue(row.category());
                excelRow.createCell(3).setCellValue(row.value1());
                excelRow.createCell(4).setCellValue(row.value2());
                excelRow.createCell(5).setCellValue(row.value3());
            }

            // Write to byte array
            try (var baos = new ByteArrayOutputStream()) {
                wb.write(baos);
                blackhole.consume(baos.toByteArray());
            }
        }
    }
}