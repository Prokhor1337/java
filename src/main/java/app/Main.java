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
    public static void processFleet(Transport[] fleet, double distanceKm) {
        for (Transport t : fleet) {
            System.out.printf(Locale.US, "%-10s -> %.2f%n", t.getModel(), t.tripCost(distanceKm));
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Transport[] fleet = {
                new Car("Car", 3.00, 2, 0.25),
                new Truck("Truck", 5.00, 4.0, 1.00),
                new Motorcycle("Motorcycle", 2.00, false)
        };

        processFleet(fleet, 100);

        Transport[] extendedFleet = {
                fleet[0],
                fleet[1],
                fleet[2],
                new Bus("Bus", 4.00, 30)
        };

        System.out.println();
        processFleet(extendedFleet, 100);

        try {
            fleet[0].tripCost(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("спроба розрахувати вартість для від'ємної відстані -> відхилено");
        }

        try {
            new Truck("Truck", -5.0, 2.0, 1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("спроба створити вантажівку з від'ємною ставкою -> відхилено");
        }
    }
}
