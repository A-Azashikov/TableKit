package io.github.a_azashikov.tablekit.benchmark;

import io.github.a_azashikov.tablekit.benchmark.TestDataGenerator.SimpleRow;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.List;

/**
 * Shared JMH state providing test data for benchmarks.
 */
@State(Scope.Thread)
public class BenchmarkState {

    @Param({"100", "1000", "10000", "100000"})
    public int rowCount;

    public List<SimpleRow> rows;

    @Setup
    public void setup() {
        rows = TestDataGenerator.generateRows(rowCount);
    }
}