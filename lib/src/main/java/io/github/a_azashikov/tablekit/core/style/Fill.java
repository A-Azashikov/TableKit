package io.github.a_azashikov.tablekit.core.style;

import org.apache.poi.ss.usermodel.FillPatternType;

public enum Fill {
    Solid(FillPatternType.SOLID_FOREGROUND),
    ;

    private FillPatternType fillPatternType;

    private Fill(FillPatternType fillPatternType) {
        this.fillPatternType = fillPatternType;
    }
    
    public FillPatternType getFillPatternType() {
        return fillPatternType;
    }

}
