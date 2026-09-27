package app;

import model.Book;
import model.ModelTester;

public class Main {
    public static void main(String[] args) {
        ModelTester.testAccess();

        Book book1 = new Book();
        
        System.out.println("Доступ до isAvailable (public): " + book1.isAvailable);
        System.out.println();

        Book book2 = new Book("Кобзар", "Т. Шевченко", 1840, true);
        Book book3 = new Book("Тіні забутих предків", "М. Коцюбинський");
        
        book1.displayInfo();
        book2.displayInfo();
        book3.displayInfo();
        System.out.println();

        System.out.println("Загальна кількість створених книг (static спільне): " + Book.totalBooks);
        System.out.println("ID книги 2 (final власне): " + book2.id);
        
        final int localFinal = 10;
    }
}
