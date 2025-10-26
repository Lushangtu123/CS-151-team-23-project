# Student Information Management System - Version 0.6

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions 
**Version 0.6

| Name | Contribution                                                                             |
|------|------------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented SearchController functionality                                               |
| Yinqi Chen | Designed and implemented search-view.fxml UI layout, filter system, CSS styling          |
| Harshika Vijayabharath | Updated MainController navigation, update flag as checkbox from previous version, testing |
| Phuong Tong | Integrated search page, implemented deletion feature, documentation, final review |

**Version 0.5

| Name | Contribution                                                                                                      |
|------|-------------------------------------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented StudentDAO, AddStudentController and MultiSelectDropdown model                                        |
| Yinqi Chen | Designed and implemented students' fxml UI layouts, CSS styling, StudentTableController and StudentMenuController |
| Harshika Vijayabharath | Implemented StudentController and testing students' data                                                          |
| Phuong Tong | Updating add-student form and MainController, implemented Student model, final review and documentation           |

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

This is Version 0.6 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

**Note**: This version includes **pre-populated data** (3 programming languages and 5 student profiles) and a fully functional **Search Student Profiles** page with advanced filtering and deletion capabilities.

## Version 0.6 Features

### ✅ New in This Version 0.6

- **Pre-populated Database**
  - Application automatically initializes with **exactly 3 programming languages**: Java, Python, JavaScript
  - Application automatically initializes with **exactly 5 student profiles** with diverse backgrounds
  - Data initialization happens on first run or when database is empty
  - Ensures consistent starting data for demonstration and testing

- **Search Student Profiles Page** ✅ (UC-03)
  - Dedicated search page accessible from home page
  - User can type in search box to search about students 
  - Results display in a table format with a Delete action button that handles student profile deletion

- **Delete Student Profiles** ✅
  - **Delete button for each student** in the search results table
  - Confirmation dialog before deletion
  - **Permanently removes student from database**
  - Automatically updates the table after deletion
  - Success/error messages for user feedback

- **Enhanced Navigation**
  - New "Search Students" button on home page
  - Back to home functionality from all pages
  - Consistent navigation across all views

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

#### **UC-03: Search Student Profiles** ✅ (NEW in v0.6)
- **Search Student Profiles Page**:
  - ✅ `searchStudents(filters)` - Search students with multiple filter criteria
  - ✅ `filterByName(text)` - Filter by student name (case-insensitive partial match)
  - ✅ `filterByAcademicStatus(status)` - Filter by academic status
  - ✅ `filterByLanguage(language)` - Filter by programming language
  - ✅ `filterByRole(role)` - Filter by preferred role
  - ✅ `displayStudentsInTable()` - Display all matching students in **TableView**
  - ✅ `deleteStudent(id)` - Permanently delete student from database
  - ✅ `viewStudentDetails(id)` - View full student profile in dialog
  - ✅ `refreshResults()` - Reload all students from database
  - Apply filters and clear filters functionality
  - Real-time result count display
  - Automatic sorting by name (A→Z, case-insensitive)

### Features Coming in Future Versions:
- Advanced reporting and analytics
- Team formation tools
- Export data to CSV/PDF
- Import data from external sources

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
│   │   │   │   │   ├── Main.java                    (Entry point with data initialization)
│   │   │   │   │   └── MainController.java          (Home page controller)
│   │   │   │   ├── controller/
│   │   │   │   │   ├── LanguagesController.java     (Languages CRUD controller)
│   │   │   │   │   ├── StudentsController.java      (Students CRUD controller)
│   │   │   │   │   └── SearchStudentsController.java (Search & filter controller) [NEW]
│   │   │   │   ├── data/
│   │   │   │   │   ├── LanguageDAO.java             (Language database access)
│   │   │   │   │   ├── StudentDAO.java              (Student database access)
│   │   │   │   │   ├── DataInitializer.java         (Initialize database with data) [NEW]
│   │   │   │   │   └── package-info.java
│   │   │   │   └── model/
│   │   │   │       ├── Language.java                (Language entity)
│   │   │   │       ├── Student.java                 (Student entity)
│   │   │   │       └── MultiSelectDropDown.java     (Multiselect entity)
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── hello-view.fxml              (Home page)
│   │               ├── languages-view.fxml          (Languages page)
│   │               ├── add-student.fxml             (Create student page)
│   │               ├── students-view.fxml           (Manage students page)
│   │               ├── student-menu.fxml            (Navigation in manage student page)
│   │               ├── student-table.fxml           (All students page)
│   │               └── search-students-view.fxml    (Search students page) [NEW in v0.6]
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

**What you'll see:**
- Main menu with "Define Programming Languages", "Manage Student Profiles", and "Search Student Profiles"
- Database automatically initializes with **3 programming languages** and **5 student profiles**
- Search page with advanced filtering by name, academic status, language, and role
- Student management page with **CheckBox multi-selection** for languages and databases
- All data persisted in SQLite database

### Using IDE:
1. Import the project as a Maven project
2. Ensure JDK 21+ is configured
3. Run the `Main.java` class located in `cs151.application` package

## Database Information

- **Database File**: `student.db` (SQLite database)
- **Location**: Project root directory
- **Tables**: 
  - `Language` (id INTEGER PRIMARY KEY, name TEXT)
    - Pre-populated with: Java, Python, JavaScript
  - `Student` (id INTEGER PRIMARY KEY, name TEXT, academicStatus TEXT, email TEXT, languages TEXT, dbSkills TEXT, role TEXT, interests TEXT)
    - Pre-populated with: 5 diverse student profiles (Alice Johnson, Bob Smith, Carol Martinez, David Lee, Emma Wilson)
- **JDBC Driver**: SQLite JDBC 3.50.3.0 (included in Maven dependencies)
- **Relationships**: Languages stored as comma-separated strings in Student records
- **Initialization**: Database automatically initializes with required data on first run

## Notes

- Data is now **permanently stored** in SQLite database
- Database **automatically initializes** with 3 programming languages and 5 student profiles on first run
- All programming languages and student profiles persist between application sessions
- The application runs as a single-user desktop application (no login required)
- Database file is automatically created on first run if it doesn't exist
- **Search Student Profiles page** provides advanced filtering and deletion capabilities
- Deletion from search page is **permanent** and cannot be undone

## Version History

- **v0.6** (Current): 
  - ✅ **Pre-populated Database**
    - DataInitializer class automatically populates database
    - Exactly 3 programming languages (Java, Python, JavaScript)
    - Exactly 5 student profiles with diverse backgrounds
    - Data initialization happens on first run
  - ✅ **Search Student Profiles Page** (UC-03)
    - Dedicated search page with advanced filtering
    - Filter by name, academic status, language, and role
    - TableView with comprehensive student information
    - View detailed student profile dialog
    - Delete functionality with confirmation
    - Real-time result count and messaging
    - Refresh data capability
  - ✅ **Enhanced Navigation**
    - Added "Search Student Profiles" button to home page
    - Updated navigation flow across all pages
    - Consistent back-to-home functionality
  - Updated version number to 0.6 throughout application
  - Updated ReadMe with comprehensive documentation
  
- **v0.5**: 
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

