package app;

import model.Developer;
import model.Employee;
import model.Manager;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== Дослід 1: Перевизначення методу та super ===");
        Employee basicEmp = new Employee("Іван");
        System.out.print("Батьківська версія: ");
        basicEmp.work();

        Manager manager = new Manager("Олексій", "IT");
        System.out.println("Похідна версія (з викликом super.work()):");
        manager.work();

        System.out.println("\n=== Дослід 2: Порядок виклику конструкторів ===");
        System.out.println("Створення об'єкта Base b = new Derived(...):");
        Employee emp = new Manager("Богдан", manager.getDepartment());

        System.out.println("\n=== Дослід 3: Поле за типом посилання, метод за фактичним типом ===");
        System.out.println("emp.position -> " + emp.position + " (тип посилання Employee)");
        System.out.println("emp.describe() -> " + emp.describe() + " (фактичний об'єкт Manager)");

        System.out.println("\nДемонстрація для класу Developer:");
        Employee dev = new Developer("Марія", "Backend");
        System.out.println("dev.position -> " + dev.position);
        System.out.println("dev.describe() -> " + dev.describe());
        dev.work();

        Developer actualDev = (Developer) dev;
        System.out.println("Спеціалізація розробника: " + actualDev.getSpecialty());
    }
}
