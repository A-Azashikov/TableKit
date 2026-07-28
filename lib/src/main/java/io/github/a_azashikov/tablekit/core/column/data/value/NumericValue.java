package io.github.a_azashikov.tablekit.core.column.data.value;

public class NumericValue implements Value {
    private Number value;

    public NumericValue(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

}
