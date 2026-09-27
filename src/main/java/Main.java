import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        scanner.useLocale(Locale.US);

        while (true) {
            System.out.println("Конвертер довжини");
            System.out.println("1 - конвертація, 0 - вихід");
            System.out.print("Вибір: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Помилка: введіть число.\n");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            
            if (choice == 0) {
                System.out.println("Вихід.");
                break;
            } else if (choice != 1) {
                System.out.println("Помилка: невідомий вибір.\n");
                continue;
            }

            System.out.print("Значення: ");
            if (!scanner.hasNextDouble()) {
                System.out.println("Помилка: введіть число.\n");
                scanner.next();
                continue;
            }
            double value = scanner.nextDouble();

            System.out.print("З (mm/cm/m/km/mile): ");
            String fromUnit = scanner.next().toLowerCase();

            System.out.print("У (mm/cm/m/km/mile): ");
            String toUnit = scanner.next().toLowerCase();

            if (isInvalidUnit(fromUnit) || isInvalidUnit(toUnit)) {
                System.out.println("Помилка: невідома одиниця вимірювання.\n");
                continue;
            }

            double valueInMeters = toMetersSafely(value, fromUnit);
            double result = fromMetersSafely(valueInMeters, toUnit);

            System.out.printf("%s %s = %s %s\n\n", 
                formatDouble(value), fromUnit, formatDouble(result), toUnit);
        }
        scanner.close();
    }

    private static boolean isInvalidUnit(String unit) {
        return !unit.equals("mm") && !unit.equals("cm") && !unit.equals("m") && 
               !unit.equals("km") && !unit.equals("mile");
    }

    private static double toMetersSafely(double value, String unit) {
        return switch (unit) {
            case "mm" -> value / 1000.0;
            case "cm" -> value / 100.0;
            case "m" -> value;
            case "km" -> value * 1000.0;
            case "mile" -> value * 1609.344;
            default -> 0;
        };
    }

    private static double fromMetersSafely(double valueInMeters, String unit) {
        return switch (unit) {
            case "mm" -> valueInMeters * 1000.0;
            case "cm" -> valueInMeters * 100.0;
            case "m" -> valueInMeters;
            case "km" -> valueInMeters / 1000.0;
            case "mile" -> valueInMeters / 1609.344;
            default -> 0;
        };
    }
    
    private static String formatDouble(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }
}
