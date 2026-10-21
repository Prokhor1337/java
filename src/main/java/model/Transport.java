package model;

public abstract class Transport {
    private final String model;
    private final double baseRatePerKm;

    public Transport(String model, double baseRatePerKm) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Назва/модель транспорту не може бути порожньою");
        }
        if (baseRatePerKm <= 0) {
            throw new IllegalArgumentException("Базова ставка за км повинна бути більшою за 0");
        }
        this.model = model.strip();
        this.baseRatePerKm = baseRatePerKm;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRatePerKm() {
        return baseRatePerKm;
    }

    public abstract double tripCost(double distanceKm);

    protected void validateDistance(double distanceKm) {
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Відстань поїздки повинна бути більшою за 0");
        }
    }
}
