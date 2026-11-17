package app;

import payment.CardPayment;
import payment.CryptoPayment;
import payment.EmailNotifier;
import payment.PayPalPayment;
import payment.PaymentService;
import payment.SmsNotifier;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        PaymentService service = new PaymentService(new CardPayment(), new EmailNotifier());
        service.pay(500.00);

        System.out.println();
        PaymentService serviceWithSms = new PaymentService(new CardPayment(), new SmsNotifier());
        serviceWithSms.pay(250.00);

        System.out.println();
        PaymentService payPalService = new PaymentService(new PayPalPayment(), new EmailNotifier());
        payPalService.pay(1200.00);
        System.out.println("Додано PayPalPayment: PaymentService.java змінено на 0 рядків");

        System.out.println();
        PaymentService cryptoService = new PaymentService(new CryptoPayment(), new SmsNotifier());
        cryptoService.pay(750.00);

        System.out.println();
        System.out.println("Перевірка повернення коштів (Refundable):");
        service.refund(100.00);
        payPalService.refund(200.00);
        cryptoService.refund(50.00);

        System.out.println();
        System.out.printf("Активна конфігурація: %s | %s%n",
                service.getPaymentMethod().getName(),
                service.getNotifier().getClass().getSimpleName());
    }
}
