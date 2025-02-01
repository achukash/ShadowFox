import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class LibraryManagementSystem {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseHelper.initializeDatabase();
        
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (admin/member): ");
        String role = scanner.nextLine().toLowerCase();

        if (DatabaseHelper.registerUser(username, password, role)) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed. Username may be taken.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        String role = DatabaseHelper.authenticateUser(username, password);
        if (role != null) {
            System.out.println("Login successful! Welcome, " + username);
            if (role.equals("admin")) {
                adminMenu();
            } else {
                memberMenu();
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Book");
            System.out.println("2. View All Users");
            System.out.println("3. View All Books");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewUsers();
                    break;
                case 3:
                    viewBooks();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
    
    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter available copies: ");
        int availableCopies = scanner.nextInt();
        scanner.nextLine();
    
        // Assuming the method requires an additional 'bookId' parameter, which could be auto-generated or handled by the database
        int bookId = 0; // Or logic to auto-generate or fetch a new ID for the book
        if (DatabaseHelper.addBook(title, author, genre, availableCopies)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }
    
    private static void viewUsers() {
        // Fetch and display all registered users (assuming we have a method in DatabaseHelper)
        System.out.println("Fetching all users...");
        // Placeholder: Add a method in DatabaseHelper to get all users
    }
    
    private static void viewBooks() {
        List<Book> books = DatabaseHelper.getAllBooks();
        for (Book book : books) {
            System.out.println(book);  // Assuming Book class has a toString() method
        }
    }

    private static void memberMenu() {
        while (true) {
            System.out.println("\nMember Menu");
            System.out.println("1. View All Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
    
    private static void borrowBook() {
        System.out.print("Enter book ID to borrow: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        // Assuming user is authenticated, fetch userId from session or login info
        int userId = 1; // Replace with actual user ID
        if (DatabaseHelper.borrowBook(userId, bookId)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Failed to borrow book.");
        }
    }
    
    private static void returnBook() {
        System.out.print("Enter book ID to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        // Assuming user is authenticated, fetch userId from session or login info
        int userId = 1; // Replace with actual user ID
        if (DatabaseHelper.returnBook(userId, bookId)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book.");
        }
    }
}
