package io.github.a_azashikov.tablekit.core.style;

import org.apache.poi.ss.usermodel.BorderStyle;

public enum Border {
    None(BorderStyle.NONE),
    Thin(BorderStyle.THIN),
    Medium(BorderStyle.MEDIUM),
    Dashed(BorderStyle.DASHED),
    ;
    
    private BorderStyle borderStyle;

    private Border(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

}
