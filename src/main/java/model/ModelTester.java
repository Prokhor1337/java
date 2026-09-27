package model;

public class ModelTester {
    public static void testAccess() {
        System.out.println("--- Тест доступу з пакету 'model' ---");
        Book book = new Book();
        
        // Доступ до private поля - ПОМИЛКА КОМПІЛЯЦІЇ
        // System.out.println(book.title); // error: title has private access in Book

        // Доступ до default поля - ДОЗВОЛЕНО (той самий пакет)
        System.out.println("Доступ до author (default): " + book.author);

        // Доступ до protected поля - ДОЗВОЛЕНО (той самий пакет)
        System.out.println("Доступ до year (protected): " + book.year);

        // Доступ до public поля - ДОЗВОЛЕНО
        System.out.println("Доступ до isAvailable (public): " + book.isAvailable);
        System.out.println();
    }
}
