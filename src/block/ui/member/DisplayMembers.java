package block.ui.member;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayMembers {
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void displayMembers() {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM members")) {
            System.out.println("Library Members:");
            while (rs.next()) {
                System.out.printf("ID: %d, First Name: %s, Last Name: %s, Email: %s, Join Date: %s%n",
                        rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("join_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
