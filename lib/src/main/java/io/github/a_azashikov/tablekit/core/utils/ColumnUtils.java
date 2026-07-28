package io.github.a_azashikov.tablekit.core.utils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import io.github.a_azashikov.tablekit.core.column.Column;
import io.github.a_azashikov.tablekit.core.column.configurations.HeadStyle;
import io.github.a_azashikov.tablekit.core.column.configurations.Name;
import io.github.a_azashikov.tablekit.core.column.data.DataColumn;
import io.github.a_azashikov.tablekit.core.column.data.value.Value;

public class ColumnUtils {
    public static <T> Stream<Column<T>> generateColumns(Class<T> classz) {
        Stream.Builder<Column<T>> columnsStreamBuilder = Stream.<Column<T>>builder();
        var fields = classz.getFields();

        for (Field field : fields) {
            var columnAnnotation = field.getAnnotation(Name.class);

            if (columnAnnotation == null) {
                continue;
            }

            var column = new DataColumn<T>();

            column.setTitle(columnAnnotation.value());
            column.setValueGetter(r -> mapFieldValue(r, field));
            var styleAnnotation = field.getAnnotation(HeadStyle.class);
            column.setHeaderStyle(StyleUtils.map(styleAnnotation));
            
            columnsStreamBuilder.add(column);
        }

        return columnsStreamBuilder.build();
    }

    private static <T> Value mapFieldValue(T row, Field field) {
        if (!field.canAccess(row)) {
            return null;
        }
        try {
            return ValueUtils.mapValue(field.get(field));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
