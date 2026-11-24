package app;

import exception.InvalidGradeException;
import model.StudentGrade;
import service.GradeService;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("================================================================================");
        System.out.println("ПРАКТИЧНА РОБОТА № 5. ВИНЯТКИ, ЗАХИСНЕ ПРОГРАМУВАННЯ ТА ВВІД-ВИВІД");
        System.out.println("Варіант 0: Оцінки студентів / InvalidGradeException");
        System.out.println("================================================================================\n");

        GradeService gradeService = new GradeService();

        runTask1(gradeService);
        runTask2(gradeService);
        runTask3(gradeService);
    }

    // =========================================================================
    // ЗАВДАННЯ 1: Перевірювані та неперевірювані винятки, catch, finally
    // =========================================================================
    private static void runTask1(GradeService gradeService) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 1. Дослідження перевірюваних і неперевірюваних винятків");
        System.out.println("--------------------------------------------------------------------------------");

        // 1.1 Перевірюваний виняток (Checked Exception) з try-catch
        System.out.println("[Дослід 1.1] Перевірюваний виняток (Checked Exception) з try-catch:");
        try {
            FileReader reader = new FileReader("non_existing_file.txt");
            reader.read();
        } catch (FileNotFoundException e) {
            System.out.println("-> Перехоплено FileNotFoundException (checked): " + e.getMessage());
        } catch (IOException e) {
            System.out.println("-> Перехоплено загальний IOException: " + e.getMessage());
        }

        // 1.2 Перевірюваний виняток через throws у сервісі
        System.out.println("\n[Дослід 1.2] Перевірюваний виняток, оголошений у сервісі через throws:");
        try {
            gradeService.openGradeFileWithThrows("non_existing_grades.txt");
        } catch (FileNotFoundException e) {
            System.out.println("-> Перехоплено у виклику методу сервісу з throws: " + e.getMessage());
        }

        // 1.3 Неперевірюваний виняток (Unchecked / RuntimeException)
        System.out.println("\n[Дослід 1.3] Неперевірюваний виняток (Unchecked / RuntimeException):");
        try {
            int[] sampleGrades = {95, 88, 74};
            System.out.println("Спроба отримати оцінку з індексом 5 із масиву довжиною 3...");
            int invalidAccess = sampleGrades[5];
            System.out.println("Оцінка: " + invalidAccess);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("-> Перехоплено ArrayIndexOutOfBoundsException (unchecked): " + e.getMessage());
            System.out.println("   (Код компілюється без обов'язкового try/throws, але завершується аварійно у runtime)");
        }

        // 1.4 Демонстрація виконання блоку finally
        System.out.println("\n[Дослід 1.4] Демонстрація блоку finally:");
        System.out.println("1) Випадок з помилкою:");
        testFinallyBlock(true);

        System.out.println("\n2) Випадок без помилки:");
        testFinallyBlock(false);
    }

    private static void testFinallyBlock(boolean causeError) {
        try {
            System.out.println("   [try] Початок виконання блоку try");
            if (causeError) {
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
    private static void runTask2(GradeService gradeService) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 2. Власний виняток та ланцюжок винятків (Exception Chaining)");
        System.out.println("--------------------------------------------------------------------------------");

        String invalidInput = "abc";

        // Спосіб 1: new InvalidGradeException(e.getMessage()) - втрата причини
        System.out.println("[Дослід 2.1] Перекидання БЕЗ збереження причини (throw new InvalidGradeException(e.getMessage())):");
        try {
            gradeService.parseGradeWithoutCause(invalidInput);
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
            gradeService.parseGradeWithCause(invalidInput);
        } catch (InvalidGradeException e) {
            System.out.println("-> Повідомлення: " + e.getMessage());
            System.out.println("-> Стектрейс (присутній Caused by: NumberFormatException):");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString().trim());
        }
    }

    // =========================================================================
    // ЗАВДАННЯ 3: Робота з файлами (BufferedWriter, try-with-resources, кодування)
    // =========================================================================
    private static void runTask3(GradeService gradeService) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("ЗАВДАННЯ 3. Робота з файлами: буферизація, try-with-resources та кодування");
        System.out.println("--------------------------------------------------------------------------------");

        List<StudentGrade> sampleGrades = new ArrayList<>();
        try {
            sampleGrades.add(new StudentGrade("Іваненко Петро", 95));
            sampleGrades.add(new StudentGrade("Петренко Марія", 88));
            sampleGrades.add(new StudentGrade("Сидоренко Олег", 74));
        } catch (InvalidGradeException e) {
            System.out.println("Помилка створення сутності: " + e.getMessage());
        }

        // 3.1 Запис без закриття потоку (без close())
        System.out.println("[Дослід 3.1] Запис у файл через BufferedWriter БЕЗ виклику close():");
        try {
            File unclosedFile = gradeService.writeGradesUnclosed(sampleGrades, "grades_unclosed.txt");
            System.out.println("-> Записано рядки в буфер через GradeService, але close() не викликано.");
            System.out.println("-> Розмір файлу 'grades_unclosed.txt' на диску: " + unclosedFile.length() + " байт (файл порожній!)");
        } catch (IOException e) {
            System.out.println("Помилка вводу-виводу: " + e.getMessage());
        }

        // 3.2 Запис через try-with-resources
        System.out.println("\n[Дослід 3.2] Запис через try-with-resources (автоматичне закриття потоку):");
        File safeFile = null;
        try {
            safeFile = gradeService.writeGradesWithResources(sampleGrades, "grades_safe.txt");
            System.out.println("-> Потік автоматично закрився після блоку try-with-resources.");
            System.out.println("-> Розмір файлу 'grades_safe.txt' на диску: " + safeFile.length() + " байт (дані скинуто на диск!)");
        } catch (IOException e) {
            System.out.println("Помилка вводу-виводу: " + e.getMessage());
        }

        if (safeFile != null) {
            // 3.3 Читання у кодуванні UTF-8
            System.out.println("\n[Дослід 3.3] Читання файлу з явним кодуванням UTF-8:");
            try {
                List<String> utfLines = gradeService.readGradesWithCharset(safeFile, StandardCharsets.UTF_8);
                for (String line : utfLines) {
                    System.out.println("   [UTF-8 OK]: " + line);
                }
            } catch (IOException e) {
                System.out.println("Помилка читання UTF-8: " + e.getMessage());
            }

            // 3.4 Читання у кодуванні Windows-1251
            System.out.println("\n[Дослід 3.4] Читання того самого файлу як windows-1251 (демонстрація спотворення):");
            try {
                List<String> cp1251Lines = gradeService.readGradesWithCharset(safeFile, Charset.forName("windows-1251"));
                for (String line : cp1251Lines) {
                    System.out.println("   [windows-1251 СПОТВОРЕНО]: " + line);
                }
            } catch (IOException e) {
                System.out.println("Помилка читання windows-1251: " + e.getMessage());
            }
        }

        System.out.println("\n================================================================================");
        System.out.println("ВСІ ДОСЛІДИ УСПІШНО ВИКОНАНО!");
        System.out.println("================================================================================");
    }
}
