import docx
from docx.shared import Pt, Inches, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import OxmlElement
from docx.oxml.ns import qn

def set_cell_background(cell, fill_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = OxmlElement('w:shd')
    shd.set(qn('w:val'), 'clear')
    shd.set(qn('w:color'), 'auto')
    shd.set(qn('w:fill'), fill_hex)
    tcPr.append(shd)

def create_report(output_path):
    doc = docx.Document()

    section = doc.sections[0]
    section.top_margin = Inches(0.8)
    section.bottom_margin = Inches(0.8)
    section.left_margin = Inches(1.0)
    section.right_margin = Inches(0.8)

    def add_p(text, font_name="Times New Roman", font_size=13, bold=False, italic=False,
              align=WD_ALIGN_PARAGRAPH.LEFT, line_spacing=1.15, space_after=Pt(4)):
        p = doc.add_paragraph()
        p.alignment = align
        p.paragraph_format.line_spacing = line_spacing
        p.paragraph_format.space_after = space_after
        run = p.add_run(text)
        run.font.name = font_name
        run.font.size = Pt(font_size)
        run.bold = bold
        run.italic = italic
        return p

    def add_code_block(code_text):
        table = doc.add_table(rows=1, cols=1)
        table.alignment = WD_TABLE_ALIGNMENT.CENTER
        table.autofit = False
        cell = table.cell(0, 0)
        cell.width = Inches(6.5)
        set_cell_background(cell, "1E1E1E")
        
        tcPr = cell._tc.get_or_add_tcPr()
        tcMar = OxmlElement('w:tcMar')
        for margin in ['top', 'bottom']:
            m = OxmlElement(f'w:{margin}')
            m.set(qn('w:w'), '120')
            m.set(qn('w:type'), 'dxa')
            tcMar.append(m)
        for margin in ['left', 'right']:
            m = OxmlElement(f'w:{margin}')
            m.set(qn('w:w'), '180')
            m.set(qn('w:type'), 'dxa')
            tcMar.append(m)
        tcPr.append(tcMar)

        p = cell.paragraphs[0]
        p.paragraph_format.line_spacing = 1.05
        p.paragraph_format.space_after = Pt(0)
        run = p.add_run(code_text.strip())
        run.font.name = "Consolas"
        run.font.size = Pt(9.5)
        run.font.color.rgb = RGBColor(220, 220, 220)
        
        p_after = doc.add_paragraph()
        p_after.paragraph_format.space_after = Pt(4)

    # Титульна сторінка
    add_p("Державний університет інформаційно-комунікаційних технологій\nКафедра технологій цифрового розвитку",
          font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, line_spacing=1.15, space_after=Pt(36))

    for _ in range(3):
        add_p("", space_after=Pt(10))

    add_p("З В І Т", font_size=18, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, space_after=Pt(12))
    add_p("з практичної роботи № 5 КПЗ Java", font_size=14, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, space_after=Pt(14))
    add_p("Тема 5. Винятки, захисне програмування та основи вводу-виводу.\nПеревірювані та неперевірювані винятки, власні винятки, робота з файлами",
          font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, line_spacing=1.15, space_after=Pt(40))

    for _ in range(4):
        add_p("", space_after=Pt(10))

    add_p("Виконав: студент групи ТЦР-31", font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.RIGHT, space_after=Pt(4))
    add_p("Прізвище: Чертілін П. О.", font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.RIGHT, space_after=Pt(4))
    add_p("Перевірив: Німещенко Д. О.", font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.RIGHT, space_after=Pt(30))

    for _ in range(4):
        add_p("", space_after=Pt(10))

    add_p("Київ – 2026", font_size=13, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, space_after=Pt(10))

    doc.add_page_break()

    # Основна частина
    add_p("Практична робота № 5. Винятки, захисне програмування та основи вводу-виводу",
          font_size=14, bold=True, align=WD_ALIGN_PARAGRAPH.CENTER, space_after=Pt(8))

    add_p("Мета роботи: Набуття практичних навичок обробки винятків у Java, ознайомлення з відмінностями між перевірюваними та неперевірюваними винятками, створенням власних винятків зі збереженням причини, а також з коректним закриттям ресурсів і явним заданням кодування під час роботи з текстовими файлами.",
          font_size=12, space_after=Pt(8))

    add_p("Завдання за варіантом: Варіант 0 (25 % 5 = 0)", font_size=12, bold=True, space_after=Pt(4))
    add_p("Дані у файлі: оцінки студентів. Власний виняток: InvalidGradeException.", font_size=12, space_after=Pt(12))

    add_p("Фрагменти коду до кожного досліду й спостереження", font_size=13, bold=True, space_after=Pt(8))

    # Дослід 1
    add_p("1. Перевірювані та неперевірювані винятки, порядок catch, finally", font_size=12, bold=True, space_after=Pt(4))
    
    add_p("1.1. Виклик методу з перевірюваним винятком без обробки:", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("FileReader reader = new FileReader(\"data.txt\");")
    add_p("Реакція компілятора javac:", font_size=12, italic=True, space_after=Pt(2))
    add_code_block("error: unreported exception FileNotFoundException; must be caught or declared to be thrown\n        FileReader reader = new FileReader(\"data.txt\");\n                            ^")
    add_p("Пояснення: FileNotFoundException належить до перевірюваних винятків. Компілятор зобов'язує розробника або обробити його через try-catch, або задекларувати у сигнатурі методу через throws.", font_size=12, space_after=Pt(6))

    add_p("1.2. Обробка через try-catch та через throws:", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("try {\n    FileReader reader = new FileReader(\"grades.txt\");\n} catch (FileNotFoundException e) {\n    System.out.println(e.getMessage());\n}\n\npublic void openGradeFileWithThrows(String fileName) throws FileNotFoundException {\n    new FileReader(fileName);\n}")
    add_p("Пояснення: При try-catch виняток обробляється локально. При throws метод делегує виняток викликаючому коду вище за стеком викликів.", font_size=12, space_after=Pt(6))

    add_p("1.3. Неперевірюваний виняток (Unchecked Exception):", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("int[] sampleGrades = {95, 88, 74};\nint invalidIndex = sampleGrades.length + 2;\nint invalidAccess = sampleGrades[invalidIndex];")
    add_p("Спостереження: Код компілюється без помилок, оскільки ArrayIndexOutOfBoundsException є нащадком RuntimeException. Під час виконання виникає аварійне завершення:", font_size=12, space_after=Pt(2))
    add_code_block("Exception in thread \"main\" java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 3")

    add_p("1.4. Неправильний порядок блоків catch:", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("try {\n    FileReader reader = new FileReader(\"data.txt\");\n} catch (Exception e) {\n    System.out.println(e.getMessage());\n} catch (IOException e) {\n    System.out.println(e.getMessage());\n}")
    add_p("Реакція компілятора javac:", font_size=12, italic=True, space_after=Pt(2))
    add_code_block("error: exception IOException has already been caught\n        } catch (IOException e) {\n          ^")
    add_p("Пояснення: Блок catch (Exception e) перехоплює всі винятки, тому розташований нижче блок catch (IOException e) є недосяжним кодом (unreachable code).", font_size=12, space_after=Pt(6))

    add_p("1.5. Демонстрація виконання блоку finally:", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("try {\n    int denominator = 0;\n    int res = 100 / denominator;\n} catch (ArithmeticException e) {\n    System.out.println(e.getMessage());\n} finally {\n    System.out.println(\"Блок finally виконано\");\n}")
    add_p("Спостереження: Блок finally виконується як у разі виникнення помилки, так і за успішного завершення.", font_size=12, space_after=Pt(10))

    # Дослід 2
    add_p("2. Власний виняток InvalidGradeException та збереження причини (Exception Chaining)", font_size=12, bold=True, space_after=Pt(4))
    add_p("Клас власного винятку з підтримкою Throwable cause:", font_size=12, space_after=Pt(2))
    add_code_block("package exception;\n\npublic class InvalidGradeException extends Exception {\n    public InvalidGradeException(String message) {\n        super(message);\n    }\n    public InvalidGradeException(String message, Throwable cause) {\n        super(message, cause);\n    }\n}")

    add_p("Методи парсингу в класі GradeService:", font_size=12, space_after=Pt(2))
    add_code_block("public int parseGradeWithoutCause(String input) throws InvalidGradeException {\n    try {\n        return Integer.parseInt(input);\n    } catch (NumberFormatException e) {\n        throw new InvalidGradeException(e.getMessage());\n    }\n}\n\npublic int parseGradeWithCause(String input) throws InvalidGradeException {\n    try {\n        return Integer.parseInt(input);\n    } catch (NumberFormatException e) {\n        throw new InvalidGradeException(\"Некоректна оцінка студента: '\" + input + \"'\", e);\n    }\n}")

    add_p("Порівняння двох способів перекидання винятку при розборі некоректного рядка \"abc\":", font_size=12, space_after=Pt(2))
    add_p("Спосіб 1: throw new InvalidGradeException(e.getMessage())", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("exception.InvalidGradeException: For input string: \"abc\"\n\tat service.GradeService.parseGradeWithoutCause(GradeService.java:23)\n\tat app.Main.runTask2(Main.java:90)\n\tat app.Main.main(Main.java:23)")
    add_p("Спостереження: Причину втрачено. У стектрейсі відсутній тип первинного винятку (NumberFormatException) та місце його виникнення в Integer.parseInt.", font_size=12, space_after=Pt(4))

    add_p("Спосіб 2: throw new InvalidGradeException(\"Некоректна оцінка студента\", e)", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("exception.InvalidGradeException: Некоректна оцінка студента: 'abc'\n\tat service.GradeService.parseGradeWithCause(GradeService.java:31)\n\tat app.Main.runTask2(Main.java:101)\n\tat app.Main.main(Main.java:23)\nCaused by: java.lang.NumberFormatException: For input string: \"abc\"\n\tat java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)\n\tat java.base/java.lang.Integer.parseInt(Integer.java:662)\n\tat java.base/java.lang.Integer.parseInt(Integer.java:778)\n\tat service.GradeService.parseGradeWithCause(GradeService.java:29)\n\t... 2 more")
    add_p("Спостереження: Причину збережено через блок Caused by із повним технічним стектрейсом першопричини.", font_size=12, space_after=Pt(10))

    # Дослід 3
    add_p("3. Робота з файлами: буферизація, try-with-resources та кодування", font_size=12, bold=True, space_after=Pt(4))
    
    add_p("3.1. Запис через BufferedWriter без закриття потоку (без close()):", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));\nwriter.write(grade.toString());\nwriter.newLine();")
    add_p("Результат перевірки: Розмір файлу grades_unclosed.txt на диску складає 0 байт.", font_size=12, italic=True, space_after=Pt(2))
    add_p("Причина: Дані залишилися у внутрішньому буфері пам'яті (8 КБ) і не були скинуті на диск, оскільки close() або flush() не викликалися.", font_size=12, space_after=Pt(6))

    add_p("3.2. Запис через try-with-resources:", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {\n    writer.write(grade.toString());\n    writer.newLine();\n}")
    add_p("Результат перевірки: Розмір файлу grades_safe.txt на диску складає 99 байт.", font_size=12, italic=True, space_after=Pt(2))
    add_p("Причина: Конструкція try-with-resources автоматично викликала close(), що забезпечило скидання буфера на диск.", font_size=12, space_after=Pt(6))

    add_p("3.3. Вплив кодування при читанні кирилиці (UTF-8 проти windows-1251):", font_size=12, bold=True, space_after=Pt(2))
    add_code_block("Читання з UTF-8:\nІваненко Петро: 95\nПетренко Марія: 88\nСидоренко Олег: 74\n\nЧитання того самого файлу як windows-1251:\nР†РІР°РЅРµРЅРєРѕ РџРµС‚СЂРѕ: 95\nРџРµС‚СЂРµРЅРєРѕ РњР°СЂС–СЏ: 88\nРЎРёРґРѕСЂРµРЅРєРѕ РћР»РµРі: 74")
    add_p("Пояснення: У UTF-8 символи кирилиці займають по 2 байти. Однобайтне кодування windows-1251 трактує кожен байт окремо, що викликає спотворення тексту.", font_size=12, space_after=Pt(12))

    # Результати
    add_p("Результати роботи програми", font_size=13, bold=True, space_after=Pt(4))
    add_code_block("""Практична робота № 5. Варіант 0: Оцінки студентів, InvalidGradeException

Завдання 1. Дослідження перевірюваних і неперевірюваних винятків
Дослід 1.1. Перевірюваний виняток (Checked Exception) з try-catch:
Перехоплено FileNotFoundException: non_existing_file.txt (The system cannot find the file specified)

Дослід 1.2. Перевірюваний виняток, оголошений у сервісі через throws:
Перехоплено у виклику методу з throws: non_existing_grades.txt (The system cannot find the file specified)

Дослід 1.3. Неперевірюваний виняток (Unchecked / RuntimeException):
Перехоплено ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 3

Дослід 1.4. Демонстрація блоку finally:
1) Випадок з помилкою:
Початок виконання блоку try
Перехоплено помилку: / by zero
Блок finally виконано
2) Випадок без помилки:
Початок виконання блоку try
Успішне завершення блоку try
Блок finally виконано

Завдання 2. Власний виняток та ланцюжок винятків
Дослід 2.1. Перекидання без збереження причини:
Повідомлення: For input string: "abc"
Стектрейс:
exception.InvalidGradeException: For input string: "abc"
	at service.GradeService.parseGradeWithoutCause(GradeService.java:23)
	at app.Main.runTask2(Main.java:90)
	at app.Main.main(Main.java:23)

Дослід 2.2. Перекидання зі збереженням причини:
Повідомлення: Некоректна оцінка студента: 'abc'
Стектрейс:
exception.InvalidGradeException: Некоректна оцінка студента: 'abc'
	at service.GradeService.parseGradeWithCause(GradeService.java:31)
	at app.Main.runTask2(Main.java:101)
	at app.Main.main(Main.java:23)
Caused by: java.lang.NumberFormatException: For input string: "abc"
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
	at java.base/java.lang.Integer.parseInt(Integer.java:662)
	at java.base/java.lang.Integer.parseInt(Integer.java:778)
	at service.GradeService.parseGradeWithCause(GradeService.java:29)
	... 2 more

Завдання 3. Робота з файлами, буферизація та кодування
Дослід 3.1. Запис у файл через BufferedWriter без виклику close():
Розмір файлу grades_unclosed.txt на диску: 0 байт

Дослід 3.2. Запис через try-with-resources:
Розмір файлу grades_safe.txt на диску: 99 байт

Дослід 3.3. Читання файлу з явним кодуванням UTF-8:
Іваненко Петро: 95
Петренко Марія: 88
Сидоренко Олег: 74

Дослід 3.4. Читання того самого файлу як windows-1251:
Р†РІР°РЅРµРЅРєРѕ РџРµС‚СЂРѕ: 95
РџРµС‚СЂРµРЅРєРѕ РњР°СЂС–СЏ: 88
РЎРёРґРѕСЂРµРЅРєРѕ РћР»РµРі: 74""")

    # Контрольні питання
    add_p("Контрольні питання", font_size=13, bold=True, space_after=Pt(6))

    questions = [
        ("1. Чим перевірюваний виняток відрізняється від неперевірюваного і коли який доречний?",
         "Перевірювані винятки (Checked, успадковані від Exception крім RuntimeException) перевіряються компілятором і вимагають обов'язкової обробки (try-catch або throws). Вони доречні для передбачуваних зовнішніх помилок середовища (відсутність файлу, мережевий збій), від яких програма може відновитися. Неперевірювані (Unchecked, нащадки RuntimeException) сигналізують про помилки розробника (ділення на нуль, вихід за межі масиву, NullPointerException); вони не вимагають примусового декларування і зазвичай свідчать про баг у коді."),
        
        ("2. Чому порядок блоків catch має значення?",
         "Тому що винятки перевіряються зверху вниз за ієрархією наслідування до першого збігу. Якщо вище розмістити більш загальний клас (наприклад, catch (Exception e)), він перехопить абсолютно всі винятки, роблячи розташовані нижче спеціалізовані блоки (наприклад, catch (IOException e)) недосяжними, що призводить до помилки компіляції: exception has already been caught."),
        
        ("3. Що втрачається, якщо перекинути виняток лише з повідомленням попереднього?",
         "Втрачається точний тип первинного винятку (наприклад, NumberFormatException) і його оригінальний стектрейс із номером рядка та назвою методу, де саме стався збій. При збереженні причини через конструктор із параметром cause виняток містить секцію Caused by, що дозволяє відстежити повний ланцюжок виникнення проблеми."),
        
        ("4. Чому дані можуть не потрапити у файл, якщо потік не закрито, і як цьому запобігає try-with-resources?",
         "BufferedWriter для оптимізації швидкодії накопичує символи у внутрішньому буфері пам'яті (8 КБ) і записує їх на диск лише при заповненні або виклику flush()/close(). Якщо потік не закрити, залишок даних у буфері втрачається при завершенні програми. Конструкція try-with-resources гарантує автоматичний виклик close() (який завжди робить flush) для будь-якого ресурсу, що реалізує AutoCloseable, навіть якщо виникла помилка."),
        
        ("5. Навіщо задавати кодування явно?",
         "Якщо кодування не вказано явно, Java використовує системне кодування платформи за замовчуванням (у Windows це може бути windows-1251 або CP866). Якщо файл був збережений в іншій системі в UTF-8, на іншій машині виникнуть «кракозябри». Явне задання StandardCharsets.UTF_8 гарантує кросплатформність та однакове коректне відображення тексту на будь-якій ОС.")
    ]

    for q, a in questions:
        add_p(q, font_size=12, bold=True, space_after=Pt(2))
        add_p(a, font_size=12, space_after=Pt(6))

    # Висновок
    add_p("Висновок", font_size=13, bold=True, space_after=Pt(4))
    add_p(
        "Під час виконання практичної роботи було детально досліджено механізми обробки винятків та роботи з файлами в Java. "
        "На практиці перевірено відмінність між перевірюваними (Checked) та неперевірюваними (Unchecked) винятками, а також вимогу суворого порядку блоків catch від специфічного до загального. "
        "Створено власний виняток InvalidGradeException та доведено важливість збереження першопричини (Exception Chaining) через блок Caused by для збереження повного діагностичного стектрейсу. "
        "Також експериментально підтверджено необхідність коректного закриття буферизованих потоків за допомогою try-with-resources та критичну роль явного вказання кодування UTF-8 для запобігання втраті й спотворенню кириличних даних.",
        font_size=12, space_after=Pt(12)
    )

    # Репозиторій
    add_p("Посилання на репозиторій:", font_size=13, bold=True, space_after=Pt(2))
    add_p("https://github.com/Prokhor1337/java/tree/pract-5", font_size=12, space_after=Pt(6))

    doc.save(output_path)
    print("Report generated successfully at:", output_path)

if __name__ == "__main__":
    create_report("C:/Users/PROsha/Desktop/pract-5/pract5_report.docx")
