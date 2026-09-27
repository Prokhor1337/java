package app;

import model.Book;
import model.ModelTester;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Практична робота №2 ===");

        // Виклик тесту з пакету model
        ModelTester.testAccess();

        System.out.println("--- Тест доступу з пакету 'app' ---");
        Book book1 = new Book();
        
        // Доступ до private поля - ПОМИЛКА
        // System.out.println(book1.title); 

        // Доступ до default поля - ПОМИЛКА (інший пакет)
        // System.out.println(book1.author);

        // Доступ до protected поля - ПОМИЛКА (інший пакет, не спадкоємець)
        // System.out.println(book1.year);

        // Доступ до public поля - ДОЗВОЛЕНО
        System.out.println("Доступ до isAvailable (public): " + book1.isAvailable);
        System.out.println();

        System.out.println("--- Демонстрація конструкторів ---");
        Book book2 = new Book("Кобзар", "Т. Шевченко", 1840, true);
        Book book3 = new Book("Тіні забутих предків", "М. Коцюбинський");
        
        book1.displayInfo();
        book2.displayInfo();
        book3.displayInfo();
        System.out.println();

        System.out.println("--- Демонстрація static та final ---");
        System.out.println("Загальна кількість створених книг (static спільне): " + Book.totalBooks);
        System.out.println("ID книги 2 (final власне): " + book2.id);
        
        // Спроба змінити final змінну - ПОМИЛКА КОМПІЛЯЦІЇ
        // book2.id = 99; // error: cannot assign a value to final variable id

        final int localFinal = 10;
        // localFinal = 20; // error: cannot assign a value to final variable localFinal
    }
}
