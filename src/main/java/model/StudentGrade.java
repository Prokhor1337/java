package model;

import exception.InvalidGradeException;

public class StudentGrade {
    private final String studentName;
    private final int grade;

    public StudentGrade(String studentName, int grade) throws InvalidGradeException {
        if (studentName == null || studentName.isBlank()) {
            throw new IllegalArgumentException("Ім'я студента не може бути порожнім");
        }
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException("Оцінка повинна бути в діапазоні від 0 до 100: " + grade);
        }
        this.studentName = studentName.strip();
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return studentName + ": " + grade;
    }
}
