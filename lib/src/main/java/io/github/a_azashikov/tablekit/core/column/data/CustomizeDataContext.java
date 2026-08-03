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

public class CustomizeDataContext<T, K> {
    private final DataColumn<T> column;
    
    public CustomizeDataContext(DataColumn<T> column) {
        this.column = column;
    }

    public CustomizeDataContext<T, K> title(String title) {
        this.column.setTitle(title);

        if (this.column.getKey().equals("")) {
            this.column.setKey(title);
        }

        return this;
    }
    
    public CustomizeDataContext<T, K> key(K key) {
        this.column.setKey(key.toString());

        return this;
    }
    
    public CustomizeDataContext<T, K> size(Integer size) {
        this.column.setSize(size);

        return this;
    }
    
    public CustomizeDataContext<T, K> style(CellStyleDefinition headerStyle) {
        this.column.setHeaderStyle(headerStyle);

        return this;
    }
    
    public CustomizeDataContext<T, K> cellStyle(BiFunction<T, Integer, CellStyleDefinition> cellStyleGetter) {
        this.column.setStyleGetter(cellStyleGetter);

        return this;
    }
    
    public CustomizeDataContext<T, K> value(Function<T, Object> stringValueGetter) {
        this.column.setValueGetter(stringValueGetter.andThen(ValueUtils::mapValue));

        return this;
    }
    
    public CustomizeDataContext<T, K> string(Function<T, String> stringValueGetter) {
        this.column.setValueGetter(stringValueGetter.andThen(StringValue::new));

        return this;
    }
    
    public CustomizeDataContext<T, K> number(Function<T, Number> numberValueGetter) {
        this.column.setValueGetter(numberValueGetter.andThen(NumericValue::new));

        return this;
    }
    
    public CustomizeDataContext<T, K> date(Function<T, Date> dateValueGetter) {
        this.column.setValueGetter(dateValueGetter.andThen(DateValue::new));

        return this;
    }
    
    public CustomizeDataContext<T, K> formula(BiFunction<FormulaContext<K>, T, Formula> formulaBuilder) {
        this.column.setValueGetter(
            r -> new FormulaValue(formulaBuilder.apply(new FormulaContext<>(), r))
        );

        return this;
    }
    
    public CustomizeDataContext<T, K> formula(Formula formula) {
        this.column.setValueGetter(
            r -> new FormulaValue(formula)
        );

        return this;
    }
}
