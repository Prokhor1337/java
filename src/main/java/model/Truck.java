package model;

/**
 * Різновид транспорту: Вантажівка (Truck).
 * Специфіка: враховує вагу перевезеного вантажу (у тоннах) та тариф за тонно-кілометр.
 */
public class Truck extends Transport {
    private final double cargoWeightTons;
    private final double feePerTonKm;

    /**
     * Повний конструктор.
     * Ініціалізує базову частину через super та власні специфічні поля.
     *
     * @param model              модель вантажівки
     * @param baseRatePerKm      базова ставка за км
     * @param cargoWeightTons    маса вантажу в тоннах (>= 0)
     * @param feePerTonKm        додатковий тариф за тонну на км (>= 0)
     */
    public Truck(String model, double baseRatePerKm, double cargoWeightTons, double feePerTonKm) {
        super(model, baseRatePerKm);
        if (cargoWeightTons < 0) {
            throw new IllegalArgumentException("Вага вантажу не може бути від'ємною");
        }
        if (feePerTonKm < 0) {
            throw new IllegalArgumentException("Тариф за тонно-кілометр не може бути від'ємним");
        }
        this.cargoWeightTons = cargoWeightTons;
        this.feePerTonKm = feePerTonKm;
    }

    /**
     * Скорочений конструктор з типовим тарифом за тонну (1.00 грн/(т*км)).
     *
     * @param model            модель вантажівки
     * @param baseRatePerKm    базова ставка за км
     * @param cargoWeightTons  маса вантажу в тоннах (>= 0)
     */
    public Truck(String model, double baseRatePerKm, double cargoWeightTons) {
        this(model, baseRatePerKm, cargoWeightTons, 1.00);
    }

    public double getCargoWeightTons() {
        return cargoWeightTons;
    }

    public double getFeePerTonKm() {
        return feePerTonKm;
    }

    /**
     * Перевизначення спільної операції:
     * Вартість включає базову ставку та оплату за перевезення вантажу:
     * cost = (baseRatePerKm + cargoWeightTons * feePerTonKm) * distanceKm
     */
    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        double effectiveRate = getBaseRatePerKm() + (cargoWeightTons * feePerTonKm);
        return effectiveRate * distanceKm;
    }
}
