package model;

public class TableFormatter implements ReportFormatter {
    @Override
    public String format(String title, String content) {
        return "+--------------------------------+\n" +
               "| " + title + "\n" +
               "+--------------------------------+\n" +
               "| " + content + "\n" +
               "+--------------------------------+";
    }
}
