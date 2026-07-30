package io.github.a_azashikov.tablekit.core.column.configurations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.a_azashikov.tablekit.core.style.Alignment;
import io.github.a_azashikov.tablekit.core.style.Border;
import io.github.a_azashikov.tablekit.core.style.Fill;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HeadStyle {
    String fontName() default "Arial";
    short fontSize() default 11;
    boolean bold() default false;
    boolean italic() default false;
    boolean underline() default false;
    String fontColor() default "#000000"; // HEX

    // Фон
    String backgroundColor() default "#FFFFFF"; // HEX
    Fill fill() default Fill.Solid;

    // Выравнивание
    Alignment alignment() default Alignment.Center;

    // Границы
    Border border() default Border.None;
    String borderColor() default "#000000";

    // Формат числа
    String dataFormat() default ""; // например "#,##0.00"
}
