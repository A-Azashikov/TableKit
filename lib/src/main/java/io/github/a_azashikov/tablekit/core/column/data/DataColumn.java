package io.github.a_azashikov.tablekit.core.column.data;

import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.data.value.Value;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

public class DataColumn<T> implements Column<T> {
    private String title = "";
    private String key = "";
    private Function<T, Value> valueGetter = r -> null;
    private BiFunction<T, Integer, CellStyleDefinition> styleGetter = (r, i) -> null;
    private CellStyleDefinition headerStyle = null;
    private Integer size = null;

    @Override
    public String getTitle() {
        return title;
    }

    public Value getValue(T row) {
        return valueGetter.apply(row);
    }

    public CellStyleDefinition getCellStyle(T row, Integer index) {
        return styleGetter.apply(row, index);
    }

    @Override
    public CellStyleDefinition getHeaderStyle() {
        return headerStyle;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    public Integer getSize() {
        return size;
    }
    
    public String getKey() {
        return key;
    }

    public void setHeaderStyle(CellStyleDefinition headerStyle) {
        this.headerStyle = headerStyle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValueGetter(Function<T, Value> valueGetter) {
        this.valueGetter = valueGetter;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStyleGetter(BiFunction<T, Integer, CellStyleDefinition> styleGetter) {
        this.styleGetter = styleGetter;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
