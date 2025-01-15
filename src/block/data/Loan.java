package block.data;

import java.time.LocalDate;

public class Loan {
    private int id;
    private int bookId;
    private int memberId;
    private LocalDate dateLoan;
    private LocalDate dateDue;
    private LocalDate dateReturn;
    private String memberFirstName;
    private String memberLastName;
    private String bookTitle;

    public Loan(int id, int bookId, int memberId, LocalDate dateLoan, LocalDate dateDue, LocalDate dateReturn, String memberFirstName, String memberLastName, String bookTitle) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.dateLoan = dateLoan;
        this.dateDue = dateDue;
        this.dateReturn = dateReturn;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.bookTitle = bookTitle;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getDateLoan() {
        return dateLoan;
    }

    public void setDateLoan(LocalDate dateLoan) {
        this.dateLoan = dateLoan;
    }

    public LocalDate getDateDue() {
        return dateDue;
    }

    public void setDateDue(LocalDate dateDue) {
        this.dateDue = dateDue;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
