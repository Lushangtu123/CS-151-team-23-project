# Student Information Management System - Version 0.2

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, code testing and debugging |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling |
| Harshika Vijayabharath | Implemented LanguagesController with CRUD operations, setup table view|
| Phuong Tong | Updated MainController navigation, integrated module-info.java, project documentation |

## Project Description

This is Version 0.2 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

## Version 0.2 Features

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
│   │   │   │   └── model/
│   │   │   │       └── Language.java
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── hello-view.fxml        (Home page)
│   │               └── languages-view.fxml    (Languages page)
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

- **v0.2** (Current): Define Programming Languages feature implemented
- **v0.1**: Initial project setup

---

**Client:** Ahmad Yazdankhah  
**Course:** CS 151 - Object-Oriented Design  

**Semester:** Fall 2025

