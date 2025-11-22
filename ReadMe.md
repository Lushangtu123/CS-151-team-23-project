# Student Information Management System - Version 0.9

**Team Number:** 23  
**Section:** 02

## Team Members and Contributions 

**Version 0.9**

| Name | Contribution                                                                                                                                                              |
|------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Van Anh Tran | Created DataInitializer for predefined students and comments, implemented ReportsController with filtering logic                                                          |
| Yinqi Chen | Designed and implemented reports-view.fxml, student-profile-report-view.fxml, and comment-detail-view.fxml UI layouts                                                     |
| Harshika Vijayabharath | Implemented StudentProfileReportController and CommentDetailController, tested double-click and click interactions                                                        |
| Phuong Tong | Implemented DataInfoDAO to ensure data initialized once, Updated MainController with Reports navigation, integrated all new pages, documentation and final review + fixes |

**Version 0.8**

| Name | Contribution                                                                            |
|------|-----------------------------------------------------------------------------------------|
| Van Anh Tran | Created Comment model and CommentDAO for database operations, implemented comment CRUD functionality |
| Yinqi Chen | Designed and implemented comments-view.fxml UI layout, comment display cards with date stamps, CSS styling |
| Harshika Vijayabharath | Implemented CommentsController with add/delete operations, integrated comments button in SearchController |
| Phuong Tong | Updated navigation flow for comments, enhanced StudentDetailController, documentation and final review |

**Version 0.7**

| Name | Contribution                                                                            |
|------|-----------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented StudentDetailController for viewing and editing student profiles, enhanced data validation |
| Yinqi Chen | Designed and implemented student-detail-view.fxml UI layout, edit form styling, confirmation dialogs |
| Harshika Vijayabharath | Implemented edit functionality in StudentsController, integrated View button in SearchController, testing |
| Phuong Tong | Updated StudentDAO with edit operations, enhanced navigation flow, documentation and final review |

**Version 0.6**

| Name | Contribution                                                                             |
|------|------------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented SearchController functionality                                               |
| Yinqi Chen | Designed and implemented search-view.fxml UI layout, filter system, CSS styling          |
| Harshika Vijayabharath | Updated MainController navigation, update flag as checkbox from previous version, testing |
| Phuong Tong | Integrated search page, implemented deletion feature, documentation, final review |

**Version 0.5**

| Name | Contribution                                                                                                      |
|------|-------------------------------------------------------------------------------------------------------------------|
| Van Anh Tran | Implemented StudentDAO, AddStudentController and MultiSelectDropdown model                                        |
| Yinqi Chen | Designed and implemented students' fxml UI layouts, CSS styling, StudentTableController and StudentMenuController |
| Harshika Vijayabharath | Implemented StudentController and testing students' data                                                          |
| Phuong Tong | Updating add-student form and MainController, implemented Student model, final review and documentation           |

**Version 0.3**

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, database testing and debugging, SQLite integration |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling, fixed merge conflicts |
| Harshika Vijayabharath | Setup table view with sorting, Implemented LanguagesController with CRUD operations, final review |
| Phuong Tong | Implemented LanguageDAO for SQLite persistence, updated MainController navigation, integrated module-info.java, project documentation |

**Version 0.2**

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Created Language model class, implemented data validation logic, code testing and debugging |
| Yinqi Chen | Designed and implemented languages-view.fxml UI layout, CSS styling |
| Harshika Vijayabharath | Implemented LanguagesController with CRUD operations, table view setup |
| Phuong Tong | Updated MainController navigation, integrated module-info.java, project documentation |

**Technical Spec**

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Introduction, Software Overview, Data Model (Revised)|
| Yinqi Chen | Detailed Design |
| Harshika Vijayabharath | Class Diagram (domain model), final version |
| Phuong Tong | Document review, sequence diagram final version |

**Functional Spec**

| Name | Contribution |
|------|--------------|
| Van Anh Tran | Use Case - Alternative Flow of Events (Alternative #2), Nonfunctional Requirements |
| Yinqi Chen |  Introduction, Mockups |
| Harshika Vijayabharath | Use Case Primary Flow of Events, Alternative Flow of Events (Alternative #1) |
| Phuong Tong | Functional Requirements, Nonfunctional Requirements (Revised and Completed) |

## Project Description

This is Version 0.9 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

**Note**: This version includes **pre-populated data** (6 student profiles with meaningful comments) and a fully functioning **Reports System** for viewing Whitelist/Blacklist student reports

## Version 0.9 Features

- **Pre-populated Database**
  - Application automatically initializes with **exactly 3 programming languages**: Java, Python, C++
  - Application automatically initializes with **exactly 6 student profiles**:
    - **Whitelisted** (3): Alice Johnson, Bob Smith, Carol Martinez
    - **Blacklisted** (3): David Lee, Emma Wilson, Frank Brown
  - Each student has **meaningful evaluation comments** (30+ words each)

### ✅ New in This Version 0.9

- **Reports System** ✅
  - **View Reports Page**: Navigate from home to reports page
  - **Filter by Flag Status**: Select All/Whitelist/Blacklist using radio buttons
  - **Students Table**: Display filtered students in tabular format with Flag column
  - **Double-Click to View**: Double-click student row to open detailed profile report
  
- **Student Profile Report** ✅
  - **Top Section**: Student information in form format
  - **Bottom Section**: Comments table with Date and Comment (excerpt) columns
  - **Click to Expand**: Click comment row to view full comment text in popup window
  
- **Comment Detail View** ✅
  - Popup window displaying complete comment content
  - Shows date and full text without truncation
  - Easy-to-read format for long evaluation comments

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
  
#### **UC-04: Edit Student Profiles** ✅
- **Edit Student Profiles Page** ✅
  - ✅ `UpdateStudent()` - Save the edited information for students
  - **Updating student's new information to database**
  - Automatically updates the detail page after editing
  - Success/error messages for user feedback
  - **Cancel** button allow user to cancel the editing process

#### **UC-05: Evaluation Comments ** ✅
- **Student Evaluation Comments System** ✅ (UC-05)
    - Dedicated comments page for each student showing all evaluation history
    - **View Comments** button available in both Search Results and Student Details pages
    - Add new evaluation comments with **automatic date stamping** (today's date)
    - Display all comments with their date stamps in chronological order (newest first)
    - Delete individual comments with confirmation dialog


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
│   │   │   │   │   ├── LanguagesController.java          (Languages CRUD controller)
│   │   │   │   │   ├── StudentsController.java           (Students CRUD controller)
│   │   │   │   │   ├── SearchController.java             (Search & filter controller)
│   │   │   │   │   ├── StudentDetailController.java      (Student details view controller)
│   │   │   │   │   ├── CommentsController.java           (Comments management controller)
│   │   │   │   │   ├── ReportsController.java            (Reports with filtering controller)
│   │   │   │   │   ├── StudentProfileReportController.java (Profile + comments report controller)
│   │   │   │   │   └── CommentDetailController.java      (Comment detail popup controller)
│   │   │   │   ├── data/
│   │   │   │   │   ├── LanguageDAO.java             (Language database access)
│   │   │   │   │   ├── StudentDAO.java              (Student database access)
│   │   │   │   │   ├── CommentDAO.java              (Comment database access)
│   │   │   │   │   ├── DataInitializer.java         (Initialize predefined data)
│   │   │   │   │   ├── DataInfoDAO.java             (Check initialized data)
│   │   │   │   │   └── package-info.java
│   │   │   │   └── model/
│   │   │   │       ├── Language.java                (Language entity)
│   │   │   │       ├── Student.java                 (Student entity)
│   │   │   │       └── Comment.java                 (Comment entity with date stamp)
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── hello-view.fxml                    (Home page)
│   │               ├── languages-view.fxml                (Languages page)
│   │               ├── search-view.fxml                   (Search student page)
│   │               ├── students-view.fxml                 (Manage students page)
│   │               ├── student-detail-view.fxml           (Student details page)
│   │               ├── comments-view.fxml                 (Comments/Evaluation page)
│   │               ├── reports-view.fxml                  (Reports main page)
│   │               ├── student-profile-report-view.fxml   (Profile + comments report)
│   │               └── comment-detail-view.fxml           (Comment detail popup)
├── student.db                                       (SQLite database)
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
- Main menu with "Define Programming Languages", "Manage Student Profiles", "Search Student Profiles", and "View Reports"
- Database automatically initializes with **3 programming languages** and **6 student profiles** (3 whitelisted, 3 blacklisted)
- Each student has **meaningful evaluation comments** (30+ words each)
- **Reports page** with radio button filtering (All/Whitelist/Blacklist)
- **Double-click** student row to view detailed profile report
- **Profile report** showing student info + comments table
- **Click** comment row to view full content in popup
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
    - Pre-populated with: **Java, Python, C++**
  - `Student` (id INTEGER PRIMARY KEY, name TEXT, academicStatus TEXT, email TEXT, languages TEXT, dbSkills TEXT, role TEXT, employmentStatus TEXT, jobDetails TEXT, comments TEXT, flag TEXT)
    - Pre-populated with **6 students**:
      - **Whitelisted**: Alice Johnson, Bob Smith, Carol Martinez
      - **Blacklisted**: David Lee, Emma Wilson, Frank Brown
  - `Comment` (id INTEGER PRIMARY KEY, student_id INTEGER, content TEXT, date TEXT)
    - Links to Student table with foreign key
    - Each student has **3 meaningful evaluation comments** (30+ words each)
    - Stores evaluation comments with automatic date stamping
- **JDBC Driver**: SQLite JDBC 3.50.3.0 (included in Maven dependencies)
- **Relationships**: 
  - Languages stored as comma-separated strings in Student records
  - Comments linked to students via foreign key (student_id)
- **Initialization**: Database automatically initializes with required data on first run

## Notes

- Data is now **permanently stored** in SQLite database
- Database **automatically initializes** with 3 programming languages, 6 student profiles, and 18 evaluation comments on first run
- All programming languages, student profiles, and evaluation comments persist between application sessions
- The application runs as a single-user desktop application (no login required)
- Database file is automatically created on first run if it doesn't exist
- **Reports System** provides filtering by Whitelist/Blacklist status
- **Double-click** on student row opens detailed profile with comments table
- **Click** on comment row shows full text in popup window
- Deletion from search page or comments page is **permanent** and cannot be undone

## Version History

- **v0.9**:
  - ✅ **Predefined Student Data**
    - 6 student profiles pre-populated (3 whitelisted, 3 blacklisted)
    - Each student has 3 meaningful evaluation comments (30+ words each)
    - Total 18 evaluation comments in database
  - ✅ **Reports System** (UC-06)
    - Created Reports main page with filtering by flag status
    - Radio buttons for All/Whitelist/Blacklist filtering
    - Double-click student row to view detailed profile report
  - ✅ **Student Profile Report**
    - Top section: Student information in form format
    - Bottom section: Comments table with Date and Comment excerpt
    - Click comment row to view full content in popup
  - ✅ **Comment Detail View**
    - Popup window displaying complete comment text
    - Shows date and full content without truncation
  - ✅ **DataInitializer Class**
    - Automated database initialization with predefined data
    - Comprehensive student profiles with realistic information
    - Meaningful evaluation comments for each student
  - Updated version number to 0.9 throughout application
  - Updated ReadMe with comprehensive v0.9 documentation

- **v0.8**:
  - ✅ **Student Evaluation Comments System** (UC-05)
    - Created Comment model and CommentDAO for database operations
    - Implemented dedicated comments page with comment history display
    - Automatic date stamping for new comments (yyyy-MM-dd format)
    - Add and delete comment functionality with confirmation
    - Comment count display and chronological ordering (newest first)
    - Professional card-based UI for comment display
  - ✅ **Enhanced Navigation for Comments**
    - Added "Comments" button in search results table
    - Added "View Comments" button in student details page
    - Back navigation from comments to student details or home
  - ✅ **Database Schema Updates**
    - Created Comment table with foreign key to Student table
    - Automatic Comment table initialization on app start
  - Updated version number to 0.8 throughout application
  - Updated ReadMe with comprehensive comments feature documentation
  
- **v0.7**:
  - ✅ **Edit Student Profiles** (UC-04)
    - Edit button in student details page
    - Confirmation dialogs and validation
    - Database update operations
    - Success/error feedback
  - ✅ **Enhanced Student Details View**
    - Dedicated student detail page
    - View button in search results
    - Professional layout and styling
  - Updated version number to 0.7 throughout application

- **v0.6**: 
  - ✅ **Pre-populated Database**
    - Exactly 3 programming languages
    - Exactly 5 student profiles with diverse backgrounds
  - ✅ **Search Student Profiles Page** (UC-03)
    - Dedicated search page allow user to search up students
    - TableView with comprehensive student information
    - Delete functionality with confirmation
    - Refresh data capability
  - ✅ **Enhanced Navigation**
    - Added "Search Students" button to home page
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
