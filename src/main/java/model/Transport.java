package model;

import java.util.Objects;

/**
 * Базовий абстрактний клас транспортного засобу.
 * Інкапсулює спільні дані (назва/модель, базова ставка за км)
 * та спільну поведінку для всієї ієрархії.
 */
public abstract class Transport {
    private final String model;
    private final double baseRatePerKm;

    /**
     * Конструктор базового класу.
     * Ініціалізує та валідує спільні поля.
     *
     * @param model          назва або модель транспортного засобу
     * @param baseRatePerKm  базова вартість за 1 кілометр (має бути > 0)
     */
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

    /**
     * Спільна операція розрахунку вартості поїздки.
     * Контракт базового типу:
     * - Попередня умова (precondition): distanceKm > 0
     * - Постумова (postcondition): результат > 0
     *
     * @param distanceKm відстань поїздки в кілометрах
     * @return розрахована вартість поїздки
     */
    public abstract double tripCost(double distanceKm);

    /**
     * Службовий метод валідації вхідного параметра дистанції.
     * Забезпечує дотримання однакової попередньої умови в усіх нащадках (LSP).
     *
     * @param distanceKm відстань у кілометрах
     */
    protected void validateDistance(double distanceKm) {
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Відстань поїздки повинна бути більшою за 0");
        }
    }

    @Override
    public String toString() {
        return String.format("%s [базова ставка: %.2f грн/км]", model, baseRatePerKm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return Double.compare(transport.baseRatePerKm, baseRatePerKm) == 0 &&
                Objects.equals(model, transport.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, baseRatePerKm);
    }
}
