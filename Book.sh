#!/bin/bash

echo -e "\n~~~~~ Library Management System ~~~~~\n"

# Sample data
declare -A BOOKS
BOOKS=( ["1"]="The Great Gatsby,F. Scott Fitzgerald,Fiction,5"
        ["2"]="1984,George Orwell,Dystopian,3"
        ["3"]="To Kill a Mockingbird,Harper Lee,Fiction,4" )

declare -A MEMBERS
MEMBERS=( ["1"]="Richard,Mogou,Richardmogou99.com,2023-01-01" )
declare -A LOANS
declare -A LOANS
LOANS=( ["1"]="1,1,2023-01-01,2023-01-15,2023-01-10,richard,Mogou,The Great Gatsby")

declare -a RECENT_ACTIVITY

# Function to add a book
ADD_BOOK() {
    echo -e "\nEnter book details:"
    echo -n "ID: "
    read ID
    echo -n "Title: "
    read TITLE
    echo -n "Author: "
    read AUTHOR
    echo -n "Category: "
    read CATEGORY
    echo -n "Number of Copies: "
    read COPIES
    BOOKS[$ID]="$TITLE,$AUTHOR,$CATEGORY,$COPIES"
    echo "Book added!"
}

# Function to search for a book by title or category
SEARCH_BOOKS() {
    echo -e "\nEnter search keyword: "
    read QUERY
    echo -e "\nSearch by:\n1. Title\n2. Category"
    read OPTION
    case $OPTION in
        1)
            echo "Books found:"
            FOUND=false
            for ID in "${!BOOKS[@]}"; do
                IFS=',' read -r TITLE AUTHOR CATEGORY COPIES <<< "${BOOKS[$ID]}"
                if [[ "${TITLE,,}" == *"${QUERY,,}"* ]]; then
                    echo "$ID) $TITLE by $AUTHOR, Category: $CATEGORY, Copies: $COPIES"
                    FOUND=true
                fi
            done
            if [ "$FOUND" = false ]; then
                echo "No books found with the title \"$QUERY\"."
            fi
            ;;
        2)
            echo "Books found:"
            FOUND=false
            for ID in "${!BOOKS[@]}"; do
                IFS=',' read -r TITLE AUTHOR CATEGORY COPIES <<< "${BOOKS[$ID]}"
                if [[ "${CATEGORY,,}" == *"${QUERY,,}"* ]]; then
                    echo "$ID) $TITLE by $AUTHOR, Category: $CATEGORY, Copies: $COPIES"
                    FOUND=true
                fi
            done
            if [ "$FOUND" = false ]; then
                echo "No books found in the category \"$QUERY\"."
            fi
            ;;
        *)
            echo "Invalid option. Please choose 1 for Title or 2 for Category."
            ;;
    esac
}


# Function to display all available books
DISPLAY_BOOKS() {
    echo "Available Books:"
    for ID in "${!BOOKS[@]}"; do
        IFS=',' read -r TITLE AUTHOR CATEGORY COPIES <<< "${BOOKS[$ID]}"
        echo "$ID) $TITLE by $AUTHOR, Category: $CATEGORY, Copies: $COPIES"
    done
}
# Function to add a member
ADD_MEMBER() {
    echo -e "\nEnter member details:"
    echo -n "ID: "
    read ID
    if [[ -n "${MEMBERS[$ID]}" ]]; then
        echo "ID already exists. Please use a unique ID."
        return
    fi
    echo -n "First Name: "
    read FIRST_NAME
    if [[ -z "$FIRST_NAME" ]]; then
        echo "First Name cannot be empty."
        return
    fi
    echo -n "Last Name: "
    read LAST_NAME
    if [[ -z "$LAST_NAME" ]]; then
        echo "Last Name cannot be empty."
        return
    fi
    echo -n "Email: "
    read EMAIL
    if ! [[ "$EMAIL" =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$ ]]; then
        echo "Invalid email format."
        return
    fi
    echo -n "Join Date (YYYY-MM-DD): "
    read JOIN_DATE
    if ! [[ "$JOIN_DATE" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        echo "Invalid date format. Please use YYYY-MM-DD."
        return
    fi
    MEMBERS[$ID]="$FIRST_NAME,$LAST_NAME,$EMAIL,$JOIN_DATE"
    echo "Member added!"
}


# Function to remove a member
REMOVE_MEMBER() {
    echo -n "Enter member ID to remove: "
    read ID
    if [[ -n "${MEMBERS[$ID]}" ]]; then
        unset MEMBERS[$ID]
        echo "Member removed!"
    else
        echo "Member not found!"
    fi
}

# Function to search for a member by name
SEARCH_MEMBERS() {
    echo -e "\nEnter search query (First or Last Name): "
    read QUERY
    echo "Members found:"
    FOUND=false
    for ID in "${!MEMBERS[@]}"; do
        IFS=',' read -r FIRST_NAME LAST_NAME EMAIL JOIN_DATE <<< "${MEMBERS[$ID]}"
        if [[ "${FIRST_NAME,,}" == *"${QUERY,,}"* ]] || [[ "${LAST_NAME,,}" == *"${QUERY,,}"* ]]; then
            echo "$ID) $FIRST_NAME $LAST_NAME, Email: $EMAIL, Joined on: $JOIN_DATE"
            FOUND=true
        fi
    done
    if [ "$FOUND" = false ]; then
        echo "No members found with the name \"$QUERY\"."
    fi
}

# Function to record a loan
RECORD_LOAN() {
    echo -n "Enter member ID: "
    read MEMBER_ID
    if [[ -z "${MEMBERS[$MEMBER_ID]}" ]]; then
        echo "Member not found!"
        return
    fi

    echo -n "Enter book ID: "
    read BOOK_ID
    if [[ -z "${BOOKS[$BOOK_ID]}" ]]; then
        echo "Book not found!"
        return
    fi

    IFS=',' read -r TITLE AUTHOR CATEGORY COPIES <<< "${BOOKS[$BOOK_ID]}"
    if (( COPIES > 0 )); then
        COPIES=$((COPIES - 1))
        BOOKS[$BOOK_ID]="$TITLE,$AUTHOR,$CATEGORY,$COPIES"
        LOAN_ID="${#LOANS[@]}"
        DATE_LOAN=$(date +%Y-%m-%d)
        DATE_DUE=$(date -d "+14 days" +%Y-%m-%d)
        LOANS[$LOAN_ID]="$MEMBER_ID,$BOOK_ID,$DATE_LOAN,$DATE_DUE,"
        echo "Loan recorded: Loan ID $LOAN_ID, Due on $DATE_DUE"
    else
        echo "No copies available for loan"
    fi
}
# Function to display all loans
DISPLAY_LOANS() {
    if [ ${#LOANS[@]} -eq 0 ]; then
        echo "No loans to display."
        return
    fi

    echo "Library Loans:"
    for LOAN_ID in "${!LOANS[@]}"; do
        IFS=',' read -r MEMBER_ID BOOK_ID DATE_LOAN DATE_DUE DATE_RETURN <<< "${LOANS[$LOAN_ID]}"
        IFS=',' read -r MEMBER_FIRST_NAME MEMBER_LAST_NAME MEMBER_EMAIL MEMBER_JOIN_DATE <<< "${MEMBERS[$MEMBER_ID]}"
        IFS=',' read -r BOOK_TITLE BOOK_AUTHOR BOOK_CATEGORY BOOK_COPIES <<< "${BOOKS[$BOOK_ID]}"
        echo "----------------------------------------"
        echo "Loan ID: $LOAN_ID"
        echo "Member: $MEMBER_FIRST_NAME $MEMBER_LAST_NAME, Email: $MEMBER_EMAIL"
        echo "Book: $BOOK_TITLE by $BOOK_AUTHOR, Category: $BOOK_CATEGORY"
        echo "Loan Date: $DATE_LOAN, Due Date: $DATE_DUE, Return Date: ${DATE_RETURN:-Not Returned}"
        echo "----------------------------------------"
    done
}



# Function to update a book
UPDATE_BOOK() {
    echo -n "Enter book ID to update: "
    read ID
    if [[ -n "${BOOKS[$ID]}" ]]; then
        echo -n "New Title (leave blank to keep current): "
        read TITLE
        echo -n "New Author (leave blank to keep current): "
        read AUTHOR
        echo -n "New Category (leave blank to keep current): "
        read CATEGORY
        echo -n "New Number of Copies (leave blank to keep current): "
        read COPIES

        IFS=',' read -r CURRENT_TITLE CURRENT_AUTHOR CURRENT_CATEGORY CURRENT_COPIES <<< "${BOOKS[$ID]}"
        TITLE=${TITLE:-$CURRENT_TITLE}
        AUTHOR=${AUTHOR:-$CURRENT_AUTHOR}
        CATEGORY=${CATEGORY:-$CURRENT_CATEGORY}
        COPIES=${COPIES:-$CURRENT_COPIES}

        BOOKS[$ID]="$TITLE,$AUTHOR,$CATEGORY,$COPIES"
        echo "Book updated!"
    else
        echo "Book not found!"
    fi
}
# Function to display all members
DISPLAY_MEMBERS() {
    echo "Library Members:"
    for ID in "${!MEMBERS[@]}"; do
        IFS=',' read -r FIRST_NAME LAST_NAME EMAIL JOIN_DATE <<< "${MEMBERS[$ID]}"
        echo "$ID) $FIRST_NAME $LAST_NAME, Email: $EMAIL, Joined on: $JOIN_DATE"
    done
}


# Function to record a return
RETURN_BOOK() {
    echo -n "Enter loan ID: "
    read LOAN_ID
    if [[ -z "${LOANS[$LOAN_ID]}" ]]; then
        echo "Loan not found!"
        return
    fi

    IFS=',' read -r MEMBER_ID BOOK_ID DATE_LOAN DATE_DUE DATE_RETURN <<< "${LOANS[$LOAN_ID]}"
    if [[ -n "$DATE_RETURN" ]]; then
        echo "Book already returned on $DATE_RETURN"
        return
    fi

    DATE_RETURN=$(date +%Y-%m-%d)
    LOANS[$LOAN_ID]="$MEMBER_ID,$BOOK_ID,$DATE_LOAN,$DATE_DUE,$DATE_RETURN"

    IFS=',' read -r TITLE AUTHOR CATEGORY COPIES <<< "${BOOKS[$BOOK_ID]}"
    COPIES=$((COPIES + 1))
    BOOKS[$BOOK_ID]="$TITLE,$AUTHOR,$CATEGORY,$COPIES"

    DATE_DUE_EPOCH=$(date -d "$DATE_DUE" +%s)
    DATE_RETURN_EPOCH=$(date -d "$DATE_RETURN" +%s)
    if (( DATE_RETURN_EPOCH > DATE_DUE_EPOCH )); then
        DAYS_LATE=$(( (DATE_RETURN_EPOCH - DATE_DUE_EPOCH) / 86400 ))
        PENALTY=$((DAYS_LATE * 100))
        echo "Late return. Penalty: $PENALTY F CFA"
    else
        echo "Book returned on time."
    fi
    echo "Book returned: Loan ID $LOAN_ID"
}
# Function to display the main menu
MAIN_MENU() {
    echo -e "\nMain Menu:"
    echo "1. Add Book"
    echo "2. Remove Book"
    echo "3. Update Book"
    echo "4. Search Book"
    echo "5. Display All Books"
    echo "6. Add Member"
    echo "7. Remove Member"
    echo "8. Search Member"
    echo "9. Display Member"
    echo "10. Record Loan"
    echo "11. Display Loans"
    echo "12. Return Book"
    echo "13. Exit"
    echo -n "Choose an option: "
    read OPTION

    case $OPTION in
        1) ADD_BOOK ;;
        2) REMOVE_BOOK ;;
        3) UPDATE_BOOK ;;
        4) SEARCH_BOOKS ;;
        5) DISPLAY_BOOKS ;;
        6) ADD_MEMBER ;;
        7) REMOVE_MEMBER ;;
        8) SEARCH_MEMBERS ;;
        9) DISPLAY_MEMBERS ;;
        10) RECORD_LOAN ;;
        11) DISPLAY_LOANS ;;
        12) RETURN_BOOK ;;
        13) EXIT ;;
        *) echo "Invalid option";;
    esac
    MAIN_MENU
}

# Function to exit the program
EXIT(){
  echo -e "\nThank you for using the Library Management System.\n"
  exit 0
}

# Initialize the main menu
MAIN_MENU
