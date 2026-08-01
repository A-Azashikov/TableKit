# Примеры использования TableKit

В этом модуле собраны готовые примеры использования библиотеки TableKit. Каждый пример находится в отдельном пакете и может быть запущен через соответствующую Gradle-задачу.

## Содержание

- [1. Простая таблица](#1-простая-таблица)
- [2. Группировка колонок](#2-группировка-колонок)
- [3. Формулы](#3-формулы)
- [4. Динамически генерируемые колонки](#4-динамически-генерируемые-колонки)

---

## 1. Простая таблица

**Пакет:** `io.github.a_azashikov.tablekit.example.simple`

**Запуск:**
```bash
./gradlew :example:run
```

**Код:** `SimpleExample.java`

```java
var rows = new ArrayList<Row>();

for (int i = 0; i < 10000; i++) {
    rows.add(
        new Row(
            "name " + i,
            "date 2",
            "test 2",
            "4", "1", "2"
        )
    );
}

var table = Table.from(rows)
    .name("Test")
    .column("Name", Row::name)
    .column("Date", Row::date)
    .column("Test", Row::test)
    .column("v1", Row::c1)
    .column("v2", Row::c2)
    .column("v3", Row::c3)
    .build();

Path tempFilePath = Files.createTempFile("resultExcel", ".xlsx");
try (
    FileOutputStream outputStream = new FileOutputStream(tempFilePath.toFile());
) {
    var workbook = new POIWorkbook();
    workbook.add(table);
    workbook.render(outputStream);
}
System.out.println("Excel file created at: " + tempFilePath);
```

---

## 2. Группировка колонок

**Пакет:** `io.github.a_azashikov.tablekit.example.group`

**Запуск:**
```bash
./gradlew :example:runGroupExample
```

**Код:** `GroupExample.java`

```java
var table = Table.from(rows)
    .name("Grouped")
    .group("Personal Info", group -> group
        .column("Name", Row::name)
        .column("Date", Row::date)
    )
    .column("Test", Row::test)
    .group("Values", group -> group
        .column("v1", Row::c1)
        .column("v2", Row::c2)
        .column("v3", Row::c3)
    )
    .build();
```

В Excel заголовки групп объединяются, формируя иерархическую шапку таблицы.

---

## 3. Формулы

**Пакет:** `io.github.a_azashikov.tablekit.example.formula`

**Запуск:**
```bash
./gradlew :example:runFormulaExample
```

**Код:** `FormulaExample.java`

```java
var table = Table.from(rows)
    .name("Formula")
    .rowKey(r -> r.product())
    .column("Product", Row::product)
    .column("Price", Row::price)
    .column("Quantity", Row::quantity)
    .column(ctx -> ctx
        .title("Income")
        .formula((fc, r) -> {
            if (!r.product().equals("Total")) {
                return fc.mul(fc.ref("Price"), fc.ref("Quantity"));
            }
            return fc.sum(fc.range(fc.ref("Income", "Phone"), fc.ref("Income", "Laptop")));
        })
    )
    .build();
```

Ссылки на ячейки в формулах автоматически резолвятся в реальные Excel-координаты (например, `B2`, `C3`) при рендеринге.

---

## 4. Динамически генерируемые колонки

**Пакет:** `io.github.a_azashikov.tablekit.example.dynamic`

**Запуск:**
```bash
./gradlew :example:runDynamicColumnsExample
```

**Код:** `DynamicColumnsExample.java`

Количество колонок определяется только в рантайме — на основе диапазона дат из клиентского запроса. Для каждой даты между `startDate` и `endDate` создаётся отдельная колонка.

```java
// Имитация клиентского запроса: диапазон дат, для которого генерируются колонки
LocalDate startDate = LocalDate.of(2026, 1, 1);
LocalDate endDate = LocalDate.of(2026, 1, 31);

// Строки для отрисовки
List<String> rows = generateRows();
// Значения для каждого дня диапазона по строкам
Map<RowDayDataKey, Double> values = generateData(rows, startDate, endDate);

TableBuilder<String> builder = Table.from(rows)
    .name("Daily Sales")
    .column("Name", String::valueOf);

// Динамически создаём по одной колонке на каждый день диапазона
for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
    LocalDate day = date;
    builder.column(
        day.format(HEADER_FORMAT),
        row -> values.get(new RowDayDataKey(row, day))
    );
}

Table<String> table = builder.build();
```

**Класс-строка данных** (`Row.java`):

```java
public record Row(
    String name,
    Map<LocalDate, Double> dailyValues
) {}
```

**Результат:** таблица с колонкой `Name` и одной колонкой на каждый день января (01.01, 02.01, ..., 31.01), значения берутся из `dailyValues` каждой строки.