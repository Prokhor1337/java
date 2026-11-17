package payment;

import java.util.Locale;

public class CardPayment extends BasePaymentMethod implements Refundable {
    public CardPayment() {
        super("карткою");
    }

    @Override
    public void pay(double amount) {
        validateAmount(amount);
        System.out.printf(Locale.US, "-> Оплата %.2f %s%n", amount, getName());
    }

    @Override
    public void refund(double amount) {
        validateAmount(amount);
        System.out.printf(Locale.US, "-> Повернення %.2f на банківську картку виконано%n", amount);
    }
}
