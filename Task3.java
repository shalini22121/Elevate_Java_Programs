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

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author + " - " + (isAvailable ? "Available" : "Issued");
    }
}

class User {
    private int userId;
    private String name;
    private List<Book> borrowedBooks;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name;
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) return user;
        }
        return null;
    }

    public void issueBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user != null && book != null && book.isAvailable()) {
            book.setAvailable(false);
            user.borrowBook(book);
            System.out.println("✅ Book issued successfully!");
        } else {
            System.out.println("❌ Book is not available or user/book not found.");
        }
    }

    public void returnBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);

        if (user != null && book != null && user.getBorrowedBooks().contains(book)) {
            book.setAvailable(true);
            user.returnBook(book);
            System.out.println("✅ Book returned successfully!");
        } else {
            System.out.println("❌ Error in returning book.");
        }
    }

    public void showAllBooks() {
        System.out.println("\n📚 Book List:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void showAllUsers() {
        System.out.println("\n👥 User List:");
        for (User user : users) {
            System.out.println(user);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        // Sample data
        library.addBook(new Book(1, "The Alchemist", "Paulo Coelho"));
        library.addBook(new Book(2, "Clean Code", "Robert C. Martin"));
        library.addBook(new Book(3, "1984", "George Orwell"));

        library.registerUser(new User(101, "Alice"));
        library.registerUser(new User(102, "Bob"));

        while (true) {
            System.out.println("\n========= Library Menu =========");
            System.out.println("1. View Books");
            System.out.println("2. View Users");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                sc.nextLine(); // clear the buffer
                continue;
            }

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    library.showAllUsers();
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    int uid1 = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bid1 = sc.nextInt();
                    library.issueBook(uid1, bid1);
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    int uid2 = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bid2 = sc.nextInt();
                    library.returnBook(uid2, bid2);
                    break;
                case 5:
                    System.out.println("📕 Thank you for using the Library System!");
                    sc.close();
                    return;
                default:
                    System.out.println("⚠️ Invalid option. Try again.");
            }
        }
    }
}
