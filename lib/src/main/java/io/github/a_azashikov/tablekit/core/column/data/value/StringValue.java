package io.github.a_azashikov.tablekit.core.column.data.value;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
