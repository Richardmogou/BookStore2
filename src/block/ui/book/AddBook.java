package block.ui.book;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddBook {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void addBook() {
        System.out.println("\nEnter book details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();

        BookCategory category = null;
        while (category == null) {
            System.out.print("Category (choose from: ");
            for (BookCategory c : BookCategory.values()) {
                System.out.print(c.name() + " ");
            }
            System.out.print("): ");
            String categoryInput = scanner.nextLine().toUpperCase();
            try {
                category = BookCategory.valueOf(categoryInput);
            } catch (IllegalArgumentException e) {
                System.out.println("This is not part of category: " + categoryInput);
            }
        }

        System.out.print("Number of Copies: ");
        int copies = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, author, category, copies) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category.name());
            stmt.setInt(4, copies);
            stmt.executeUpdate();
            System.out.println("Book added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
