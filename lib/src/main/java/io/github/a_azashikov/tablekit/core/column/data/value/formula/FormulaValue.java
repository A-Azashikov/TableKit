package io.github.a_azashikov.tablekit.core.column.data.value.formula;

import io.github.a_azashikov.tablekit.core.column.data.value.Value;

public class FormulaValue implements Value {
    private Formula value;

    public FormulaValue(Formula value) {
        this.value = value;
    }

    public Formula getFormula() {
        return value;
    }

}
