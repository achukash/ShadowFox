import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:library.db";

    public static void initializeDatabase() {
        try {
            // Ensure SQLite JDBC driver is loaded
            Class.forName("org.sqlite.JDBC");

            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                if (conn != null) {
                    String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                                              "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                              "title TEXT NOT NULL," +
                                              "author TEXT NOT NULL," +
                                              "genre TEXT NOT NULL," +
                                              "available_copies INTEGER NOT NULL)";
                    String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                                              "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                              "username TEXT NOT NULL UNIQUE," +
                                              "password TEXT NOT NULL," +
                                              "role TEXT NOT NULL)";
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(createBooksTable);
                        stmt.execute(createUsersTable);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database initialization error: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    public static boolean addBook(String title, String author, String genre, int availableCopies) {
        String sql = "INSERT INTO books (title, author, genre, available_copies) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setInt(4, availableCopies);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to add book: " + e.getMessage());
            return false;
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT id, title, author, genre, available_copies FROM Books";  // Removed 'year' column

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                int availableCopies = rs.getInt("available_copies");

                books.add(new Book(id, title, author, genre, availableCopies)); // Updated to match Book class constructor
            }
            
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
        return books;
    }

    // Borrow a book
    public static boolean borrowBook(int userId, int bookId) {
        String query = "INSERT INTO BorrowedBooks (user_id, book_id, borrow_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.setString(3, new java.sql.Date(System.currentTimeMillis()).toString());
            pstmt.executeUpdate();
            
            // Reduce the available copies
            updateBookCopies(bookId, -1);
            return true;
        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
            return false;
        }
    }

    // Return a book
    public static boolean returnBook(int userId, int bookId) {
        String query = "UPDATE BorrowedBooks SET return_date = ? WHERE user_id = ? AND book_id = ? AND return_date IS NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, new java.sql.Date(System.currentTimeMillis()).toString());
            pstmt.setInt(2, userId);
            pstmt.setInt(3, bookId);
            pstmt.executeUpdate();

            // Increase the available copies
            updateBookCopies(bookId, 1);
            return true;
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
            return false;
        }
    }

    // Update the available copies of a book
    private static void updateBookCopies(int bookId, int change) {
        String query = "UPDATE Books SET available_copies = available_copies + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, change);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating book copies: " + e.getMessage());
        }
    }

    // Register a new user
    public static boolean registerUser(String username, String password, String role) {
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    // Authenticate a user based on username and password
    public static String authenticateUser(String username, String password) {
        String query = "SELECT role FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
        return null;
    }
}
