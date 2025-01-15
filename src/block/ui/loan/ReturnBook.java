package block.ui.loan;

import block.HelperMethods;
import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReturnBook {
    private static ConnectionManager connectionManager = new ConnectionManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void returnBook() {
        System.out.print("Enter loan ID: ");
        int loanId = scanner.nextInt();
        if (!loanExists(loanId)) {
            System.out.println("Loan not found!");
            return;
        }

        String dateReturnStr = HelperMethods.getCurrentDate();
        Date dateReturn = Date.valueOf(dateReturnStr);
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE loans SET date_return = ? WHERE id = ?")) {
            stmt.setDate(1, dateReturn); // Use setDate instead of setString
            stmt.setInt(2, loanId);
            stmt.executeUpdate();
            int bookId = getBookIdFromLoan(loanId);
            HelperMethods.updateBookCopies(bookId, HelperMethods.getBookCopies(bookId) + 1);
            String dateDue = getLoanDueDate(loanId);
            if (HelperMethods.isLateReturn(dateDue, dateReturnStr)) {
                int daysLate = HelperMethods.calculateDaysLate(dateDue, dateReturnStr);
                int penalty = daysLate * 100;
                System.out.println("Late return. Penalty: " + penalty + " F CFA");
            } else {
                System.out.println("Book returned on time.");
            }
            System.out.println("Book returned: Loan ID " + loanId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean loanExists(int loanId) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM loans WHERE id = ?")) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getBookIdFromLoan(int loanId) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT book_id FROM loans WHERE id = ?")) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("book_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getLoanDueDate(int loanId) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT date_due FROM loans WHERE id = ?")) {
            stmt.setInt(1, loanId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("date_due");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
