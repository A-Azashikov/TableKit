package io.github.a_azashikov.tablekit.core.utils;

import java.util.Date;

import io.github.a_azashikov.tablekit.core.column.data.value.DateValue;
import io.github.a_azashikov.tablekit.core.column.data.value.NumericValue;
import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.column.data.value.Value;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;

public class ValueUtils {
    public static Value mapValue(Object v) {
        if (v instanceof Value) {
            return (Value) v;
        }
        if (v instanceof Number) {
            return new NumericValue(Double.valueOf(v.toString()));
        }
        if (v instanceof Date) {
            return new DateValue((Date) v);
        }
        if (v instanceof Formula) {
            return new FormulaValue((Formula) v);
        }
        if (v instanceof String) {
            return new StringValue(v.toString());
        }
        return new StringValue(v.toString());
    }
}
