package io.github.a_azashikov.tablekit.example.simple;

import io.github.a_azashikov.tablekit.core.column.configurations.Name;
import io.github.a_azashikov.tablekit.core.column.configurations.HeadStyle;

public record Row(
    @Name("name")
    String name,
    @Name("date")
    String date,
    @Name("test")
    String test,
    @Name("c1")
    String c1,
    @Name("c2")
    String c2,
    @Name("c3")
    @HeadStyle(
        dataFormat = "#.##"
    )
    String c3
) {}