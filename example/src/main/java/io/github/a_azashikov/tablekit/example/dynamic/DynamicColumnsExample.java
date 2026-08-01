package io.github.a_azashikov.tablekit.example.dynamic;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<String> rows = generateRows();
        Map<RowDayDataKey, Double> values = generateData(rows, startDate, endDate);

        TableBuilder<String> builder = Table.from(rows)
            .name("Daily Sales")
            .column("Name", String::valueOf);

        // Dynamically create one column per day between startDate and endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate day = date;
            builder.column(
                day.format(HEADER_FORMAT),
                row -> values.get(new RowDayDataKey(row, day))
            );
        }

        Table<String> table = builder.build();

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

    private static List<String> generateRows() {
        List<String> rows = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            rows.add("Product " + i);
        }
        return rows;
    }

    private static Map<RowDayDataKey, Double> generateData(List<String> rows, LocalDate startDate, LocalDate endDate) {
        Map<RowDayDataKey, Double> values = new HashMap<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < rows.size(); i++) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                values.put(new RowDayDataKey(rows.get(i), date), random.nextDouble(0, 1000));
            }
        }
        return values;
    }

    private static record RowDayDataKey(String row, LocalDate day) {}
}