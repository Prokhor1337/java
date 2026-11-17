package payment;

public class EmailNotifier implements Notifier {
    @Override
    public void notifyClient(String message) {
        System.out.printf("-> Email: %s%n", message);
    }
}
