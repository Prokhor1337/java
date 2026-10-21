package model;

public class Motorcycle extends Transport {
    private final boolean hasSidecar;

    public Motorcycle(String model, double baseRatePerKm, boolean hasSidecar) {
        super(model, baseRatePerKm);
        this.hasSidecar = hasSidecar;
    }

    public Motorcycle(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, false);
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        double multiplier = hasSidecar ? 1.25 : 1.00;
        return getBaseRatePerKm() * multiplier * distanceKm;
    }
}
