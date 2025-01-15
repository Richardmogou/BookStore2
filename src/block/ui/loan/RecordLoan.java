package block.ui.loan;

import block.HelperMethods;
import block.connection.ConnectionManager;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RecordLoan {
    private static ConnectionManager connectionManager = new ConnectionManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void recordLoan() {
        System.out.print("Enter member ID: ");
        int memberId = getIntInput();
        if (memberId == -1) {
            System.out.println("Invalid input for member ID. Returning to main menu.");
            return;
        }
        if (!memberExists(memberId)) {
            System.out.println("Member not found! Returning to main menu.");
            return;
        }
        System.out.print("Enter book ID: ");
        int bookId = getIntInput();
        if (bookId == -1) {
            System.out.println("Invalid input for book ID. Returning to main menu.");
            return;
        }
        if (!bookExists(bookId)) {
            System.out.println("Book not found! Returning to main menu.");
            return;
        }
        int copies = HelperMethods.getBookCopies(bookId);
        if (copies > 0) {
            HelperMethods.updateBookCopies(bookId, copies - 1);
            String dateLoan = HelperMethods.getCurrentDate();
            String dateDue = HelperMethods.getDueDate();
            String memberName = HelperMethods.getMemberName(memberId);
            String bookTitle = HelperMethods.getBookTitle(bookId);
            try (Connection conn = connectionManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO loans (member_id, book_id, date_loan, date_due, member_first_name, member_last_name, book_title) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setInt(1, memberId);
                stmt.setInt(2, bookId);
                stmt.setDate(3, Date.valueOf(dateLoan));
                stmt.setDate(4, Date.valueOf(dateDue));
                stmt.setString(5, memberName.split(" ")[0]);
                stmt.setString(6, memberName.split(" ")[1]);
                stmt.setString(7, bookTitle);
                stmt.executeUpdate();
                System.out.println("Loan recorded: Due on " + dateDue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No copies available for loan. Returning to main menu.");
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
                scanner.next();
            }
        }
    }

    private static boolean memberExists(int memberId) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM members WHERE id = ?")) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean bookExists(int bookId) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM books WHERE id = ?")) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
