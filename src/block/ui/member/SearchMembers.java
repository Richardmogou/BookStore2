package block.ui.member;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchMembers {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void searchMembers() {
        System.out.print("\nEnter search query (First or Last Name): ");
        String query = scanner.nextLine();
        String sql = "SELECT * FROM members WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            System.out.println("Members found:");
            while (rs.next()) {
                System.out.printf("ID: %d, First Name: %s, Last Name: %s, Email: %s, Join Date: %s%n",
                        rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getDate("join_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
