package model;

public class Car extends Transport {
    private final int passengerCount;
    private final double passengerFeePerKm;

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

    public Car(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, 2, 0.25);
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double getPassengerFeePerKm() {
        return passengerFeePerKm;
    }

    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        return (getBaseRatePerKm() + passengerCount * passengerFeePerKm) * distanceKm;
    }
}
