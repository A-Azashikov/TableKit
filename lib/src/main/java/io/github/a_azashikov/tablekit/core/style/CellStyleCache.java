package io.github.a_azashikov.tablekit.core.style;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class CellStyleCache {
    private Workbook workbook;
    private Map<CellStyleDefinition, CellStyle> cache = new HashMap<>();

    public CellStyleCache(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle getOrCreateCellStyle(CellStyleDefinition definition){
        return cache.computeIfAbsent(
            definition,
            k -> toPoiStyle(k, workbook)
        );
    }

    public CellStyle toPoiStyle(CellStyleDefinition def, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        if (def == null) {
            return style;
        }

        // Шрифт
        Font font = workbook.createFont();
        font.setFontName(def.getFontName());
        font.setFontHeightInPoints(def.getFontSize());
        font.setBold(def.isBold());
        font.setItalic(def.isItalic());
        font.setUnderline(def.isUnderline() ? Font.U_SINGLE : Font.U_NONE);
        if (def.getFontColor() != null) {
            font.setColor(hexToXssfColor(def.getFontColor()));
        }
        style.setFont(font);

        // Фон
        if (def.getBackgroundColor() != null) {
            style.setFillForegroundColor(hexToXssfColor(def.getBackgroundColor()));
            style.setFillPattern(def.getFill().getFillPatternType());
        }

        // Выравнивание
        style.setAlignment(def.getAlignment().getHorizontalAlignment());
        style.setVerticalAlignment(def.getAlignment().getVerticalAlignment());

        // Границы
        style.setBorderTop(def.getBorder().getBorderStyle());
        style.setBorderBottom(def.getBorder().getBorderStyle());
        style.setBorderLeft(def.getBorder().getBorderStyle());
        style.setBorderRight(def.getBorder().getBorderStyle());
        if (def.getBorderColor() != null) {
            short color = hexToXssfColor(def.getBorderColor());
            style.setTopBorderColor(color);
            style.setBottomBorderColor(color);
            style.setLeftBorderColor(color);
            style.setRightBorderColor(color);
        }

        // Формат чисел
        if (def.getDataFormat() != null) {
            DataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat(def.getDataFormat()));
        }

        return style;
    }

    // Преобразование HEX в цвет XSSF (для .xlsx)
    private short hexToXssfColor(String hex) {
        if (hex == null || hex.isEmpty()) {
            return IndexedColors.BLACK.getIndex();
        }
        return new XSSFColor(new java.awt.Color(
            Integer.parseInt(hex.substring(1, 3), 16),
            Integer.parseInt(hex.substring(3, 5), 16),
            Integer.parseInt(hex.substring(5, 7), 16)
        ), null).getIndex();
    }
}
