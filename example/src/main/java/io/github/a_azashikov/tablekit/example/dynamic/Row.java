package io.github.a_azashikov.tablekit.example.dynamic;

import java.time.LocalDate;
import java.util.Map;

public record Row(
    String name,
    Map<LocalDate, Double> dailyValues
) {}