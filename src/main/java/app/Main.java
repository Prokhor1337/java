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

        System.out.println("Практична робота № 5. Варіант 0: Оцінки студентів, InvalidGradeException\n");

        GradeService gradeService = new GradeService();

        runTask1(gradeService);
        runTask2(gradeService);
        runTask3(gradeService);
    }

    private static void runTask1(GradeService gradeService) {
        System.out.println("Завдання 1. Дослідження перевірюваних і неперевірюваних винятків");

        System.out.println("Дослід 1.1. Перевірюваний виняток (Checked Exception) з try-catch:");
        try (FileReader reader = new FileReader("non_existing_file.txt")) {
            int charCode = reader.read();
            System.out.println("Прочитано символ: " + charCode);
        } catch (FileNotFoundException e) {
            System.out.println("Перехоплено FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Перехоплено IOException: " + e.getMessage());
        }

        System.out.println("\nДослід 1.2. Перевірюваний виняток, оголошений у сервісі через throws:");
        try {
            gradeService.openGradeFileWithThrows("non_existing_grades.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Перехоплено у виклику методу з throws: " + e.getMessage());
        }

        System.out.println("\nДослід 1.3. Неперевірюваний виняток (Unchecked / RuntimeException):");
        try {
            int[] sampleGrades = {95, 88, 74};
            int invalidIndex = sampleGrades.length + 2;
            int invalidAccess = sampleGrades[invalidIndex];
            System.out.println("Оцінка: " + invalidAccess);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Перехоплено ArrayIndexOutOfBoundsException: " + e.getMessage());
        }

        System.out.println("\nДослід 1.4. Демонстрація блоку finally:");
        System.out.println("1) Випадок з помилкою:");
        testFinallyBlock(true);

        System.out.println("2) Випадок без помилки:");
        testFinallyBlock(false);
    }

    private static void testFinallyBlock(boolean causeError) {
        try {
            System.out.println("Початок виконання блоку try");
            if (causeError) {
                int denominator = 0;
                int res = 100 / denominator;
                System.out.println("Результат: " + res);
            }
            System.out.println("Успішне завершення блоку try");
        } catch (ArithmeticException e) {
            System.out.println("Перехоплено помилку: " + e.getMessage());
        } finally {
            System.out.println("Блок finally виконано");
        }
    }

    private static void runTask2(GradeService gradeService) {
        System.out.println("\nЗавдання 2. Власний виняток та ланцюжок винятків");

        String invalidInput = "abc";

        System.out.println("Дослід 2.1. Перекидання без збереження причини:");
        try {
            int parsedGrade = gradeService.parseGradeWithoutCause(invalidInput);
            System.out.println("Отримано оцінку: " + parsedGrade);
        } catch (InvalidGradeException e) {
            System.out.println("Повідомлення: " + e.getMessage());
            System.out.println("Стектрейс:");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString().trim());
        }

        System.out.println("\nДослід 2.2. Перекидання зі збереженням причини:");
        try {
            int parsedGrade = gradeService.parseGradeWithCause(invalidInput);
            System.out.println("Отримано оцінку: " + parsedGrade);
        } catch (InvalidGradeException e) {
            System.out.println("Повідомлення: " + e.getMessage());
            System.out.println("Стектрейс:");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println(sw.toString().trim());
        }
    }

    private static void runTask3(GradeService gradeService) {
        System.out.println("\nЗавдання 3. Робота з файлами, буферизація та кодування");

        List<StudentGrade> sampleGrades = new ArrayList<>();
        try {
            sampleGrades.add(new StudentGrade("Іваненко Петро", 95));
            sampleGrades.add(new StudentGrade("Петренко Марія", 88));
            sampleGrades.add(new StudentGrade("Сидоренко Олег", 74));
        } catch (InvalidGradeException e) {
            System.out.println("Помилка створення сутності: " + e.getMessage());
        }

        System.out.println("Дослід 3.1. Запис у файл через BufferedWriter без виклику close():");
        try {
            File unclosedFile = gradeService.writeGradesUnclosed(sampleGrades, "grades_unclosed.txt");
            System.out.println("Розмір файлу grades_unclosed.txt на диску: " + unclosedFile.length() + " байт");
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("\nДослід 3.2. Запис через try-with-resources:");
        File safeFile = null;
        try {
            safeFile = gradeService.writeGradesWithResources(sampleGrades, "grades_safe.txt");
            System.out.println("Розмір файлу grades_safe.txt на диску: " + safeFile.length() + " байт");
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        if (safeFile != null) {
            System.out.println("\nДослід 3.3. Читання файлу з явним кодуванням UTF-8:");
            try {
                List<String> utfLines = gradeService.readGradesWithCharset(safeFile, StandardCharsets.UTF_8);
                for (String line : utfLines) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Помилка читання UTF-8: " + e.getMessage());
            }

            System.out.println("\nДослід 3.4. Читання того самого файлу як windows-1251:");
            try {
                List<String> cp1251Lines = gradeService.readGradesWithCharset(safeFile, Charset.forName("windows-1251"));
                for (String line : cp1251Lines) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Помилка читання windows-1251: " + e.getMessage());
            }
        }
    }
}
