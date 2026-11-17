package payment;

import java.util.Locale;

@SuppressWarnings("ClassCanBeRecord")
public class PaymentService {
    private final PaymentMethod paymentMethod;
    private final Notifier notifier;

    public PaymentService(PaymentMethod paymentMethod, Notifier notifier) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Платіжний метод не може бути null");
        }
        if (notifier == null) {
            throw new IllegalArgumentException("Сервіс сповіщень не може бути null");
        }
        this.paymentMethod = paymentMethod;
        this.notifier = notifier;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void pay(double amount) {
        paymentMethod.pay(amount);
        notifier.notifyClient(String.format(Locale.US, "клієнта сповіщено про оплату %.2f", amount));
    }

    public void refund(double amount) {
        if (paymentMethod instanceof Refundable refundable) {
            refundable.refund(amount);
            notifier.notifyClient(String.format(Locale.US, "клієнта сповіщено про повернення %.2f", amount));
        } else {
            System.out.printf("-> Повернення коштів неможливе: спосіб оплати '%s' не підтримує повернення%n",
                    paymentMethod.getName());
        }
    }
}
