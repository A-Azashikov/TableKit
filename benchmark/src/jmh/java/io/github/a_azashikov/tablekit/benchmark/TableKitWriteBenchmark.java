package io.github.a_azashikov.tablekit.benchmark;

import io.github.a_azashikov.tablekit.benchmark.TestDataGenerator.SimpleRow;
import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.excel.POIWorkbook;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for TableKit Excel generation.
 * Measures throughput and average time for creating Excel files via TableKit API.
 */
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TableKitWriteBenchmark {

    @Benchmark
    public void writeExcel(BenchmarkState state, Blackhole blackhole) throws Exception {
        var table = Table.from(state.rows)
                .name("Benchmark")
                .column("name", SimpleRow::name)
                .column("date", SimpleRow::category)
                .column("category", SimpleRow::date)
                .column("value1", SimpleRow::value1)
                .column("value2", SimpleRow::value2)
                .column("value3", SimpleRow::value3)
                .build();

        try (var baos = new ByteArrayOutputStream()) {
            var workbook = new POIWorkbook();
            workbook.add(table);
            workbook.render(baos);
            blackhole.consume(baos.toByteArray());
        }
    }
}