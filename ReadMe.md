# Student Information Management System - Version 0.3

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, SQlite |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling |
| Harshika Vijayabharath | Implemented LanguagesController with CRUD operations, setup table view|
| Phuong Tong | Updated MainController navigation, integrated module-info.java, project documentation |

## Project Description

This is Version 0.3 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.
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
## Old Version 0.2 Features

### Implemented Features:
- **Home Page**: Landing page with navigation to different sections
- **Define Programming Languages Page**: 
  - Add new programming languages
  - View list of all programming languages
  - Edit existing programming languages
  - Delete programming languages with confirmation
  - Validation for empty and duplicate language names
  - Success/error messages for user feedback

### Features Coming in Future Versions:
- Student profile management
- Search functionality
- Report generation
- Data persistence (SQLite/file system)

## Technical Requirements

- **Java Version**: Zulu 23
- **Build Tool**: Maven
- **GUI Framework**: JavaFX 17.0.6
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
│   │   │   │   │   └── LanguagesController.java
│   │   │   │   ├── data/
│   │   │   │   │   └── LanguageDAO.java       (Database access layer)
│   │   │   │   └── model/
│   │   │   │       └── Language.java
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── main-view.fxml         (Home page)
│   │               └── languages-view.fxml    (Languages page)
├── student.db                                 (Database)
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

## Notes

- Data is currently stored in-memory only (not persisted)
- Data persistence will be implemented in future versions
- The application runs as a single-user desktop application (no login required)

## Version History
- **v0.3** (Current): TableView integration and SQLite persistence
- **v0.2**: Define Programming Languages feature implemented
- **v0.1**: Initial project setup

---

**Client:** Ahmad Yazdankhah  
**Course:** CS 151 - Object-Oriented Design  

**Semester:** Fall 2025





