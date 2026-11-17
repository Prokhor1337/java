package payment;

public class SmsNotifier implements Notifier {
    @Override
    public void notifyClient(String message) {
        System.out.printf("-> SMS: %s%n", message);
    }
}
