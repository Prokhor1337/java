package model;

/**
 * Різновид транспорту: Легковий автомобіль (Car).
 * Специфіка: враховує кількість пасажирів та надбавку за пасажиро-кілометр.
 */
public class Car extends Transport {
    private final int passengerCount;
    private final double passengerFeePerKm;

    /**
     * Повний конструктор.
     * Ініціалізує базову частину через super та власні специфічні поля.
     *
     * @param model               модель автомобіля
     * @param baseRatePerKm       базова ставка за км
     * @param passengerCount      кількість пасажирів (>= 0)
     * @param passengerFeePerKm   надбавка за пасажира на км (>= 0)
     */
    public Car(String model, double baseRatePerKm, int passengerCount, double passengerFeePerKm) {
        super(model, baseRatePerKm);
        if (passengerCount < 0) {
            throw new IllegalArgumentException("Кількість пасажирів не може бути від'ємною");
        }
        if (passengerFeePerKm < 0) {
            throw new IllegalArgumentException("Надбавка за пасажира не може бути від'ємною");
        }
        this.passengerCount = passengerCount;
        this.passengerFeePerKm = passengerFeePerKm;
    }

    /**
     * Скорочений конструктор з типовими значеннями для пасажирів.
     *
     * @param model          модель автомобіля
     * @param baseRatePerKm  базова ставка за км
     */
    public Car(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, 2, 0.25);
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double getPassengerFeePerKm() {
        return passengerFeePerKm;
    }

    /**
     * Перевизначення спільної операції:
     * Вартість розраховується з урахуванням базової ставки та надбавки за пасажирів:
     * cost = (baseRatePerKm + passengerCount * passengerFeePerKm) * distanceKm
     */
    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        double effectiveRate = getBaseRatePerKm() + (passengerCount * passengerFeePerKm);
        return effectiveRate * distanceKm;
    }
}
