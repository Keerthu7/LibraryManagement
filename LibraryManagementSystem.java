import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return id + " - " + title + " by " + author + " [" + (isAvailable ? "Available" : "Not Available") + "]";
    }
}

class User {
    private int id;
    private String name;
    private List<Book> borrowedBooks;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        borrowedBooks = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) { books.add(book); }
    public void addUser(User user) { users.add(user); }

    public List<Book> getBooks() { return books; }
    public List<User> getUsers() { return users; }

    public Book findBook(int bookId) {
        for (Book b : books) {
            if (b.getId() == bookId) return b;
        }
        return null;
    }

    public User findUser(int userId) {
        for (User u : users) {
            if (u.getId() == userId) return u;
        }
        return null;
    }

    public void borrowBook(int userId, int bookId) {
        User user = findUser(userId);
        Book book = findBook(bookId);
        if (user != null && book != null && book.isAvailable()) {
            book.setAvailable(false);
            user.borrowBook(book);
            System.out.println(user.getName() + " borrowed " + book.getTitle());
        } else {
            System.out.println("Book not available or user not found.");
        }
    }

    public void returnBook(int userId, int bookId) {
        User user = findUser(userId);
        Book book = findBook(bookId);
        if (user != null && book != null && user.getBorrowedBooks().contains(book)) {
            book.setAvailable(true);
            user.returnBook(book);
            System.out.println(user.getName() + " returned " + book.getTitle());
        } else {
            System.out.println("Invalid return operation.");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        // Preloaded books and users
        library.addBook(new Book(1, "Java Programming", "James Gosling"));
        library.addBook(new Book(2, "Data Structures", "Robert Lafore"));
        library.addBook(new Book(3, "Operating Systems", "Galvin"));
        library.addUser(new User(1, "Akshay"));
        library.addUser(new User(2, "Keerthana"));

        while (true) {
            System.out.println("\n==== Library Management System ====");
            System.out.println("1. View Books");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Books:");
                    for (Book b : library.getBooks()) {
                        System.out.println(b);
                    }
                    break;

                case 2:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(id, title, author));
                    System.out.println("Book added successfully.");
                    break;

                case 3:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    library.borrowBook(userId, bookId);
                    break;

                case 4:
                    System.out.print("Enter User ID: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();
                    library.returnBook(uid, bid);
                    break;

                case 5:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
