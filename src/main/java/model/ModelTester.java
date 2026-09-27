package model;

public class ModelTester {
    public static void testAccess() {
        Book book = new Book();
        
        System.out.println("Доступ до author (default): " + book.author);
        System.out.println("Доступ до year (protected): " + book.year);
        System.out.println("Доступ до isAvailable (public): " + book.isAvailable);
        System.out.println();
    }
}
