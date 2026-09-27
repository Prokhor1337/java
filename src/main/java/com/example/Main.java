package com.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Забезпечення коректного кодування UTF-8 для введення та виведення
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        scanner.useLocale(Locale.US);

        while (true) {
            System.out.println("=== Конвертер довжини ===");
            System.out.println("Введіть 1 для конвертації, або 0 для виходу");
            System.out.print("Вибір: ");

            if (!scanner.hasNextInt()) {
                System.out.println("-> Помилка: введіть число (0 або 1). Спробуйте ще раз.\n");
                scanner.next(); // Очищуємо некоректне введення
                continue;
            }
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                System.out.println("-> Вихід. Дякуємо за використання!");
                break;
            } else if (choice != 1) {
                System.out.println("-> Помилка: невідомий вибір. Спробуйте ще раз.\n");
                continue;
            }

            System.out.print("Значення: ");
            if (!scanner.hasNextDouble()) {
                System.out.println("-> Помилка: введіть число. Спробуйте ще раз.\n");
                scanner.next(); // Очищуємо некоректне введення
                continue;
            }
            double value = scanner.nextDouble();

            System.out.print("З (mm/cm/m/km/mile): ");
            String fromUnit = scanner.next().toLowerCase();

            System.out.print("У (mm/cm/m/km/mile): ");
            String toUnit = scanner.next().toLowerCase();

            double valueInMeters = toMeters(value, fromUnit);
            if (valueInMeters == -1.0 && !fromUnit.equals("m") && value != -1.0) { // Проста перевірка на помилку
                if (!(value == -1.0 && fromUnit.equals("m"))) {
                    // Якщо toMeters повернуло -1.0 і це не був конверт "-1.0 m"
                    System.out.println("-> Помилка: невідома вихідна одиниця вимірювання.\n");
                    continue;
                }
            }
            
            // Кращий спосіб обробки помилок одиниць вимірювання
            if (!isValidUnit(fromUnit) || !isValidUnit(toUnit)) {
                System.out.println("-> Помилка: невідома одиниця вимірювання. Спробуйте ще раз.\n");
                continue;
            }

            valueInMeters = toMetersSafely(value, fromUnit);
            double result = fromMetersSafely(valueInMeters, toUnit);

            System.out.printf("-> %s %s = %s %s\n\n", 
                formatDouble(value), fromUnit, formatDouble(result), toUnit);
        }
        scanner.close();
    }

    private static boolean isValidUnit(String unit) {
        return unit.equals("mm") || unit.equals("cm") || unit.equals("m") || 
               unit.equals("km") || unit.equals("mile");
    }

    private static double toMetersSafely(double value, String unit) {
        switch (unit) {
            case "mm": return value / 1000.0;
            case "cm": return value / 100.0;
            case "m": return value;
            case "km": return value * 1000.0;
            case "mile": return value * 1609.344;
            default: return 0;
        }
    }

    private static double fromMetersSafely(double valueInMeters, String unit) {
        switch (unit) {
            case "mm": return valueInMeters * 1000.0;
            case "cm": return valueInMeters * 100.0;
            case "m": return valueInMeters;
            case "km": return valueInMeters / 1000.0;
            case "mile": return valueInMeters / 1609.344;
            default: return 0;
        }
    }
    
    // Старий метод залишено для сумісності з попередньою логікою, але не використовується
    private static double toMeters(double value, String unit) {
        return toMetersSafely(value, unit);
    }
    
    private static String formatDouble(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }
}
