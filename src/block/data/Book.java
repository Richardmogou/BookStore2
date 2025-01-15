package block.data;

import block.ui.book.BookCategory;
import block.ui.book.Item;

public class Book extends Item {
    private String author;
    private BookCategory category;
    private int copies;

    public Book(int id, String title, String author, BookCategory category, int copies) {
        super(id, title);
        this.author = author;
        this.category = category;
        this.copies = copies;
    }

    @Override
    public void display() {
        System.out.printf("ID: %d, Title: %s, Author: %s, Category: %s, Copies: %d%n", id, title, author, category, copies);
    }

    // Getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
