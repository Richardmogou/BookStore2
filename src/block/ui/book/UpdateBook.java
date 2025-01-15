package block.ui.book;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class UpdateBook {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void updateBook() {
        System.out.print("Enter book ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("New Title (leave blank to keep current): ");
        String title = scanner.nextLine();
        System.out.print("New Author (leave blank to keep current): ");
        String author = scanner.nextLine();
        System.out.print("New Category (leave blank to keep current): ");
        String category = scanner.nextLine();
        System.out.print("New Number of Copies (leave blank to keep current): ");
        String copiesStr = scanner.nextLine();
        Integer copies = copiesStr.isEmpty() ? null : Integer.parseInt(copiesStr);
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET title = COALESCE(NULLIF(?, ''), title), author = COALESCE(NULLIF(?, ''), author), category = COALESCE(NULLIF(?, ''), category), copies = COALESCE(?, copies) WHERE id = ?")) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            if (copies != null) {
                stmt.setInt(4, copies);
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Book updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
