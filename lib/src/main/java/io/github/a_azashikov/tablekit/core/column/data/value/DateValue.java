package io.github.a_azashikov.tablekit.core.column.data.value;

import java.util.Date;

public class DateValue implements Value {
    private Date value;

    public DateValue(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return value;
    }

}
