package model;

/**
 * Додатковий різновид транспорту: Автобус (Bus).
 * Створений для демонстрації відкритості до розширення (Open-Closed Principle):
 * додавання нового класу не вимагає змін у клієнтському коді обробки масиву.
 * Специфіка: враховує кількість пасажирських місць.
 */
public class Bus extends Transport {
    private final int seatingCapacity;

    /**
     * Повний конструктор.
     * Ініціалізує базову частину через super та кількість посадкових місць.
     *
     * @param model            модель автобуса
     * @param baseRatePerKm    базова ставка за км
     * @param seatingCapacity  кількість посадкових місць (>= 0)
     */
    public Bus(String model, double baseRatePerKm, int seatingCapacity) {
        super(model, baseRatePerKm);
        if (seatingCapacity < 0) {
            throw new IllegalArgumentException("Місткість автобуса не може бути від'ємною");
        }
        this.seatingCapacity = seatingCapacity;
    }

    /**
     * Скорочений конструктор з типовою місткістю мікроавтобуса (18 місць).
     *
     * @param model          модель автобуса
     * @param baseRatePerKm  базова ставка за км
     */
    public Bus(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, 18);
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    /**
     * Перевизначення спільної операції:
     * Вартість включає базову ставку та невелику надбавку за обслуговування кожного місця:
     * cost = (baseRatePerKm + seatingCapacity * 0.10) * distanceKm
     */
    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        double effectiveRate = getBaseRatePerKm() + (seatingCapacity * 0.10);
        return effectiveRate * distanceKm;
    }
}
