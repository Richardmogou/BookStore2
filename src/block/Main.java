package block;

import block.ui.book.*;
import block.ui.loan.DisplayLoans;
import block.ui.loan.RecordLoan;
import block.ui.loan.ReturnBook;
import block.ui.member.AddMember;
import block.ui.member.DisplayMembers;
import block.ui.member.RemoveMember;
import block.ui.member.SearchMembers;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n~~~~~ Library Management System ~~~~~\n");
        mainMenu();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Update Book");
            System.out.println("4. Search Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Add Member");
            System.out.println("7. Remove Member");
            System.out.println("8. Search Member");
            System.out.println("9. Display Members");
            System.out.println("10. Record Loan");
            System.out.println("11. Display Loans");
            System.out.println("12. Return Book");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (option) {
                case 1 -> AddBook.addBook();
                case 2 -> RemoveBook.removeBook();
                case 3 -> UpdateBook.updateBook();
                case 4 -> SearchBooks.searchBooks();
                case 5 -> DisplayBooks.displayBooks();
                case 6 -> AddMember.addMember();
                case 7 -> RemoveMember.removeMember();
                case 8 -> SearchMembers.searchMembers();
                case 9 -> DisplayMembers.displayMembers();
                case 10 -> RecordLoan.recordLoan();
                case 11 -> DisplayLoans.displayLoans();
                case 12 -> ReturnBook.returnBook();
                case 13 -> {
                    System.out.println("\nThank you for using the Library Management System.\n");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }
}
