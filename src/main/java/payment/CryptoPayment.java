package payment;

import java.util.Locale;

public class CryptoPayment extends BasePaymentMethod {
    public CryptoPayment() {
        super("криптовалютою");
    }

    @Override
    public void pay(double amount) {
        validateAmount(amount);
        System.out.printf(Locale.US, "-> Оплата %.2f %s%n", amount, getName());
    }
}
