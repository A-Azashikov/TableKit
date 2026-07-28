package io.github.a_azashikov.tablekit.core.style;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public enum Alignment {
    TopLeft(VerticalAlignment.TOP, HorizontalAlignment.LEFT),
    Top(VerticalAlignment.TOP, HorizontalAlignment.CENTER),
    TopRight(VerticalAlignment.TOP, HorizontalAlignment.RIGHT),
    Left(VerticalAlignment.CENTER, HorizontalAlignment.LEFT),
    Center(VerticalAlignment.CENTER, HorizontalAlignment.CENTER),
    Right(VerticalAlignment.CENTER, HorizontalAlignment.RIGHT),
    BottomLeft(VerticalAlignment.BOTTOM, HorizontalAlignment.LEFT),
    Bottom(VerticalAlignment.BOTTOM, HorizontalAlignment.CENTER),
    BottomRight(VerticalAlignment.BOTTOM, HorizontalAlignment.RIGHT),
    ;
    
    private VerticalAlignment verticalAlignment;
    private HorizontalAlignment horizontalAlignment;

    private Alignment(VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment) {
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
    }
    
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

}
