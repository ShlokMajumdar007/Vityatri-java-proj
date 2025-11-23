// Main.java
package com.library;

import com.library.ui.LibraryUI;
import com.library.service.LibraryService;
import com.library.util.Logger;

/**
 * Main entry point for the Library Management System
 * Demonstrates OOP concepts: Encapsulation, Abstraction, Polymorphism
 * 
 * @author Student
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            Logger.getInstance().log("System starting...");
            
            // Initialize services
            LibraryService libraryService = LibraryService.getInstance();
            
            // Start UI
            LibraryUI ui = new LibraryUI(libraryService);
            ui.start();
            
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            Logger.getInstance().logError("Fatal error", e);
        }
    }
}

// ============================================================================
// MODEL CLASSES
// ============================================================================

// User.java
package com.library.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class for all users in the system
 * Demonstrates: Abstraction, Encapsulation, Inheritance
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    
    public User(String userId, String name, String email, String phoneNumber) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    // Abstract method - polymorphism
    public abstract String getUserType();
    public abstract int getMaxBooksAllowed();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
    
    @Override
    public String toString() {
        return String.format("%s[ID=%s, Name=%s, Email=%s]", 
            getUserType(), userId, name, email);
    }
}

// Member.java
package com.library.model;

/**
 * Regular library member
 * Demonstrates: Inheritance, Method Overriding
 */
public class Member extends User {
    private static final long serialVersionUID = 1L;
    private static final int MAX_BOOKS = 5;
    
    private String membershipType; // "REGULAR", "PREMIUM"
    
    public Member(String userId, String name, String email, String phoneNumber, String membershipType) {
        super(userId, name, email, phoneNumber);
        this.membershipType = membershipType;
    }
    
    @Override
    public String getUserType() {
        return "Member";
    }
    
    @Override
    public int getMaxBooksAllowed() {
        return membershipType.equals("PREMIUM") ? MAX_BOOKS + 5 : MAX_BOOKS;
    }
    
    public String getMembershipType() {
        return membershipType;
    }
    
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}

// Librarian.java
package com.library.model;

/**
 * Librarian user with administrative privileges
 * Demonstrates: Inheritance, Polymorphism
 */
public class Librarian extends User {
    private static final long serialVersionUID = 1L;
    private static final int MAX_BOOKS = 20;
    
    private String employeeId;
    
    public Librarian(String userId, String name, String email, String phoneNumber, String employeeId) {
        super(userId, name, email, phoneNumber);
        this.employeeId = employeeId;
    }
    
    @Override
    public String getUserType() {
        return "Librarian";
    }
    
    @Override
    public int getMaxBooksAllowed() {
        return MAX_BOOKS;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
}

// Book.java
package com.library.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a book in the library
 * Demonstrates: Encapsulation, Data Validation
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private double price;
    
    public Book(String isbn, String title, String author, String category, 
                int totalCopies, double price) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative");
        }
        
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.price = price;
    }
    
    // Getters and Setters with validation
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public void setTotalCopies(int totalCopies) {
        if (totalCopies < this.totalCopies - this.availableCopies) {
            throw new IllegalArgumentException("Cannot reduce total copies below borrowed amount");
        }
        int difference = totalCopies - this.totalCopies;
        this.totalCopies = totalCopies;
        this.availableCopies += difference;
    }
    
    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }
    
    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }
    
    public boolean isAvailable() {
        return availableCopies > 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    
    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, Title=%s, Author=%s, Available=%d/%d]",
            isbn, title, author, availableCopies, totalCopies);
    }
}

// Transaction.java
package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a book borrowing/returning transaction
 * Demonstrates: Encapsulation, Date handling
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int transactionCounter = 0;
    
    private String transactionId;
    private String userId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private TransactionStatus status;
    private double fine;
    
    public enum TransactionStatus {
        ACTIVE, RETURNED, OVERDUE
    }
    
    public Transaction(String userId, String isbn) {
        this.transactionId = "TXN" + String.format("%06d", ++transactionCounter);
        this.userId = userId;
        this.isbn = isbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(14); // 14 days borrowing period
        this.status = TransactionStatus.ACTIVE;
        this.fine = 0.0;
    }
    
    // Getters
    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public TransactionStatus getStatus() { return status; }
    public double getFine() { return fine; }
    
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.status = TransactionStatus.RETURNED;
        calculateFine();
    }
    
    private void calculateFine() {
        if (returnDate.isAfter(dueDate)) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            this.fine = daysOverdue * 5.0; // Rs. 5 per day fine
        }
    }
    
    public void checkOverdue() {
        if (status == TransactionStatus.ACTIVE && LocalDate.now().isAfter(dueDate)) {
            this.status = TransactionStatus.OVERDUE;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return transactionId.equals(that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
    
    @Override
    public String toString() {
        return String.format("Transaction[ID=%s, User=%s, Book=%s, Status=%s, Due=%s]",
            transactionId, userId, isbn, status, dueDate);
    }
}

// ============================================================================
// SERVICE CLASSES
// ============================================================================

// LibraryService.java
package com.library.service;

import com.library.model.*;
import com.library.util.Logger;
import com.library.exception.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core service class for library operations
 * Demonstrates: Singleton Pattern, Business Logic, Collections
 */
public class LibraryService {
    private static LibraryService instance;
    
    private Map<String, Book> bookCatalog;
    private Map<String, User> users;
    private List<Transaction> transactions;
    private Logger logger;
    
    private LibraryService() {
        this.bookCatalog = new HashMap<>();
        this.users = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.logger = Logger.getInstance();
        initializeSampleData();
    }
    
    // Singleton pattern
    public static synchronized LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryService();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Add sample books
        addBook(new Book("978-0-596-52068-7", "Head First Java", "Kathy Sierra", "Programming", 5, 599.0));
        addBook(new Book("978-0-134-68599-1", "Effective Java", "Joshua Bloch", "Programming", 3, 799.0));
        addBook(new Book("978-0-201-63361-0", "Design Patterns", "Gang of Four", "Software Engineering", 4, 899.0));
        addBook(new Book("978-0-132-35088-4", "Clean Code", "Robert Martin", "Software Engineering", 6, 699.0));
        addBook(new Book("978-0-262-03384-8", "Introduction to Algorithms", "CLRS", "Algorithms", 2, 1299.0));
        
        // Add sample users
        registerUser(new Member("M001", "Alice Johnson", "alice@email.com", "9876543210", "REGULAR"));
        registerUser(new Member("M002", "Bob Smith", "bob@email.com", "9876543211", "PREMIUM"));
        registerUser(new Librarian("L001", "Carol Admin", "carol@library.com", "9876543212", "EMP001"));
        
        logger.log("Sample data initialized");
    }
    
    // Book Management
    public void addBook(Book book) {
        if (bookCatalog.containsKey(book.getIsbn())) {
            throw new DuplicateBookException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        bookCatalog.put(book.getIsbn(), book);
        logger.log("Book added: " + book.getTitle());
    }
    
    public Book getBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }
        return book;
    }
    
    public void updateBook(String isbn, Book updatedBook) {
        if (!bookCatalog.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }
        bookCatalog.put(isbn, updatedBook);
        logger.log("Book updated: " + isbn);
    }
    
    public void removeBook(String isbn) {
        Book book = bookCatalog.remove(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }
        logger.log("Book removed: " + isbn);
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookCatalog.values());
    }
    
    public List<Book> searchBooks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return bookCatalog.values().stream()
            .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword) ||
                           book.getAuthor().toLowerCase().contains(lowerKeyword) ||
                           book.getCategory().toLowerCase().contains(lowerKeyword))
            .collect(Collectors.toList());
    }
    
    // User Management
    public void registerUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new DuplicateUserException("User with ID " + user.getUserId() + " already exists");
        }
        users.put(user.getUserId(), user);
        logger.log("User registered: " + user.getName());
    }
    
    public User getUser(String userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        return user;
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    // Transaction Management
    public Transaction borrowBook(String userId, String isbn) {
        User user = getUser(userId);
        Book book = getBook(isbn);
        
        // Check if book is available
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for borrowing");
        }
        
        // Check user's borrowing limit
        long currentBorrowedCount = transactions.stream()
            .filter(t -> t.getUserId().equals(userId) && t.getStatus() == Transaction.TransactionStatus.ACTIVE)
            .count();
            
        if (currentBorrowedCount >= user.getMaxBooksAllowed()) {
            throw new BorrowLimitExceededException("User has reached maximum borrowing limit");
        }
        
        // Create transaction
        Transaction transaction = new Transaction(userId, isbn);
        transactions.add(transaction);
        book.borrowCopy();
        
        logger.log(String.format("Book borrowed: %s by %s", isbn, userId));
        return transaction;
    }
    
    public Transaction returnBook(String transactionId) {
        Transaction transaction = transactions.stream()
            .filter(t -> t.getTransactionId().equals(transactionId))
            .findFirst()
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        
        if (transaction.getStatus() != Transaction.TransactionStatus.ACTIVE &&
            transaction.getStatus() != Transaction.TransactionStatus.OVERDUE) {
            throw new InvalidTransactionException("Book already returned");
        }
        
        transaction.returnBook();
        Book book = getBook(transaction.getIsbn());
        book.returnCopy();
        
        logger.log(String.format("Book returned: Transaction %s", transactionId));
        return transaction;
    }
    
    public List<Transaction> getUserTransactions(String userId) {
        return transactions.stream()
            .filter(t -> t.getUserId().equals(userId))
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getActiveTransactions() {
        return transactions.stream()
            .filter(t -> t.getStatus() == Transaction.TransactionStatus.ACTIVE ||
                        t.getStatus() == Transaction.TransactionStatus.OVERDUE)
            .collect(Collectors.toList());
    }
    
    // Analytics
    public Map<String, Long> getBooksByCategoryCount() {
        return bookCatalog.values().stream()
            .collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
    }
    
    public List<Book> getMostBorrowedBooks(int limit) {
        Map<String, Long> borrowCounts = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getIsbn, Collectors.counting()));
        
        return borrowCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(limit)
            .map(entry -> bookCatalog.get(entry.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
    public double getTotalFinesCollected() {
        return transactions.stream()
            .filter(t -> t.getStatus() == Transaction.TransactionStatus.RETURNED)
            .mapToDouble(Transaction::getFine)
            .sum();
    }
}

// ============================================================================
// EXCEPTION CLASSES
// ============================================================================

// LibraryException.java
package com.library.exception;

public class LibraryException extends RuntimeException {
    public LibraryException(String message) {
        super(message);
    }
    
    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}

// BookNotFoundException.java
package com.library.exception;

public class BookNotFoundException extends LibraryException {
    public BookNotFoundException(String message) {
        super(message);
    }
}

// BookNotAvailableException.java
package com.library.exception;

public class BookNotAvailableException extends LibraryException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

// UserNotFoundException.java
package com.library.exception;

public class UserNotFoundException extends LibraryException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

// DuplicateBookException.java
package com.library.exception;

public class DuplicateBookException extends LibraryException {
    public DuplicateBookException(String message) {
        super(message);
    }
}

// DuplicateUserException.java
package com.library.exception;

public class DuplicateUserException extends LibraryException {
    public DuplicateUserException(String message) {
        super(message);
    }
}

// BorrowLimitExceededException.java
package com.library.exception;

public class BorrowLimitExceededException extends LibraryException {
    public BorrowLimitExceededException(String message) {
        super(message);
    }
}

// TransactionNotFoundException.java
package com.library.exception;

public class TransactionNotFoundException extends LibraryException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}

// InvalidTransactionException.java
package com.library.exception;

public class InvalidTransactionException extends LibraryException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}

// ============================================================================
// UTILITY CLASSES
// ============================================================================

// Logger.java
package com.library.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple logging utility
 * Demonstrates: Singleton Pattern, File I/O
 */
public class Logger {
    private static Logger instance;
    private static final String LOG_FILE = "library_system.log";
    private DateTimeFormatter formatter;
    
    private Logger() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] INFO: %s", timestamp, message);
        System.out.println(logMessage);
        writeToFile(logMessage);
    }
    
    public void logError(String message, Exception e) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] ERROR: %s - %s", timestamp, message, e.getMessage());
        System.err.println(logMessage);
        writeToFile(logMessage);
    }
    
    private void writeToFile(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}

// InputValidator.java
package com.library.util;

import java.util.regex.Pattern;

/**
 * Input validation utility
 * Demonstrates: Static methods, Regex validation
 */
public class InputValidator {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");
    private static final Pattern ISBN_PATTERN = 
        Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    public static boolean isValidISBN(String isbn) {
        return isbn != null && ISBN_PATTERN.matcher(isbn).matches();
    }
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}

// ============================================================================
// UI CLASS
// ============================================================================

// LibraryUI.java
package com.library.ui;

import com.library.model.*;
import com.library.service.LibraryService;
import com.library.exception.*;
import com.library.util.InputValidator;

import java.util.*;

/**
 * Console-based user interface
 * Demonstrates: User interaction, Menu-driven system
 */
public class LibraryUI {
    private LibraryService libraryService;
    private Scanner scanner;
    
    public LibraryUI(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("========================================");
        System.out.println("  LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        
        while (true) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        bookManagementMenu();
                        break;
                    case 2:
                        userManagementMenu();
                        break;
                    case 3:
                        transactionManagementMenu();
                        break;
                    case 4:
                        displayAnalytics();
                        break;
                    case 5:
                        System.out.println("Thank you for using Library Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Book Management");
        System.out.println("2. User Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. View Analytics");
        System.out.println("5. Exit");
        System.out.println("================================");
    }
    
    private void bookManagementMenu() {
        System.out.println("\n----- Book Management -----");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Books");
        System.out.println("4. Update Book");
        System.out.println("5. Remove Book");
        System.out.println("6. Back to Main Menu");
        
        int choice = readInt("Enter choice: ");
        
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                viewAllBooks();
                break;
            case 3:
                searchBooks();
                break;
            case 4:
                updateBook();
                break;
            case 5:
                removeBook();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private void addBook() {
        System.out.println("\n--- Add New Book ---");
        String isbn = readString("Enter ISBN: ");
        String title = readString("Enter Title: ");
        String author = readString("Enter Author: ");
        String category = readString("Enter Category: ");
        int copies = readInt("Enter Total Copies: ");
        double price = readDouble("Enter Price: ");
        
        Book book = new Book(isbn, title, author, category, copies, price);
        libraryService.addBook(book);
        System.out.println("Book added successfully!");
    }
    
    private void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = libraryService.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        
        System.out.printf("%-20s %-30s %-25s %-15s %-10s %-10s%n", 
            "ISBN", "Title", "Author", "Category", "Available", "Total");
        System.out.println("-".repeat(120));
        
        for (Book book : books) {
            System.out.printf("%-20s %-30s %-25s %-15s %-10d %-10d%n",
                book.getIsbn(), 
                truncate(book.getTitle(), 30),
                truncate(book.getAuthor(), 25),
                book.getCategory(),
                book.getAvailableCopies(),
                book.getTotalCopies());
        }
    }
    
    private void searchBooks() {
        String keyword = readString("Enter search keyword: ");
        List<Book> results = libraryService.searchBooks(keyword);
        
        System.out.println("\n--- Search Results ---");
        if (results.isEmpty()) {
            System.out.println("No books found matching: " + keyword);
            return;
        }
        
        for (Book book : results) {
            System.out.println(book);
        }
    }
    
    private void updateBook() {
        String isbn = readString("Enter ISBN of book to update: ");
        Book book = libraryService.getBook(isbn);
        
        System.out.println("Current details: " + book);
        System.out.println("Enter new details (press Enter to keep current value):");
        
        String title = readStringOptional("New Title: ", book.getTitle());
        String author = readStringOptional("New Author: ", book.getAuthor());
        String category = readStringOptional("New Category: ", book.getCategory());
        
        Book updatedBook = new Book(isbn, title, author, category, book.getTotalCopies(), book.getPrice());
        updatedBook.setTotalCopies(book.getTotalCopies());
        
        libraryService.updateBook(isbn, updatedBook);
        System.out.println("Book updated successfully!");
    }
    
    private void removeBook() {
        String isbn = readString("Enter ISBN of book to remove: ");
        libraryService.removeBook(isbn);
        System.out.println("Book removed successfully!");
    }
    
    private void userManagementMenu() {
        System.out.println("\n----- User Management -----");
        System.out.println("1. Register New User");
        System.out.println("2. View All Users");
        System.out.println("3. View User Transactions");
        System.out.println("4. Back to Main Menu");
        
        int choice = readInt("Enter choice: ");
        
        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                viewAllUsers();
                break;
            case 3:
                viewUserTransactions();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private void registerUser() {
        System.out.println("\n--- Register New User ---");
        String userId = readString("Enter User ID: ");
        String name = readString("Enter Name: ");
        String email = readString("Enter Email: ");
        String phone = readString("Enter Phone: ");
        
        System.out.println("Select User Type:");
        System.out.println("1. Member");
        System.out.println("2. Librarian");
        int type = readInt("Enter choice: ");
        
        User user;
        if (type == 1) {
            System.out.println("Select Membership Type:");
            System.out.println("1. REGULAR");
            System.out.println("2. PREMIUM");
            int memType = readInt("Enter choice: ");
            String membershipType = (memType == 2) ? "PREMIUM" : "REGULAR";
            user = new Member(userId, name, email, phone, membershipType);
        } else {
            String empId = readString("Enter Employee ID: ");
            user = new Librarian(userId, name, email, phone, empId);
        }
        
        libraryService.registerUser(user);
        System.out.println("User registered successfully!");
    }
    
    private void viewAllUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = libraryService.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }
        
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    private void viewUserTransactions() {
        String userId = readString("Enter User ID: ");
        List<Transaction> transactions = libraryService.getUserTransactions(userId);
        
        System.out.println("\n--- User Transactions ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for user: " + userId);
            return;
        }
        
        for (Transaction txn : transactions) {
            System.out.println(txn);
            if (txn.getFine() > 0) {
                System.out.println("  Fine: Rs. " + txn.getFine());
            }
        }
    }
    
    private void transactionManagementMenu() {
        System.out.println("\n----- Transaction Management -----");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. View Active Transactions");
        System.out.println("4. Back to Main Menu");
        
        int choice = readInt("Enter choice: ");
        
        switch (choice) {
            case 1:
                borrowBook();
                break;
            case 2:
                returnBook();
                break;
            case 3:
                viewActiveTransactions();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private void borrowBook() {
        String userId = readString("Enter User ID: ");
        String isbn = readString("Enter Book ISBN: ");
        
        Transaction transaction = libraryService.borrowBook(userId, isbn);
        System.out.println("Book borrowed successfully!");
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Due Date: " + transaction.getDueDate());
    }
    
    private void returnBook() {
        String transactionId = readString("Enter Transaction ID: ");
        
        Transaction transaction = libraryService.returnBook(transactionId);
        System.out.println("Book returned successfully!");
        
        if (transaction.getFine() > 0) {
            System.out.println("Fine Amount: Rs. " + transaction.getFine());
        } else {
            System.out.println("No fine applicable.");
        }
    }
    
    private void viewActiveTransactions() {
        System.out.println("\n--- Active Transactions ---");
        List<Transaction> transactions = libraryService.getActiveTransactions();
        
        if (transactions.isEmpty()) {
            System.out.println("No active transactions.");
            return;
        }
        
        for (Transaction txn : transactions) {
            txn.checkOverdue();
            System.out.println(txn);
        }
    }
    
    private void displayAnalytics() {
        System.out.println("\n========== ANALYTICS ==========");
        
        // Books by category
        System.out.println("\n1. Books by Category:");
        Map<String, Long> categoryCounts = libraryService.getBooksByCategoryCount();
        categoryCounts.forEach((category, count) -> 
            System.out.printf("   %s: %d books%n", category, count));
        
        // Most borrowed books
        System.out.println("\n2. Top 5 Most Borrowed Books:");
        List<Book> mostBorrowed = libraryService.getMostBorrowedBooks(5);
        for (int i = 0; i < mostBorrowed.size(); i++) {
            Book book = mostBorrowed.get(i);
            System.out.printf("   %d. %s by %s%n", i + 1, book.getTitle(), book.getAuthor());
        }
        
        // Total fines
        System.out.println("\n3. Total Fines Collected:");
        double totalFines = libraryService.getTotalFinesCollected();
        System.out.printf("   Rs. %.2f%n", totalFines);
        
        System.out.println("\n================================");
    }
    
    // Helper methods
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private String readStringOptional(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
    
    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
    
    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
