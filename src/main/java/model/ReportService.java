package model;

public class ReportService {
    private final ReportFormatter formatter;

    public ReportService(ReportFormatter formatter) {
        if (formatter == null) {
            throw new IllegalArgumentException("Форматувальник не може бути null");
        }
        this.formatter = formatter;
    }

    public String generateReport(String title, String content) {
        return formatter.format(title, content);
    }
}
