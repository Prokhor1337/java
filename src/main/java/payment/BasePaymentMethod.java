package payment;

public abstract class BasePaymentMethod implements PaymentMethod {
    private final String name;

    public BasePaymentMethod(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва платіжного методу не може бути порожньою");
        }
        this.name = name.strip();
    }

    @Override
    public String getName() {
        return name;
    }

    protected void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сума платежу повинна бути більшою за 0");
        }
    }
}
