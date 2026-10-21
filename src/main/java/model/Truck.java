package model;

public class Truck extends Transport {
    private final double cargoWeightTons;
    private final double feePerTonKm;

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

    public Truck(String model, double baseRatePerKm, double cargoWeightTons) {
        this(model, baseRatePerKm, cargoWeightTons, 1.00);
    }

    public double getCargoWeightTons() {
        return cargoWeightTons;
    }

    public double getFeePerTonKm() {
        return feePerTonKm;
    }

    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        return (getBaseRatePerKm() + cargoWeightTons * feePerTonKm) * distanceKm;
    }
}
