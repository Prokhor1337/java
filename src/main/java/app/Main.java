package app;

import exception.InvalidGradeException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("================================================================================");
        System.out.println("ПРАКТИЧНА РОБОТА № 5. ВИНЯТКИ, ЗАХИСНЕ ПРОГРАМУВАННЯ ТА ВВІД-ВИВІД");
        System.out.println("Варіант 0: Оцінки студентів / InvalidGradeException");
        System.out.println("================================================================================\n");

        runTask1Experiments();
        runTask2Experiments();
        runTask3Experiments();
    }

    // =========================================================================
    // ЗАВДАННЯ 1: Перевірювані та неперевірювані винятки, catch, finally
    // =========================================================================
    private static void runTask1Experiments() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 1. Дослідження перевірюваних і неперевірюваних винятків");
        System.out.println("--------------------------------------------------------------------------------");

        // 1.1 Перевірюваний виняток: обробка через try-catch
        System.out.println("[Дослід 1.1] Перевірюваний виняток (Checked Exception) з try-catch:");
        try {
            FileReader reader = new FileReader("non_existing_file.txt");
            reader.read();
        } catch (FileNotFoundException e) {
            System.out.println("-> Перехоплено FileNotFoundException (checked): " + e.getMessage());
        } catch (IOException e) {
            System.out.println("-> Перехоплено загальний IOException: " + e.getMessage());
        }

        // 1.2 Перевірюваний виняток: передача через throws
        System.out.println("\n[Дослід 1.2] Перевірюваний виняток, оголошений через throws:");
        try {
            openFileWithThrows("non_existing_grades.txt");
        } catch (FileNotFoundException e) {
            System.out.println("-> Перехоплено у виклику методу з throws: " + e.getMessage());
        }

        // 1.3 Неперевірюваний виняток (Unchecked Exception)
        System.out.println("\n[Дослід 1.3] Неперевірюваний виняток (Unchecked / RuntimeException):");
        try {
            int[] grades = {95, 88, 74};
            System.out.println("Спроба отримати оцінку з індексом 5 із масиву довжиною 3...");
            int invalidAccess = grades[5]; // ArrayIndexOutOfBoundsException
            System.out.println("Оцінка: " + invalidAccess);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("-> Перехоплено ArrayIndexOutOfBoundsException (unchecked): " + e.getMessage());
            System.out.println("   (Код компілюється без примусового try/throws, але падає під час виконання)");
        }

        // 1.4 Демонстрація виконання finally при винятку та без нього
        System.out.println("\n[Дослід 1.4] Демонстрація блоку finally:");
        System.out.println("1) Випадок з помилкою:");
        executeWithFinally(true);

        System.out.println("\n2) Випадок без помилки:");
        executeWithFinally(false);
    }

    private static void openFileWithThrows(String fileName) throws FileNotFoundException {
        // Метод не обробляє виняток сам, а делегує його нагору за стеком викликів
        FileReader reader = new FileReader(fileName);
    }

    private static void executeWithFinally(boolean triggerError) {
        try {
            System.out.println("   [try] Початок виконання блоку try");
            if (triggerError) {
                int error = 100 / 0; // ArithmeticException
            }
            System.out.println("   [try] Успішне завершення блоку try");
        } catch (ArithmeticException e) {
            System.out.println("   [catch] Перехоплено помилку: " + e.getMessage());
        } finally {
            System.out.println("   [finally] Блок finally гарантовано виконано!");
        }
    }

    // =========================================================================
    // ЗАВДАННЯ 2: Власний виняток InvalidGradeException та збереження причини
    // =========================================================================
    private static void runTask2Experiments() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 2. Власний виняток та ланцюжок винятків (Exception Chaining)");
        System.out.println("--------------------------------------------------------------------------------");

        String invalidGradeInput = "abc";

        // Спосіб 1: new InvalidGradeException(e.getMessage()) - втрата причини
        System.out.println("[Дослід 2.1] Перекидання БЕЗ збереження причини (throw new InvalidGradeException(e.getMessage())):");
        try {
            parseGradeWithoutCause(invalidGradeInput);
        } catch (InvalidGradeException e) {
            System.out.println("-> Повідомлення: " + e.getMessage());
            System.out.println("-> Стектрейс (причину NumberFormatException та її місце втрачено):");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString().trim());
        }

        // Спосіб 2: new InvalidGradeException("...", e) - збереження причини
        System.out.println("\n[Дослід 2.2] Перекидання ЗІ збереженням причини (throw new InvalidGradeException(\"...\", e)):");
        try {
            parseGradeWithCause(invalidGradeInput);
        } catch (InvalidGradeException e) {
            System.out.println("-> Повідомлення: " + e.getMessage());
            System.out.println("-> Стектрейс (присутній Caused by: NumberFormatException):");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString().trim());
        }
    }

    private static int parseGradeWithoutCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Втрачається первинний виняток і його стектрейс
            throw new InvalidGradeException(e.getMessage());
        }
    }

    private static int parseGradeWithCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Зберігається повний ланцюжок помилки (cause)
            throw new InvalidGradeException("Некоректна оцінка студента: '" + input + "'", e);
        }
    }

    // =========================================================================
    // ЗАВДАННЯ 3: Робота з файлами (BufferedWriter, try-with-resources, кодування)
    // =========================================================================
    private static void runTask3Experiments() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 3. Робота з файлами: буферизація, try-with-resources та кодування");
        System.out.println("--------------------------------------------------------------------------------");

        File unclosedFile = new File("grades_unclosed.txt");
        File safeFile = new File("grades_safe.txt");

        // 3.1 Запис без закриття потоку (без close())
        System.out.println("[Дослід 3.1] Запис у файл через BufferedWriter БЕЗ виклику close():");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(unclosedFile, StandardCharsets.UTF_8));
            writer.write("Іваненко Петро: 95");
            writer.newLine();
            writer.write("Петренко Марія: 88");
            writer.newLine();
            // НЕ викликаємо writer.close() та writer.flush()
            System.out.println("-> Записано рядки в буфер, але close() не викликано.");
            System.out.println("-> Розмір файлу 'grades_unclosed.txt' на диску: " + unclosedFile.length() + " байт (файл порожній!)");
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        // 3.2 Запис через try-with-resources
        System.out.println("\n[Дослід 3.2] Запис через try-with-resources (автоматичне закриття потоку):");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(safeFile, StandardCharsets.UTF_8))) {
            writer.write("Іваненко Петро: 95");
            writer.newLine();
            writer.write("Петренко Марія: 88");
            writer.newLine();
            writer.write("Сидоренко Олег: 74");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
        System.out.println("-> Потік автоматично закрився після блоку try.");
        System.out.println("-> Розмір файлу 'grades_safe.txt' на диску: " + safeFile.length() + " байт (дані скинуто на диск!)");

        // 3.3 Читання у кодуванні UTF-8
        System.out.println("\n[Дослід 3.3] Читання файлу з явним кодуванням UTF-8:");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(safeFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   [UTF-8 OK]: " + line);
            }
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        // 3.4 Читання у кодуванні Windows-1251
        System.out.println("\n[Дослід 3.4] Читання того самого файлу як windows-1251 (демонстрація спотворення):");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(safeFile), Charset.forName("windows-1251")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   [windows-1251 СПОТВОРЕНО]: " + line);
            }
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("\n================================================================================");
        System.out.println("ВСІ ДОСЛІДИ УСПІШНО ВИКОНАНО!");
        System.out.println("================================================================================");
    }
}
