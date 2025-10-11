# Student Information Management System - Version 0.3

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, database testing and debugging, SQLite integration |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling, fixed merge conflicts |
| Harshika Vijayabharath | Setup table view with sorting, Implemented LanguagesController with CRUD operations
| Phuong Tong | Implemented LanguageDAO for SQLite persistence, updated MainController navigation, integrated module-info.java, project documentation |

## Project Description

This is Version 0.3 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

## Version 0.3 Features

### ✅ New in This Version 0.3

- **JavaFX TableView Integration**
  - Displays all stored programming languages in a tabular format
  - Automatically sorted alphabetically (A to Z)

- **SQLite Persistence**
  - Languages are stored in a local database (`student.db`)
  - Data remains available across sessions

- **Improved Validation**
  - Prevents empty or duplicate entries
  - Displays success/error messages with styling

- **Confirmation Dialogs**
  - Prompts before deleting a language
  - Prompts before editing with pre-filled input

### Implemented Features:
- **Home Page**: Landing page with navigation to different sections
- **Define Programming Languages Page**: 
  - Add new programming languages
  - **Persistent storage using SQLite database**
  - View list of all programming languages in **TableView** component
  - **Automatic alphabetical sorting (A-Z ascending order)**
  - Edit existing programming languages
  - Delete programming languages with confirmation
  - Validation for empty and duplicate language names
  - Success/error messages for user feedback
  - Data persists between application sessions

### Features Coming in Future Versions:
- Student profile management
- Search functionality
- Report generation
- Additional data analytics

## Technical Requirements

- **Java Version**: Zulu 23
- **Build Tool**: Maven
- **GUI Framework**: JavaFX 21.0.1
- **Database**: SQLite (JDBC 3.50.3.0)
- **Design Pattern**: MVC (Model-View-Controller)

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── cs151/
│   │   │   │   ├── application/
│   │   │   │   │   ├── Main.java              (Entry point)
│   │   │   │   │   └── MainController.java    (Home page controller)
│   │   │   │   ├── controller/
│   │   │   │   │   └── LanguagesController.java (Languages CRUD controller)
│   │   │   │   ├── data/
│   │   │   │   │   ├── LanguageDAO.java       (Database access layer)
│   │   │   │   │   └── package-info.java
│   │   │   │   └── model/
│   │   │   │       └── Language.java          (Language entity)
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── main-view.fxml         (Home page)
│   │               └── languages-view.fxml    (Languages page)
├── student.db                                 (SQLite database)
├── ReadMe.md
├── pom.xml
└── .gitignore


```

## How to Run

### Using Maven:
```bash
mvn clean javafx:run
```

### Using IDE:
1. Import the project as a Maven project
2. Ensure JDK 23 (Zulu) is configured
3. Run the `Main.java` class located in `cs151.application` package

## Database Information

- **Database File**: `student.db` (SQLite database)
- **Location**: Project root directory
- **Tables**: 
  - `Language` (id INTEGER PRIMARY KEY, name TEXT)
- **JDBC Driver**: SQLite JDBC 3.50.3.0 (included in Maven dependencies)

## Notes

- Data is now **permanently stored** in SQLite database
- All programming languages persist between application sessions
- The application runs as a single-user desktop application (no login required)
- Database file is automatically created on first run if it doesn't exist

## Version History

- **v0.3** (Current): 
  - Added SQLite database integration for persistent storage
  - Implemented LanguageDAO for data access layer
  - Added alphabetical sorting (A-Z) for languages table
  - TableView integration with JavaFX
  - Data now persists between application sessions
  - Upgraded JavaFX from 17.0.6 to 21.0.1 for better macOS compatibility
  
- **v0.2**: 
  - Define Programming Languages feature implemented
  - Basic CRUD operations with in-memory storage
  - TableView implementation
  
- **v0.1**: Initial project setup

---

**Client:** Professor Ahmad Yazdankhah  
**Course:** CS 151 - Object-Oriented Design  

**Semester:** Fall 2025












