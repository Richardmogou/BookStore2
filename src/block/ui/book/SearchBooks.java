package block.ui.book;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchBooks {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void searchBooks() {
        System.out.print("\nEnter search keyword: ");
        String query = scanner.nextLine();
        System.out.println("\nSearch by:\n1. Title\n2. Category");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String sql = switch (option) {
            case 1 -> "SELECT title FROM books WHERE LOWER(title) LIKE LOWER(?)";
            case 2 -> "SELECT category FROM books WHERE LOWER(category) LIKE LOWER(?)";
            default -> {
                System.out.println("Invalid option. Please choose 1 for Title or 2 for Category.");
                yield null;
            }
        };
        if (sql != null) {
            try (Connection conn = connectionManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + query + "%");
                ResultSet rs = stmt.executeQuery();
                if (!rs.isBeforeFirst()) { // Check if the result set is empty
                    System.out.println("No books found.");
                } else {
                    if (option == 1) {
                        System.out.println("Books found by Title:");
                        while (rs.next()) {
                            System.out.printf("Title: %s%n", rs.getString("title"));
                        }
                    } else if (option == 2) {
                        System.out.println("Books found by Category:");
                        while (rs.next()) {
                            System.out.printf("Category: %s%n", rs.getString("category"));
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
