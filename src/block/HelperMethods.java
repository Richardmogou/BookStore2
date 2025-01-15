package block;

import block.connection.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;

public class HelperMethods {

   

	public static int getBookCopies(int bookId) {
        try (Connection conn = new ConnectionManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT copies FROM books WHERE id = ?")) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("copies");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateBookCopies(int bookId, int copies) {
        try (Connection conn = new ConnectionManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET copies = ? WHERE id = ?")) {
            stmt.setInt(1, copies);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    public static String getDueDate() {
        return LocalDate.now().plusDays(14).toString();
    }

    public static String getMemberName(int memberId) {
        try (Connection conn = new ConnectionManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT first_name || ' ' || last_name AS name FROM members WHERE id = ?")) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getBookTitle(int bookId) {
        try (Connection conn = new ConnectionManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT title FROM books WHERE id = ?")) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean loanExists(int loanId) {
        try (Connection conn = new ConnectionManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM loans WHERE id = ?")) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getBookIdFromLoan(int loanId) {
        try (Connection conn = new ConnectionManager().getConnection();
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

    public static String getLoanDueDate(int loanId) {
        try (Connection conn = new ConnectionManager().getConnection();
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

    public static boolean isLateReturn(String dateDue, String dateReturn) {
        return LocalDate.parse(dateReturn).isAfter(LocalDate.parse(dateDue));
    }

    public static int calculateDaysLate(String dateDue, String dateReturn) {
        LocalDate dueDate = LocalDate.parse(dateDue);
        LocalDate returnDate = LocalDate.parse(dateReturn);
        return (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
    }
}
