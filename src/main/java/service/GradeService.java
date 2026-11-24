package service;

import exception.InvalidGradeException;
import model.StudentGrade;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    // --- Завдання 1: Демонстрація перевірюваного винятку з throws ---
    public void openGradeFileWithThrows(String fileName) throws FileNotFoundException {
        // Метод не перехоплює виняток, а делегує його вище через throws
        FileReader reader = new FileReader(fileName);
    }

    // --- Завдання 2: Парсинг оцінки без збереження причини (втрата контексту) ---
    public int parseGradeWithoutCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Втрачається первинний виняток і його стектрейс
            throw new InvalidGradeException(e.getMessage());
        }
    }

    // --- Завдання 2: Парсинг оцінки зі збереженням причини (Exception Chaining) ---
    public int parseGradeWithCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Зберігається повний ланцюжок помилки (cause)
            throw new InvalidGradeException("Некоректна оцінка студента: '" + input + "'", e);
        }
    }

    // --- Завдання 3: Запис у файл БЕЗ закриття потоку (демонстрація втрати буфера) ---
    public File writeGradesUnclosed(List<StudentGrade> grades, String fileName) throws IOException {
        File file = new File(fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        for (StudentGrade grade : grades) {
            writer.write(grade.toString());
            writer.newLine();
        }
        // УВАГА: writer.close() та writer.flush() навмисно НЕ викликаються!
        return file;
    }

    // --- Завдання 3: Запис у файл через try-with-resources (безпечне скидання буфера) ---
    public File writeGradesWithResources(List<StudentGrade> grades, String fileName) throws IOException {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            for (StudentGrade grade : grades) {
                writer.write(grade.toString());
                writer.newLine();
            }
        } // Тут автоматично викликається writer.close() (і flush())
        return file;
    }

    // --- Завдання 3: Читання файлу із заданим кодуванням ---
    public List<String> readGradesWithCharset(File file, Charset charset) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
