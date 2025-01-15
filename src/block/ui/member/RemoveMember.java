package block.ui.member;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveMember {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void removeMember() {
        System.out.print("Enter member ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM members WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Member removed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
