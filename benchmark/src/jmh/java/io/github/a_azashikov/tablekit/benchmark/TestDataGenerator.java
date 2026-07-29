package io.github.a_azashikov.tablekit.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates test data for benchmarks.
 */
public class TestDataGenerator {

    private static final Random RANDOM = new Random(42);

    public static List<SimpleRow> generateRows(int count) {
        List<SimpleRow> rows = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            rows.add(new SimpleRow(
                    "Name " + i,
                    "2024-01-" + String.format("%02d", (i % 28) + 1),
                    "Category " + (i % 10),
                    String.valueOf(RANDOM.nextDouble() * 1000),
                    String.valueOf(RANDOM.nextInt(10000)),
                    String.valueOf(RANDOM.nextDouble() * 500)
            ));
        }
        return rows;
    }

    public static record SimpleRow(
        String name,
        String category,
        String date,
        String value1,
        String value2,
        String value3
    ) {
    }
}