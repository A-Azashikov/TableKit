package io.github.a_azashikov.tablekit.core.column.data;

import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.data.value.DateValue;
import io.github.a_azashikov.tablekit.core.column.data.value.NumericValue;
import io.github.a_azashikov.tablekit.core.column.data.value.StringValue;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.Formula;
import io.github.a_azashikov.tablekit.core.column.data.value.formula.FormulaValue;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;
import io.github.a_azashikov.tablekit.core.utils.ValueUtils;

public class CustomizeDataContext<T> {
    private final DataColumn<T> column;
    
    public CustomizeDataContext(DataColumn<T> column) {
        this.column = column;
    }

    public CustomizeDataContext<T> title(String title) {
        this.column.setTitle(title);

        if (this.column.getKey().equals("")) {
            this.column.setKey(title);
        }

        return this;
    }
    
    public CustomizeDataContext<T> key(String key) {
        this.column.setKey(key);

        return this;
    }
    
    public CustomizeDataContext<T> size(Integer size) {
        this.column.setSize(size);

        return this;
    }
    
    public CustomizeDataContext<T> style(CellStyleDefinition headerStyle) {
        this.column.setHeaderStyle(headerStyle);

        return this;
    }
    
    public CustomizeDataContext<T> cellStyle(BiFunction<T, Integer, CellStyleDefinition> cellStyleGetter) {
        this.column.setStyleGetter(cellStyleGetter);

        return this;
    }
    
    public CustomizeDataContext<T> value(Function<T, Object> stringValueGetter) {
        this.column.setValueGetter(stringValueGetter.andThen(ValueUtils::mapValue));

        return this;
    }
    
    public CustomizeDataContext<T> string(Function<T, String> stringValueGetter) {
        this.column.setValueGetter(stringValueGetter.andThen(StringValue::new));

        return this;
    }
    
    public CustomizeDataContext<T> number(Function<T, Number> numberValueGetter) {
        this.column.setValueGetter(numberValueGetter.andThen(NumericValue::new));

        return this;
    }
    
    public CustomizeDataContext<T> date(Function<T, Date> dateValueGetter) {
        this.column.setValueGetter(dateValueGetter.andThen(DateValue::new));

        return this;
    }
    
    public CustomizeDataContext<T> formula(BiFunction<FormulaContext, T, Formula> formulaBuilder) {
        this.column.setValueGetter(
            r -> new FormulaValue(formulaBuilder.apply(new FormulaContext(), r))
        );

        return this;
    }
}
