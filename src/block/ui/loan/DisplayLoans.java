package block.ui.loan;

import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayLoans {
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void displayLoans() {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM loans")) {
            System.out.println("Library Loans:");
            while (rs.next()) {
                System.out.printf("Loan ID: %d, Member ID: %d, Book ID: %d, Date Loan: %s, Date Due: %s, Date Return: %s, Member Name: %s, Book Title: %s%n",
                        rs.getInt("id"), rs.getInt("member_id"), rs.getInt("book_id"),
                        rs.getString("date_loan"), rs.getString("date_due"),
                        rs.getString("date_return"),
                        rs.getString("member_first_name") + " " + rs.getString("member_last_name"),
                        rs.getString("book_title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
