package io.github.a_azashikov.tablekit.example.formula;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.excel.POIWorkbook;

public class FormulaExample {
    public static void main(String[] args) throws Exception {
        var rows = new ArrayList<Row>();

        rows.add(new Row("Phone", 6.999, 6));
        rows.add(new Row("Mouse", 2.5, 24));
        rows.add(new Row("Mic", 1.5, 25));
        rows.add(new Row("Laptop", 29.999, 13));
        
        rows.add(new Row("Total", null, null));

        var table = Table.from(rows)
            .name("Formula")
            .rowKey(r -> r.product())
            .withKeyType(ColumnKeys.class)
            .column(ColumnKeys.Product, "Product", Row::product)
            .column(ColumnKeys.Price, "Price", Row::price)
            .column(ColumnKeys.Quantity, "Quantity", Row::quantity)
            .column(ctx -> ctx
                .title("Income")
                .key(ColumnKeys.Income)
                .formula((fc, r) -> {
                    if (!r.product().equals("Total")) {
                        return fc.mul(fc.ref(ColumnKeys.Price), fc.ref(ColumnKeys.Quantity));
                    }
                    return fc.sum(fc.range(fc.ref(ColumnKeys.Income, "Phone"), fc.ref(ColumnKeys.Income, "Laptop")));
                })
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

    static enum ColumnKeys {
        Product,
        Price,
        Quantity,
        Income,
        ;
    } 
}
