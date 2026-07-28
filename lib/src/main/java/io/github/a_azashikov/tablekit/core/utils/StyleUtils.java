package io.github.a_azashikov.tablekit.core.utils;

import io.github.a_azashikov.tablekit.core.column.configurations.HeadStyle;
import io.github.a_azashikov.tablekit.core.style.CellStyleDefinition;

public class StyleUtils {
    public static CellStyleDefinition map(HeadStyle annotation) {
        var definition = new CellStyleDefinition();

        if (annotation == null) {
            return definition;
        }

        definition.setAlignment(annotation.alignment());
        definition.setBackgroundColor(annotation.backgroundColor());
        definition.setBold(annotation.bold());
        definition.setBorder(annotation.border());
        definition.setBorderColor(annotation.borderColor());
        definition.setDataFormat(annotation.dataFormat());
        definition.setFill(annotation.fill());
        definition.setFontColor(annotation.fontColor());
        definition.setFontName(annotation.fontName());
        definition.setFontSize(annotation.fontSize());
        definition.setItalic(annotation.italic());
        definition.setUnderline(annotation.underline());

        return definition;
    }
}
