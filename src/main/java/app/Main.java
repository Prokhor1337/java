package app;

import model.Bus;
import model.Car;
import model.Motorcycle;
import model.Transport;
import model.Truck;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main {

    /**
     * Поліморфний метод обробки масиву об'єктів базового типу.
     * Працює виключно через інтерфейс класу Transport і не знає про конкретні підкласи (LSP, OCP).
     *
     * @param fleet      масив транспортних засобів базового типу
     * @param distanceKm дистанція поїздки
     */
    public static void processFleet(Transport[] fleet, double distanceKm) {
        for (Transport t : fleet) {
            System.out.printf(Locale.US, "%-10s -> %.2f%n", t.getModel(), t.tripCost(distanceKm));
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // 1. Початковий флот транспортних засобів (базовий масив)
        Transport[] fleet = {
                new Car("Car", 3.00, 2, 0.25),
                new Truck("Truck", 5.00, 4.0, 1.00),
                new Motorcycle("Motorcycle", 2.00, false)
        };

        // 2. Поліморфна обробка масиву для поїздки на 100 км
        processFleet(fleet, 100);

        // 3. Демонстрація розширюваності: додавання нового різновиду (Bus)
        // Код методу processFleet залишається абсолютно незмінним!
        System.out.println("\nДодавання нового різновиду (Bus) без зміни коду обробки:");
        Transport[] extendedFleet = {
                new Car("Car", 3.00, 2, 0.25),
                new Truck("Truck", 5.00, 4.0, 1.00),
                new Motorcycle("Motorcycle", 2.00, false),
                new Bus("Bus", 4.00, 30)
        };
        processFleet(extendedFleet, 100);

        // 4. Демонстрація дотримання LSP та валідації вхідних даних
        System.out.println("\nПеревірка захисту стану та контракту методу (LSP):");
        try {
            fleet[0].tripCost(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("спроба розрахувати вартість для від'ємної відстані -> відхилено: " + e.getMessage());
        }

        try {
            new Truck("Truck", -5.0, 2.0, 1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("спроба створити вантажівку з від'ємною ставкою -> відхилено: " + e.getMessage());
        }
    }
}
