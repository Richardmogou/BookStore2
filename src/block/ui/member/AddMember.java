package block.ui.member;

import block.data.Member;
import block.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddMember {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionManager connectionManager = new ConnectionManager();

    public static void addMember() {
        System.out.println("\nEnter member details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Join Date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        
        Member newMember = new Member(0, firstName, lastName, email, joinDate); // Using the constructor of the Member class

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO members (first_name, last_name, email, join_date) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, newMember.getFirstName());
            stmt.setString(2, newMember.getLastName());
            stmt.setString(3, newMember.getEmail());
            stmt.setDate(4, Date.valueOf(newMember.getJoinDate()));
            stmt.executeUpdate();
            System.out.println("Member added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
