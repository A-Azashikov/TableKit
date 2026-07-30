package io.github.a_azashikov.tablekit.benchmark;

import io.github.a_azashikov.tablekit.benchmark.TestDataGenerator.SimpleRow;
import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.style.Border;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;
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

    @Benchmark
    public void writeGroupedExcel(BenchmarkState state, Blackhole blackhole) throws Exception {
        var table = Table.from(state.rows)
                .name("Benchmark")
                .group("General Info", g -> g
                        .column("name", SimpleRow::name)
                        .column("date", SimpleRow::category)
                        .column("category", SimpleRow::date)
                )
                .group("Values", g -> g
                        .column("value1", SimpleRow::value1)
                        .column("value2", SimpleRow::value2)
                        .column("value3", SimpleRow::value3)
                )
                .build();

        try (var baos = new ByteArrayOutputStream()) {
            var workbook = new POIWorkbook();
            workbook.add(table);
            workbook.render(baos);
            blackhole.consume(baos.toByteArray());
        }
    }

    @Benchmark
    public void writeStyledExcel(BenchmarkState state, Blackhole blackhole) throws Exception {
        var headerStyle = new CellStyleDefinition();
        headerStyle.setBold(true);
        headerStyle.setBackgroundColor("#4472C4");
        headerStyle.setFontColor("#FFFFFF");
        headerStyle.setBorder(Border.Thin);

        var evenRowStyle = new CellStyleDefinition();
        evenRowStyle.setBorder(Border.Thin);
        evenRowStyle.setBackgroundColor("#FFFFFF");

        var oddRowStyle = new CellStyleDefinition();
        oddRowStyle.setBorder(Border.Thin);
        oddRowStyle.setBackgroundColor("#D9E2F3");

        var table = Table.from(state.rows)
                .name("Benchmark")
                .column(ctx -> ctx
                        .title("name").value(SimpleRow::name)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .column(ctx -> ctx
                        .title("date").value(SimpleRow::category)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .column(ctx -> ctx
                        .title("category").value(SimpleRow::date)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .column(ctx -> ctx
                        .title("value1").value(SimpleRow::value1)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .column(ctx -> ctx
                        .title("value2").value(SimpleRow::value2)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .column(ctx -> ctx
                        .title("value3").value(SimpleRow::value3)
                        .style(headerStyle)
                        .cellStyle((r, idx) -> idx % 2 == 0 ? evenRowStyle : oddRowStyle)
                )
                .build();

        try (var baos = new ByteArrayOutputStream()) {
            var workbook = new POIWorkbook();
            workbook.add(table);
            workbook.render(baos);
            blackhole.consume(baos.toByteArray());
        }
    }
}