package service;

import exception.InvalidGradeException;
import model.StudentGrade;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    public void openGradeFileWithThrows(String fileName) throws FileNotFoundException {
        FileReader reader = new FileReader(fileName);
        try {
            reader.close();
        } catch (IOException ignored) {
        }
    }

    public int parseGradeWithoutCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidGradeException(e.getMessage());
        }
    }

    public int parseGradeWithCause(String input) throws InvalidGradeException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidGradeException("Некоректна оцінка студента: '" + input + "'", e);
        }
    }

    public File writeGradesUnclosed(List<StudentGrade> grades, String fileName) throws IOException {
        File file = new File(fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        for (StudentGrade grade : grades) {
            writer.write(grade.toString());
            writer.newLine();
        }
        return file;
    }

    public File writeGradesWithResources(List<StudentGrade> grades, String fileName) throws IOException {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            for (StudentGrade grade : grades) {
                writer.write(grade.toString());
                writer.newLine();
            }
        }
        return file;
    }

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
