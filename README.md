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

## Установка

### Gradle

*Добавить после выхода в репозиторий*

## Быстрый старт

> Полные готовые примеры с кодом и командами запуска смотрите в [example/EXAMPLES.md](example/EXAMPLES.md).

### 1. Простейшая таблица

```java
var table = Table.from(rows)
    .name("Report")
    .column("Name",  Row::name)
    .column("Date",  Row::date)
    .column("Value", Row::value)
    .build();

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
public record Row(
    @Name("Name") String name,
    @Name("Date") LocalDate date,
    @Name("Value") double value
) {}

var table = Table.of(Row.class)
    .name("Auto Report")
    .autoColumns()
    .build();
```

Метод `autoColumns()` сканирует поля record-класса через рефлексию, находит поля с аннотацией `@Name` и автоматически создаёт колонки с соответствующими заголовками и типами.

## Бенчмарки

Подробные результаты нагрузочного тестирования TableKit vs прямой вызов Apache POI доступны в [benchmark/RESULTS.md](benchmark/RESULTS.md).

**Сводная таблица (Avg Time, ms/op):**

| Размер | POI plain | TK plain | POI grouped | TK grouped | POI styled | TK styled |
|-------:|----------:|---------:|------------:|-----------:|-----------:|----------:|
| 100 | 7.16 | 7.42 | 7.18 | 7.74 | 7.67 | 8.00 |
| 1 000 | 13.34 | 13.65 | 13.47 | 13.95 | 13.51 | 14.56 |
| 10 000 | 107.3 | 110.7 | 105.9 | 114.1 | 108.2 | 115.2 |
| 100 000 | 1436 | 1571 | 1691 | 1114 | 1723 | 1501 |

**TK** = TableKit.

**Вывод:** на всех объёмах данных TableKit показывает производительность, близкую к прямому использованию Apache POI.

## Разработка

### Сборка проекта

```bash
./gradlew build
```

### Запуск тестов

```bash
./gradlew test
```

### Git hook `pre-push`

Перед каждым пушем автоматически запускается проверка кода через `./gradlew build` (компиляция всех модулей, тесты и check-задачи). Hook устанавливается автоматически при каждой сборке — отдельная установка не требуется. Если hook не установлен (например, после `git clone`), достаточно выполнить любую сборку:

```bash
./gradlew build
```

или установить hook вручную:

```bash
./gradlew installGitHooks
```

Настройка hook:

| Переменная окружения | Описание |
|---|---|
| `GRADLE_TASKS` | Задачи Gradle, выполняемые перед пушем. По умолчанию `build`. Пример: `GRADLE_TASKS="test" git push` |
| `SKIP_GIT_HOOKS=1` | Полностью пропускает проверку перед пушем |

Для временного пропуска проверки:

```bash
SKIP_GIT_HOOKS=1 git push
```

## Лицензия

Проект распространяется под лицензией Apache 2.0. См. файл [LICENSE](LICENSE).
