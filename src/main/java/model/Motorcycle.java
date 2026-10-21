package model;

/**
 * Різновид транспорту: Мотоцикл (Motorcycle).
 * Специфіка: можливість наявності бокового причепа (коляски),
 * що збільшує опір та витрату палива (коефіцієнт 1.25).
 */
public class Motorcycle extends Transport {
    private final boolean hasSidecar;

    /**
     * Повний конструктор.
     * Ініціалізує базову частину через super та прапорець наявності коляски.
     *
     * @param model          модель мотоцикла
     * @param baseRatePerKm  базова ставка за км
     * @param hasSidecar     наявність бокового причепа
     */
    public Motorcycle(String model, double baseRatePerKm, boolean hasSidecar) {
        super(model, baseRatePerKm);
        this.hasSidecar = hasSidecar;
    }

    /**
     * Скорочений конструктор для мотоцикла без коляски.
     *
     * @param model          модель мотоцикла
     * @param baseRatePerKm  базова ставка за км
     */
    public Motorcycle(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, false);
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    /**
     * Перевизначення спільної операції:
     * Вартість поїздки залежить від наявності коляски (коефіцієнт 1.25, інакше 1.00):
     * cost = baseRatePerKm * multiplier * distanceKm
     */
    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        double multiplier = hasSidecar ? 1.25 : 1.00;
        return getBaseRatePerKm() * multiplier * distanceKm;
    }
}
