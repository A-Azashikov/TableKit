package io.github.a_azashikov.tablekit.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import io.github.a_azashikov.tablekit.core.Table;
import io.github.a_azashikov.tablekit.core.renderer.Renderer;
import io.github.a_azashikov.tablekit.core.style.CellStyleCache;

public class POIRenderer implements Renderer {
    private Workbook wb;

    public POIRenderer(Workbook wb) {
        this.wb = wb;
    }

    @Override
    public <T> void render(Table<T> table) {
        Sheet sheet = wb.createSheet(table.getName());
        var cache = new CellStyleCache(wb);

        var headerRenderer = new HeaderRenderer(cache);
        headerRenderer.render(sheet, table);

        var dataRenderer = new DataRenderer(cache);
        dataRenderer.render(sheet, table);
    }
}
