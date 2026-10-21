package model;

public class Bus extends Transport {
    private final int seatingCapacity;

    public Bus(String model, double baseRatePerKm, int seatingCapacity) {
        super(model, baseRatePerKm);
        if (seatingCapacity < 0) {
            throw new IllegalArgumentException("Місткість автобуса не може бути від'ємною");
        }
        this.seatingCapacity = seatingCapacity;
    }

    public Bus(String model, double baseRatePerKm) {
        this(model, baseRatePerKm, 18);
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    @Override
    public double tripCost(double distanceKm) {
        validateDistance(distanceKm);
        return (getBaseRatePerKm() + seatingCapacity * 0.10) * distanceKm;
    }
}
