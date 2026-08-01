package io.github.a_azashikov.tablekit.example.dynamic;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.TableBuilder;
import io.github.a_azashikov.tablekit.excel.POIWorkbook;

public class DynamicColumnsExample {
    private static final DateTimeFormatter HEADER_FORMAT = DateTimeFormatter.ofPattern("dd.MM");

    public static void main(String[] args) throws Exception {
        // Simulated client request: the date range for which columns should be generated
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 31);

        // Generate rows with a value for every day in the requested range
        List<Row> rows = generateRows(startDate, endDate);

        TableBuilder<Row> builder = Table.from(rows)
            .name("Daily Sales")
            .column("Name", Row::name);

        // Dynamically create one column per day between startDate and endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate day = date;
            builder.column(
                day.format(HEADER_FORMAT),
                row -> row.dailyValues().get(day)
            );
        }

        Table<Row> table = builder.build();

        Path tempFilePath = Files.createTempFile("resultExcel", ".xlsx");
        try (
            FileOutputStream outputStream = new FileOutputStream(
                tempFilePath.toFile()
            );
        ) {
            var workbook = new POIWorkbook();
            workbook.add(table);
            workbook.render(outputStream);
        }
        System.out.println("Excel file created at: " + tempFilePath);
    }

    private static List<Row> generateRows(LocalDate startDate, LocalDate endDate) {
        List<Row> rows = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            Map<LocalDate, Double> dailyValues = new LinkedHashMap<>();
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                dailyValues.put(date, random.nextDouble(0, 1000));
            }
            rows.add(new Row("Product " + i, dailyValues));
        }
        return rows;
    }
}