package block.ui.book;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayBooks {
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void displayBooks() {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
            if (!rs.isBeforeFirst()) {
                System.out.println("No books available.");
            } else {
                System.out.println("Available Books:");
                while (rs.next()) {
                    System.out.printf("ID: %d, Title: %s, Author: %s, Category: %s, Copies: %d%n",
                            rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                            rs.getString("category"), rs.getInt("copies"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
