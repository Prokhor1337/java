package app;

import model.Circle;
import model.Drawable;
import model.ReportService;
import model.Scalable;
import model.Shape;
import model.TableFormatter;
import model.TextFormatter;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Shape shape = new Circle(5.0);
        System.out.println("Фігура: " + shape.getName());
        System.out.printf("Площа: %.2f%n", shape.area());

        Circle circle = new Circle(3.0);
        Drawable d = circle;
        d.draw();

        Scalable s = circle;
        s.scale(2.0);
        System.out.printf("Радіус після масштабування: %.2f%n", circle.getRadius());
        System.out.println("Опис: " + circle.describe());

        System.out.println();
        ReportService textReportService = new ReportService(new TextFormatter());
        System.out.println(textReportService.generateReport("Звіт за місяць", "Усі показники в нормі"));

        System.out.println();
        ReportService tableReportService = new ReportService(new TableFormatter());
        System.out.println(tableReportService.generateReport("Звіт за місяць", "Усі показники в нормі"));
    }
}
