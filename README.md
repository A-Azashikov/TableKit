# TableKit

**TableKit** — это Java-библиотека для декларативного построения сложных Excel-отчётов с иерархическими заголовками, формулами и кастомизацией стилей. Библиотека предоставляет type-safe fluent API, позволяющий описывать структуру таблицы в коде, отделяя логику данных от рендеринга.

## Мотивация

При разработке корпоративных приложений часто возникает задача формирования Excel-отчётов со сложной структурой:

- **Иерархические заголовки** — группировка колонок по временным периодам, категориям, подразделениям
- **Формулы** — ссылки на ячейки, агрегации (SUM, AVG), условные вычисления (IF)
- **Кастомные стили** — выделение цветом, шрифты, границы, формат чисел
- **Динамическое количество колонок** — структура таблицы определяется только в рантайме

Использование Apache POI напрямую приводит к спагетти-коду: логика данных, стилей и расположения колонок перемешивается, теряется читаемость, сложно вносить изменения.

**TableKit решает эти проблемы** за счёт:

- **Декларативного DSL** — вы описываете *что* нужно отобразить, а не *как*
- **Type-safe API** — компилятор проверяет соответствие типов данных на этапе сборки
- **Отделения данных от представления** — рендеринг в Excel полностью изолирован от бизнес-логики
- **Автоматизации рутины** — авто-выбор оптимального режима записи (XSSFWorkbook / SXSSFWorkbook)

## Возможности

| Возможность | Описание |
|---|---|
| **Fluent Builder API** | Цепочка вызовов: `Table.from(rows).name("Report").column(...).build()` |
| **Иерархические заголовки** | Вложенные группы колонок произвольной глубины |
| **Collapsible-колонки** | Сворачиваемые группы для компактного отображения |
| **Поддержка формул** | +, -, *, /, IF, ссылки на ячейки |
| **Type-safe значения** | String, Number, Date, Formula — строгая типизация значений |
| **Кастомизация стилей** | Шрифт, цвет, фон, границы, выравнивание, формат чисел |
| **Excel (XLSX)** | Рендеринг в формат OOXML через Apache POI |
| **Streaming для больших данных** | Авто-выбор SXSSFWorkbook при > 1000 строк |
| **Несколько таблиц** | Возможность добавить несколько таблиц в один workbook |
| **Auto-columns** | Авто-генерация колонок из полей record-класса через аннотацию `@Name` |

## Установка

### Gradle

```kotlin
dependencies {
    implementation("org.apache.poi:poi:5.5.1")
    implementation("org.apache.poi:poi-ooxml:5.5.1")
}
```

Библиотека использует Apache POI для генерации Excel-файлов и Java 21+.

## Быстрый старт

### 1. Простейшая таблица

```java
// Класс-строка данных
public record Row(String name, LocalDate date, double value) {}

// Построение таблицы
var rows = List.of(
    new Row("Alpha", LocalDate.of(2024, 1, 1), 100.0),
    new Row("Beta",  LocalDate.of(2024, 2, 1), 200.0)
);

var table = Table.from(rows)
    .name("Report")
    .column("Name",  Row::name)
    .column("Date",  Row::date)
    .column("Value", Row::value)
    .build();

// Выгрузка в Excel
var workbook = new POIWorkbook();
workbook.add(table);

try (var out = new FileOutputStream("report.xlsx")) {
    workbook.render(out);
}
```

### 2. Иерархические заголовки (группы колонок)

```java
var table = Table.from(rows)
    .name("Финансовый отчёт")
    .column("Наименование", Row::name)
    .group("2024 год", g -> {
        g.group("Q1", q1 -> {
            q1.column("Январь",   c -> c.title("Янв").number(Row::jan));
            q1.column("Февраль",  c -> c.title("Фев").number(Row::feb));
            q1.column("Март",     c -> c.title("Мар").number(Row::mar));
        });
        g.group("Q2", q2 -> {
            q2.column("Апрель",   c -> c.title("Апр").number(Row::apr));
            q2.column("Май",      c -> c.title("Май").number(Row::may));
            q2.column("Июнь",     c -> c.title("Июн").number(Row::jun));
        });
    })
    .build();
```

**Результат:** в Excel объединённые ячейки заголовков с иерархией:

```
|              2024 год                    |
|         Q1          |         Q2         |
| Янв | Фев | Мар | Апр | Май | Июн |
```

### 3. Колонки с формулами

```java
var table = Table.from(rows)
    .name("Сводка")
    .column("Наименование", Row::name)
    .column("Доход",   c -> c.title("Доход").number(Row::income))
    .column("Расход",  c -> c.title("Расход").number(Row::expense))
    .column("Прибыль", c -> c.title("Прибыль")
        .formula((f, r) -> f.sub(f.ref("Доход"), f.ref("Расход")))
    )
    .build();
```

Поддерживаемые формулы:

| Формула | Описание |
|---|---|
| `ref("ColumnName")` | Ссылка на значение в другой колонке текущей строки |
| `ref("ColumnName", "RowKey")` | Ссылка на ячейку по ключам колонки и строки |
| `ref("ColumnName", "RowKey", "TableName")` | Кросс-табличная ссылка на ячейку |
| `val(value)` | Литеральное значение любого типа (String, Number и т.д.) |
| `add(left, right)`, `sub(left, right)`, `mul(left, right)`, `div(left, right)` | Арифметические операции |
| `iff(condition, thenVal, elseVal)` | Условное вычисление |

Также можно задать формулу напрямую через `Formula`:

```java
var table = Table.from(rows)
    .name("Сводка")
    .column("Доход",   c -> c.title("Доход").number(Row::income))
    .column("Расход",  c -> c.title("Расход").number(Row::expense))
    .column("Прибыль", c -> c.title("Прибыль")
        .formula(f.sub(f.ref("Доход"), f.ref("Расход")))
    )
    .build();
```

Ссылки на ячейки в формулах автоматически резолвятся в реальные Excel-координаты (например, `B2`, `C3`) при рендеринге.

### 4. Кастомные стили

```java
var headerStyle = new CellStyleDefinition();
headerStyle.setFontName("Arial");
headerStyle.setFontSize((short) 12);
headerStyle.setBold(true);
headerStyle.setBackgroundColor("#4472C4");
headerStyle.setFontColor("#FFFFFF");
headerStyle.setAlignment(Alignment.Center);
headerStyle.setBorder(Border.Thin);
headerStyle.setBorderColor("#000000");

var table = Table.from(rows)
    .name("Styled")
    .column(c -> c.title("Name").value(Row::name).style(headerStyle))
    .column(c -> c.title("Value").number(Row::value).style(headerStyle))
    .build();
```

### 5. Авто-колонки

```java
// Класс-строка данных с аннотацией @Name для заголовков
public record Row(
    @Name("Name") String name,
    @Name("Date") LocalDate date,
    @Name("Value") double value
) {}

// Вариант 1: через Table.of(Class) — без данных, только структура
var table = Table.of(Row.class)
    .name("Auto Report")
    .autoColumns()
    .build();

// Вариант 2: через Table.from(rows) с авто-колонками
var rows = List.of(
    new Row("Alpha", LocalDate.of(2024, 1, 1), 100.0),
    new Row("Beta",  LocalDate.of(2024, 2, 1), 200.0)
);

var table = Table.from(rows)
    .name("Auto Report")
    .autoColumns()
    .build();

// Выгрузка в Excel
var workbook = new POIWorkbook();
workbook.add(table);

try (var out = new FileOutputStream("report.xlsx")) {
    workbook.render(out);
}
```

Метод `autoColumns()` сканирует поля record-класса через рефлексию, находит поля с аннотацией `@Name` и автоматически создаёт колонки с соответствующими заголовками и типами.

## Архитектура

```
┌─────────────────────────────────────────────┐
│                  DSL / Builder               │
│  Table → TableBuilder → ChildrenContextBase │
│     ├── DataColumn     (простая колонка)    │
│     ├── GroupColumn    (группа колонок)     │
│     └── CollapsibleColumn (сворачиваемая)   │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│              Formula Engine                  │
│  Formula → FormulaValue → FormulaBaseVisitor│
│  ├── Aggregations: Sum, Avg, Count, Min, Max│
│  ├── Binary: Add, Sub, Mul, Div             │
│  ├── Unary: CellReference, Val              │
│  └── Ternary: If                            │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│             Excel Rendering                  │
│  POIWorkbook → POIRenderer                  │
│    ├── HeaderRenderer (заголовки)           │
│    └── DataRenderer    (данные + формулы)   │
└─────────────────────────────────────────────┘
```

### Ключевые классы

| Класс | Назначение |
|---|---|
| `Table<T>` | Контейнер с колонками и строками, точка входа: `Table.from(List)` |
| `TableBuilder<T>` | Fluent builder: `name()`, `column()`, `group()`, `collapsible()`, `build()` |
| `DataColumn<T>` | Колонка с данными: title, value getter, style |
| `GroupColumn<T>` | Группа колонок с вложенными дочерними колонками |
| `CollapsibleColumn<T>` | Сворачиваемая группа колонок |
| `CellStyleDefinition` | Полное описание стиля: шрифт, фон, границы, выравнивание, формат |
| `Formula` | Билдер формул: ссылки, арифметика, агрегации, IF |
| `POIWorkbook` | Excel-воркбук с авто-выбором XSSFWorkbook / SXSSFWorkbook |
| `POIRenderer` | Рендеринг таблицы в Excel-лист |

## Метрики производительности

| Сценарий | Механизм | Описание |
|---|---|---|
| **< 1000 строк** | `XSSFWorkbook` (DOM) | Полная загрузка в память, быстрая запись |
| **≥ 1000 строк** | `SXSSFWorkbook` (Streaming) | Окно в 100 строк в памяти, запись на диск — низкое потребление памяти |

Выбор режима происходит автоматически в `POIWorkbook.getWorkbook(rowsCount)`:

```java
public Workbook getWorkbook(int rowsCount) {
    if (rowsCount < 1000) {
        return new XSSFWorkbook();    // Малые данные
    } else {
        return new SXSSFWorkbook(100); // Большие данные
    }
}
```

Для точных замеров производительности на ваших данных рекомендуется запустить нагрузочное тестирование с типовой структурой колонок и формул.

## Разработка

### Сборка проекта

```bash
./gradlew build
```

### Запуск тестов

```bash
./gradlew test
```

## Лицензия

Проект распространяется под лицензией Apache 2.0. См. файл [LICENSE](LICENSE).
