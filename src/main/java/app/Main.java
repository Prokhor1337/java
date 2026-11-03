package app;

import model.Circle;
import model.Drawable;
import model.Rectangle;
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
        System.out.println("Через Drawable: " + d.describe());

        Scalable s = circle;
        s.scale(2.0);
        System.out.printf("Радіус після масштабування: %.2f%n", circle.getRadius());
        System.out.println("Через Scalable: " + s.describe());

        System.out.println();
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        System.out.printf("Прямокутник: %s, площа: %.2f (розмір: %.2f x %.2f)%n",
                rectangle.getName(), rectangle.area(), rectangle.getWidth(), rectangle.getHeight());
        rectangle.draw();
        rectangle.scale(1.5);
        System.out.println("Опис прямокутника: " + rectangle.describe());

        System.out.println();
        ReportService textReportService = new ReportService(new TextFormatter());
        System.out.println(textReportService.generateReport("Звіт за місяць", "Усі показники в нормі"));

        System.out.println();
        ReportService tableReportService = new ReportService(new TableFormatter());
        System.out.println(tableReportService.generateReport("Звіт за місяць", "Усі показники в нормі"));
    }
}
