package payment;

import java.util.Locale;

public class PayPalPayment extends BasePaymentMethod implements Refundable {
    public PayPalPayment() {
        super("через PayPal");
    }

    @Override
    public void pay(double amount) {
        validateAmount(amount);
        System.out.printf(Locale.US, "-> Оплата %.2f %s%n", amount, getName());
    }

    @Override
    public void refund(double amount) {
        validateAmount(amount);
        System.out.printf(Locale.US, "-> Повернення %.2f на рахунок PayPal виконано%n", amount);
    }
}
