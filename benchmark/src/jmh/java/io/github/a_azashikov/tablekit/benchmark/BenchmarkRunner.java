package io.github.a_azashikov.tablekit.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Entry point for running all JMH benchmarks.
 */
public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(".*Benchmark")
                .warmupIterations(3)
                .measurementIterations(5)
                .forks(2)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }
}