# Student Information Management System - Version 0.5

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions 
**Version 0.5

| Name | Contribution                                                                                            |
|------|---------------------------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented StudentDAO, AddStudentController and StudentMenuController                                  |
| Yinqi Chen | Designed and implemented students' fxml UI layouts, CSS styling, StudentTableController                 |
| Harshika Vijayabharath | Implemented StudentController and testing                                                               |
| Phuong Tong | Updating add-student form and MainController, implemented Student model, final review and documentation |

**Version 0.3

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, database testing and debugging, SQLite integration |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling, fixed merge conflicts |
| Harshika Vijayabharath | Setup table view with sorting, Implemented LanguagesController with CRUD operations, final review
| Phuong Tong | Implemented LanguageDAO for SQLite persistence, updated MainController navigation, integrated module-info.java, project documentation |

**Version 0.2

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, code testing and debugging |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling |
| Harshika Vijayabharath | Implemented LanguagesController with CRUD operations, table view setup |
| Phuong Tong | Updated MainController navigation, integrated module-info.java, project documentation |
## Project Description

This is Version 0.5 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

## Version 0.5 Features

### ✅ New in This Version 0.5

- **Complete Student Profile Management**
  - Create, view, edit, and delete student profiles
  - Required fields: Full Name, Academic Status, Current Job Status, Job Details (if Employed), Programming Languages, Databases, Preferred Role
  - Full validation with inline error messages
  - TableView with automatic sorting by name (A→Z, case-insensitive)

- **Enhanced Language Management**
  - Reference checking before deletion
  - Prevents deletion of languages assigned to students
  - Option to unassign and delete languages
  - Improved data integrity

- **Advanced Persistence Layer**
  - StudentDAO with full CRUD operations
  - LanguageDAO with reference checking
  - All data stored in SQLite database
  - Supports complex queries and sorting

### Implemented Features:

#### **UC-01: Define Programming Languages** ✅
- **Home Page**: Landing page with navigation to different sections
- **Define Programming Languages Page**: 
  - ✅ `addLanguage(name)` - Add new programming languages
  - ✅ `renameLanguage(id, newName)` - Edit existing programming languages
  - ✅ `deleteLanguage(id)` - Delete programming languages with confirmation
  - ✅ `listLanguages()` - View list in **TableView** component with **automatic alphabetical sorting (A-Z)**
  - ✅ `isUniqueLanguage(name)` - Validation for empty and duplicate language names
  - ✅ `isReferenced(name)` - Check if language is used by students
  - **Persistent storage using SQLite database**
  - Success/error messages for user feedback
  - Data persists between application sessions

#### **UC-02: Create Student Profile** ✅
- **Student Management Page**:
  - ✅ `createStudent(profile)` - Create new student profiles with validation
  - ✅ `getAllStudents({sortBy:"name", order:"asc"})` - View all students in **TableView** sorted A→Z (case-insensitive)
  - ✅ `validateStudent(profile)` - Required field validation + inline errors
  - ✅ `persistStudent(profile)` - Save students to SQLite database
  - ✅ Edit existing student profiles
  - ✅ Delete student profiles with confirmation
  - ✅ View detailed student information
  - Student count display
  - Form with all required and optional fields
  - Email format validation
  - Multi-language support (comma-separated)

### Features Coming in Future Versions:
- Search and filter functionality
- Advanced reporting and analytics
- Team formation tools
- Export data to CSV/PDF

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
│   │   │   │   │   ├── LanguagesController.java (Languages CRUD controller)
│   │   │   │   │   └── StudentsController.java  (Students CRUD controller)
│   │   │   │   ├── data/
│   │   │   │   │   ├── LanguageDAO.java       (Language database access)
│   │   │   │   │   ├── StudentDAO.java        (Student database access)
│   │   │   │   │   └── package-info.java
│   │   │   │   └── model/
│   │   │   │       ├── Language.java               (Language entity)
│   │   │   │       ├── Student.java                (Student entity)
│   │   │   │       └── MultiSelectDropdown.java    (Multiselect entity)
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── hello-view.fxml         (Home page)
│   │               ├── languages-view.fxml     (Languages page)
│   │               ├── add-student.fxml        (Create student page)
│   │               ├── student-menu.fxml       (Navigation in manage student page)
│   │               └── student-table.fxml      (Alltudents page)
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
  - `Student` (id INTEGER PRIMARY KEY, name TEXT, academicStatus TEXT, email TEXT, languages TEXT, dbSkills TEXT, role TEXT, interests TEXT)
- **JDBC Driver**: SQLite JDBC 3.50.3.0 (included in Maven dependencies)
- **Relationships**: Languages stored as comma-separated strings in Student records

## Notes

- Data is now **permanently stored** in SQLite database
- All programming languages and student profiles persist between application sessions
- The application runs as a single-user desktop application (no login required)
- Database file is automatically created on first run if it doesn't exist
- Student tables are automatically created when accessing Student Management page

## Version History

- **v0.5** (Current): 
  - ✅ **Complete Student Profile Management** (UC-02)
    - Created Student model with required and optional fields
    - Implemented StudentDAO with full CRUD operations
    - Built students-view.fxml with comprehensive form
    - Implemented StudentsController with validation
    - TableView with automatic sorting by name (A→Z, case-insensitive)
  - ✅ **Enhanced Language Management** (UC-01)
    - Added isReferenced() method to check language usage
    - Improved data integrity with reference checking
  - ✅ **Persistence Layer Complete**
    - LanguageRepository: findAll, insert, update, delete, isReferenced
    - StudentRepository: findAllSortedByName, insert, update, delete, validate
  - Updated navigation with Student Management button
  - Updated home page UI to version 0.5
  
- **v0.3**: 
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
















