package model;

public class Book {
    private String title;
    String author;
    protected int year;
    public boolean isAvailable;

    public static int totalBooks = 0;
    public final int id;

    public Book() {
        this.title = "Невідома назва";
        this.author = "Невідомий автор";
        this.year = 2000;
        this.isAvailable = false;
        
        totalBooks++;
        this.id = totalBooks;
    }

    public Book(String title, String author, int year, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = isAvailable;
        
        totalBooks++;
        this.id = totalBooks;
    }

    public Book(String title, String author) {
        this(title, author, 2026, true);
    }

    public void displayInfo() {
        System.out.printf("Книга [ID: %d]: '%s', Автор: %s, Рік: %d, Доступна: %b\n", 
            id, title, author, year, isAvailable);
    }
}
