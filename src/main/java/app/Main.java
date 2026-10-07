package app;

import model.Developer;
import model.Employee;
import model.Manager;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Employee emp = new Employee("Олег");
        emp.work();

        Manager mgr = new Manager("Олег");
        mgr.work();

        System.out.println();
        Employee b = new Manager("Олег");
        System.out.println("b.position -> " + b.position);
        System.out.println("b.describe() -> " + b.describe());

        System.out.println();
        Employee d = new Developer("Іван");
        System.out.println("d.position -> " + d.position);
        System.out.println("d.describe() -> " + d.describe());
        d.work();
    }
}
