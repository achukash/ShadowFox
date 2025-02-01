public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;

    // Constructor with 5 parameters (id, title, author, genre, availableCopies)
    public Book(int id, String title, String author, String genre, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availableCopies = availableCopies;
    }

    // Getters and setters (if needed)
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }
}
