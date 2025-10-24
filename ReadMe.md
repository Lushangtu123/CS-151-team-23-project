# Student Information Management System - Version 0.6

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions 
**Version 0.6

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Implemented DataInitializer for populating 3 languages and 5 students, enhanced data validation |
| Yinqi Chen | Updated UI for search results with delete functionality, version updates |
| Harshika Vijayabharath | Enhanced SearchStudentController with delete functionality, testing |
| Phuong Tong | Project finalization, documentation updates, deployment preparation |

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

## Version 0.6 Features

### ✅ New in This Version 0.6

- **Pre-populated Data**
  - Automatically populates database with exactly **3 Programming Languages** (Java, Python, JavaScript)
  - Automatically populates database with exactly **5 Student Profiles** with diverse information
  - Data initialization runs on application startup
  - Ensures consistent data for testing and demonstration

- **Enhanced Search & Delete Functionality**
  - Search students by multiple criteria (name, academic status, language, database, role)
  - **Delete functionality in search results** - users can delete student profiles directly from search results
  - Permanent deletion from database with confirmation dialogs
  - Real-time table updates after deletion
  - User-friendly success/error messages

- **Complete TableView Implementation**
  - All student listings use JavaFX TableView component
  - Automatic alphabetical sorting (A→Z, case-insensitive)
  - Action columns with Edit and Delete buttons
  - Comprehensive column display (name, status, employment, languages, databases, role, flag)

### ✅ Features from Version 0.5

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

#### **UC-03: Search Student Profiles** ✅
- **Search Students Page**:
  - ✅ Accessible from Home Page → Manage Student Profiles → Search Students
  - ✅ `searchByMultiCriteria(criteria)` - Search by name, academic status, programming language, database skill, or preferred role
  - ✅ Multi-criteria search with checkboxes to select active search fields
  - ✅ Results displayed in **JavaFX TableView** with all student information
  - ✅ `deleteStudent(id)` - Delete any student profile directly from search results
  - ✅ Confirmation dialog before deletion
  - ✅ Permanent removal from SQLite database
  - ✅ Real-time table refresh after deletion
  - Search result count display
  - Empty result handling with user-friendly messages

### Features Coming in Future Versions:
- Advanced reporting and analytics
- Team formation tools
- Export data to CSV/PDF
- Batch operations

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

## Initial Data

The application comes pre-populated with sample data for demonstration and testing:

### Programming Languages (3)
1. **Java** - Object-oriented programming language
2. **Python** - High-level general-purpose programming language  
3. **JavaScript** - Web development programming language

### Student Profiles (5)
1. **Alice Johnson** - Senior, Employed at Google, Full-Stack role
2. **Bob Smith** - Junior, Not Employed, Front-End role
3. **Carol Williams** - Graduate, Data Scientist at Microsoft, Data role
4. **David Brown** - Sophomore, Not Employed, Back-End role
5. **Eva Martinez** - Senior, Full Stack Developer at Amazon, Full-Stack role

All sample data includes complete information such as programming languages, database skills, preferred roles, and comments.

## Version History

- **v0.6** (Current):
  - ✅ **Pre-populated Data** - Application automatically initializes with 3 programming languages and 5 student profiles
  - ✅ **Search Student Profiles** (UC-03)
    - Multi-criteria search functionality
    - TableView implementation for search results
    - Delete functionality in search results page
    - Permanent deletion from database with confirmation
  - ✅ **Enhanced Delete Operations**
    - Delete from search results page
    - Delete from student table view page
    - Confirmation dialogs for all deletions
  - Updated version numbers across the application
  - Comprehensive documentation updates

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

## Project Submission Guidelines

### Cleaning the Project Before Submission

Before zipping the project, ensure it is properly cleaned:

```bash
# Clean all build artifacts
mvn clean

# This will remove:
# - target/ directory (compiled classes and build outputs)
# - All Maven temporary files
```

### Zipping the Project

1. Navigate to the parent directory containing the project folder
2. Create a zip file with the format: `dev-00-0.6.zip`
3. Ensure the zip contains the root project folder (CS-151-team-23-project-2)
4. After unzipping, the folder structure should be: `dev-00-0.6/` containing `src/`, `pom.xml`, `ReadMe.md`, etc.

**Command to create zip:**
```bash
cd ..
zip -r dev-00-0.6.zip CS-151-team-23-project-2 -x "*/target/*" "*/.*" "*/.DS_Store"
```

### What's Included in the Submission
- ✅ Source code (`src/` directory)
- ✅ Maven configuration (`pom.xml`)
- ✅ This ReadMe file with complete documentation
- ✅ SQLite database file (`student.db`) with pre-populated data
- ✅ FXML view files and resources
- ❌ Build artifacts (cleaned before submission)
- ❌ IDE-specific files (.idea, .classpath, .project)

---

**Client:** Professor Ahmad Yazdankhah  
**Course:** CS 151 - Object-Oriented Design  

**Semester:** Fall 2025
















