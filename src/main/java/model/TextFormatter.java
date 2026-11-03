package model;

public class TextFormatter implements ReportFormatter {
    @Override
    public String format(String title, String content) {
        return "ЗВІТ: " + title + "\nВміст: " + content;
    }
}
