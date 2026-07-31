package io.github.a_azashikov.tablekit.example.group;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.excel.POIWorkbook;
import io.github.a_azashikov.tablekit.example.simple.Row;

public class GroupExample {
    public static void main(String[] args) throws Exception {
        var rows = new ArrayList<Row>();

        for (int i = 0; i < 10000; i++) {
            rows.add(
                new Row(
                    "name " + i,
                    "date 2",
                    "test 2",
                    "4", "1", "2"
                )
            );
        }

        var table = Table.from(rows)
            .name("Grouped")
            .group("Personal Info", group -> group
                .column("Name", Row::name)
                .column("Date", Row::date)
            )
            .column("Test", Row::test)
            .group("Values", group -> group
                .column("v1", Row::c1)
                .column("v2", Row::c2)
                .column("v3", Row::c3)
            )
            .build();

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
}