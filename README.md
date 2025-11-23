# Library Management System

A comprehensive Java-based Library Management System that demonstrates advanced object-oriented programming concepts, data structures, and software engineering best practices.

## 📋 Project Overview

The Library Management System is a console-based application designed to manage library operations including book cataloging, user management, and transaction processing. The system implements core OOP principles and provides a robust solution for library administration.

## ✨ Features

### 1. **Book Management Module**
- Add new books to the catalog with ISBN, title, author, category, copies, and price
- View all books with availability status
- Search books by title, author, or category
- Update book information
- Remove books from the catalog
- Track available and total copies

### 2. **User Management Module**
- Register new users (Members and Librarians)
- Support for different membership types (Regular and Premium)
- User authentication and profile management
- View all registered users
- Track user borrowing history
- Borrowing limits based on user type

### 3. **Transaction Management Module**
- Borrow books with automatic due date assignment (14 days)
- Return books with fine calculation
- View active transactions
- View user-specific transaction history
- Automatic overdue detection
- Fine calculation (Rs. 5 per day)

### 4. **Analytics and Reporting**
- Books distribution by category
- Most borrowed books ranking
- Total fines collected
- Real-time availability tracking

## 🛠 Technologies Used

- **Language**: Java 8+
- **Core Concepts**: 
  - Object-Oriented Programming (Inheritance, Polymorphism, Abstraction, Encapsulation)
  - Collections Framework (HashMap, ArrayList, Stream API)
  - Exception Handling (Custom exceptions)
  - Design Patterns (Singleton, Factory)
  - File I/O for logging
  - Date/Time API (LocalDate)

## 📁 Project Structure

```
LibraryManagementSystem/
├── src/
│   └── com/
│       └── library/
│           ├── Main.java                    # Entry point
│           ├── model/                       # Domain models
│           │   ├── User.java               # Abstract base class
│           │   ├── Member.java             # Member implementation
│           │   ├── Librarian.java          # Librarian implementation
│           │   ├── Book.java               # Book entity
│           │   └── Transaction.java        # Transaction entity
│           ├── service/                     # Business logic
│           │   └── LibraryService.java     # Core service (Singleton)
│           ├── exception/                   # Custom exceptions
│           │   ├── LibraryException.java
│           │   ├── BookNotFoundException.java
│           │   ├── BookNotAvailableException.java
│           │   ├── UserNotFoundException.java
│           │   ├── DuplicateBookException.java
│           │   ├── DuplicateUserException.java
│           │   ├── BorrowLimitExceededException.java
│           │   ├── TransactionNotFoundException.java
│           │   └── InvalidTransactionException.java
│           ├── util/                        # Utility classes
│           │   ├── Logger.java             # Logging utility (Singleton)
│           │   └── InputValidator.java     # Input validation
│           └── ui/                          # User interface
│               └── LibraryUI.java          # Console UI
├── library_system.log                       # System logs
└── README.md
```

## 🚀 Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Steps to Run

1. **Clone or Download the Project**
   ```bash
   git clone <repository-url>
   cd LibraryManagementSystem
   ```

2. **Compile the Project**
   
   **Using Command Line:**
   ```bash
   # Navigate to src directory
   cd src
   
   # Compile all Java files
   javac com/library/*.java com/library/model/*.java com/library/service/*.java com/library/exception/*.java com/library/util/*.java com/library/ui/*.java
   
   # Run the application
   java com.library.Main
   ```

   **Using IDE:**
   - Import the project into your IDE
   - Set the source folder to `src`
   - Run `Main.java`

3. **Using the Application**
   - Follow the console menu prompts
   - Use numeric inputs to navigate menus
   - Sample data is pre-loaded for testing

## 📖 Usage Instructions

### Main Menu Options

```
========== MAIN MENU ==========
1. Book Management
2. User Management
3. Transaction Management
4. View Analytics
5. Exit
================================
```

### Sample Workflow

1. **View Available Books**
   - Select `1. Book Management`
   - Select `2. View All Books`

2. **Borrow a Book**
   - Select `3. Transaction Management`
   - Select `1. Borrow Book`
   - Enter User ID: `M001` (Alice Johnson)
   - Enter Book ISBN: `978-0-596-52068-7` (Head First Java)

3. **Return a Book**
   - Select `3. Transaction Management`
   - Select `2. Return Book`
   - Enter the Transaction ID provided during borrowing

4. **View Analytics**
   - Select `4. View Analytics`
   - View books by category, most borrowed books, and fines collected

### Pre-loaded Sample Data

**Users:**
- Member: Alice Johnson (M001) - Regular membership
- Member: Bob Smith (M002) - Premium membership
- Librarian: Carol Admin (L001)

**Books:**
- Head First Java (ISBN: 978-0-596-52068-7)
- Effective Java (ISBN: 978-0-134-68599-1)
- Design Patterns (ISBN: 978-0-201-63361-0)
- Clean Code (ISBN: 978-0-132-35088-4)
- Introduction to Algorithms (ISBN: 978-0-262-03384-8)

## 🧪 Testing

### Manual Test Cases

1. **Book Borrowing Limit Test**
   - Borrow multiple books with user M001 (Regular member - limit 5 books)
   - Try borrowing 6th book - should fail with BorrowLimitExceededException

2. **Fine Calculation Test**
   - Modify Transaction due date to past date (in code)
   - Return book after due date
   - Verify fine is calculated at Rs. 5 per day

3. **Book Availability Test**
   - Borrow all copies of a book
   - Try borrowing same book again - should fail with BookNotAvailableException

4. **Search Functionality Test**
   - Search for "Java" - should return multiple books
   - Search for "CLRS" - should return Algorithm book

## 🏗 System Architecture

### Design Patterns Used

1. **Singleton Pattern**
   - `LibraryService`: Ensures single instance of core service
   - `Logger`: Centralized logging mechanism

2. **Factory Pattern**
   - User creation with Member/Librarian types

3. **Strategy Pattern**
   - Different borrowing limits based on user type

### Class Relationships

```
User (Abstract)
├── Member (inherits)
└── Librarian (inherits)

LibraryService (Singleton)
├── manages → Book
├── manages → User
└── manages → Transaction

Transaction
├── references → User (by userId)
└── references → Book (by isbn)
```

## 📊 Non-Functional Requirements

1. **Performance**: 
   - O(1) book and user lookup using HashMap
   - Efficient search using Stream API

2. **Maintainability**: 
   - Modular code structure with clear separation of concerns
   - Comprehensive documentation and comments

3. **Reliability**: 
   - Robust exception handling with custom exceptions
   - Input validation for all user inputs

4. **Usability**: 
   - Intuitive console-based menu system
   - Clear error messages and user feedback

5. **Scalability**: 
   - Collection-based storage can be easily migrated to database
   - Service layer abstraction supports future enhancements

6. **Security**: 
   - Input validation prevents invalid data
   - Encapsulation protects data integrity

7. **Logging**: 
   - Comprehensive logging of all operations
   - Error tracking with timestamps

## 🎯 Key Java Concepts Demonstrated

### Object-Oriented Programming
- **Abstraction**: Abstract `User` class with concrete implementations
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: `Member` and `Librarian` extend `User`
- **Polymorphism**: Different user types with varying behavior

### Collections Framework
- `HashMap<String, Book>` for O(1) book lookup
- `HashMap<String, User>` for O(1) user lookup
- `ArrayList<Transaction>` for transaction history
- Stream API for filtering and data processing

### Exception Handling
- Custom exception hierarchy extending `RuntimeException`
- Specific exceptions for different error scenarios
- Try-catch blocks for graceful error handling

### Design Patterns
- **Singleton**: `LibraryService` and `Logger`
- **Template Method**: Abstract methods in `User` class
- **Serializable**: Models implement Serializable for potential persistence

## 🔧 Configuration

### Customizable Parameters

In `Transaction.java`:
```java
private static final int BORROWING_PERIOD_DAYS = 14;
private static final double FINE_PER_DAY = 5.0;
```

In `Member.java`:
```java
private static final int MAX_BOOKS = 5;
```

In `Librarian.java`:
```java
private static final int MAX_BOOKS = 20;
```

## 🐛 Known Limitations

1. Data is stored in-memory (no database persistence)
2. No user authentication/password system
3. Console-based UI only (no GUI)
4. Single-user system (no concurrent access handling)

## 🚀 Future Enhancements

1. **Database Integration**
   - Integrate MySQL/PostgreSQL for data persistence
   - Implement JDBC for database operations

2. **GUI Development**
   - Create JavaFX or Swing-based graphical interface
   - Add user-friendly forms and tables

3. **Advanced Features**
   - Book reservation system
   - Email notifications for due dates
   - Barcode scanning support
   - Multi-library branch support

4. **Authentication & Authorization**
   - Password-based login system
   - Role-based access control
   - Session management

5. **Reporting**
   - Generate PDF reports
   - Export data to Excel
   - Statistical dashboards

6. **API Development**
   - RESTful API using Spring Boot
   - Mobile app integration

## 📝 Design Decisions & Rationale

### Why HashMap for Storage?
- O(1) average time complexity for lookup operations
- Efficient for frequent search operations
- Suitable for in-memory storage

### Why Singleton for LibraryService?
- Ensures single source of truth for library data
- Prevents data inconsistency
- Centralized state management

### Why Abstract User Class?
- Common properties shared across user types
- Forces implementation of user-specific behavior
- Supports polymorphic behavior

### Why Custom Exceptions?
- Provides specific error handling
- Makes debugging easier
- Better error messages for users

## 👨‍💻 Developer Information

- **Registration Number**: 24MIM10084
- **Course**: Computer Science (Full-Stack Development & AI/ML Specialization)
- **Concepts Applied**: OOP, Data Structures, Collections, Exception Handling, Design Patterns

## 📚 References

1. "Effective Java" by Joshua Bloch - Best practices
2. "Head First Design Patterns" - Design pattern implementation
3. Java Documentation - Official Oracle docs
4. "Clean Code" by Robert Martin - Code organization principles

## 📄 License

This project is created for educational purposes as part of VITyarthi coursework.

---

**Note**: This is an academic project demonstrating Java programming skills and software engineering principles. For production use, additional features like database integration, security hardening, and comprehensive testing would be required.
