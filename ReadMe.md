# Student Information Management System - Version 0.7

**Team Number:** 23  
**Section:** 02

---

## 📑 Table of Contents
1. [Team Members and Contributions](#team-members-and-contributions)
2. [Project Description](#project-description)
3. [Requirements Compliance](#-requirements-compliance)
4. [Quick Feature Overview](#-quick-feature-overview)
5. [Version 0.7 Features](#version-07-features)
6. [Implemented Features](#implemented-features)
7. [Technical Requirements](#technical-requirements)
8. [Project Structure](#project-structure)
9. [How to Run](#how-to-run)
10. [Database Information](#database-information)
11. [Important Notes](#important-notes)
12. [Version History](#version-history)

---

## Team Members and Contributions 

**Version 0.7**

| Name | Contribution                                                                            |
|------|-----------------------------------------------------------------------------------------|
| Van Anh Tran | Enhanced search functionality, bug fixes and testing                                     |
| Yinqi Chen | UI improvements, search interface optimization                                           |
| Harshika Vijayabharath | Edit functionality implementation, testing and validation                               |
| Phuong Tong | Integration testing, documentation updates, final review                                 |

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

This is Version 0.7 of the Student Information Management System, a desktop application designed for faculty members to manage student profiles and programming language information.

**Note**: This version includes **pre-populated data** (3 programming languages and 5 student profiles) and a fully functioning 'Edit' option for users to update students' information

## ✅ Requirements Compliance

This application **fully satisfies all required specifications**:

| Requirement | Status | Implementation Details |
|-------------|--------|------------------------|
| **1. Launch to Home Page** | ✅ Complete | Application starts with main menu (`hello-view.fxml`) showing all navigation options |
| **2. Navigate to Search Page** | ✅ Complete | "Search Students" button on home page navigates to search interface |
| **3. Search by Criteria** | ✅ Complete | Multi-criteria search: name, academic status, languages, database skills, and role |
| **4. Display Results in Table** | ✅ Complete | JavaFX TableView displays all matching results with sortable columns |
| **5. Select Profile for Details** | ✅ Complete | "View" button opens detailed profile window with all student information |
| **6. Edit Student Profile** | ✅ Complete | "Edit" button allows modification of all fields with real-time validation |
| **7. Permanent Data Storage** | ✅ Complete | SQLite database (`student.db`) persists all changes permanently |

**Additional Features Implemented:**
- ✅ Real-time search (results update as you type)
- ✅ Delete functionality with confirmation dialogs
- ✅ Comments section (ready for future evaluation features)
- ✅ Whitelist/Blacklist flagging system
- ✅ Alphabetical sorting throughout the application
- ✅ Form validation with user-friendly error messages
- ✅ Back navigation from all pages to home

## 🚀 Quick Feature Overview

This application provides a complete student information management system with:

- 🏠 **User-Friendly Home Page** - Clean navigation to all features
- 🔍 **Powerful Search** - Find students by name, status, languages, skills, or role
- 📝 **Full CRUD Operations** - Create, Read, Update, Delete student profiles
- 💾 **Persistent Storage** - SQLite database keeps all data permanently
- ✏️ **Easy Editing** - Modify any student information with validation
- 📊 **Table Views** - Sortable displays throughout the application
- 🎯 **Real-Time Filtering** - Search results update as you type
- ⚡ **Pre-loaded Data** - 3 languages and 5 student profiles ready to explore

### 💼 Common Use Cases

**Scenario 1: Finding Students with Specific Skills**
```
Faculty member needs to find students who know Python and MongoDB
→ Navigate to "Search Students"
→ Type "Python" or "MongoDB" in search bar
→ Results instantly show matching students
→ Click "View" to see full profile details
```

**Scenario 2: Updating Student Information**
```
Student's academic status changed from Junior to Senior
→ Search for student by name
→ Click "Edit" button in table
→ Change "Academic Status" dropdown to "Senior"
→ Click "Update Student" - changes saved permanently
```

**Scenario 3: Managing Programming Languages**
```
Need to add a new language "TypeScript" to the system
→ Click "Define Programming Languages" from home
→ Enter "TypeScript" in the form
→ Click "Add Language"
→ Language now available for assigning to students
```

## Version 0.7 Features

- **Pre-populated Database**
  - Application automatically initializes with **exactly 3 programming languages** that user entered in **Define Programming Languages** page
  - Application automatically initializes with **exactly 5 student profiles** with diverse backgrounds

### ✅ New in This Version 0.7

- **Search Student Profiles Page** ✅ (UC-03)
  - Results display in a table format with a new View action button that handles showing student profile details

- **Edit Student Profiles** ✅
  - **Edit button for each student** in the students details page
  - Confirmation dialog before submit the edit
  - **Updating student's new information to database**
  - Automatically updates the detail page after editing
  - Success/error messages for user feedback
  - **Cancel** button allow user to cancel the editing process

- **Enhanced Navigation**
  - New "View" button on the search result table
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

### Features Coming in Future Versions:
- Evaluation comments 
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
│   │   │   │   │   └── SearchController.java        (Search & filter controller)
│   │   │   │   ├── data/
│   │   │   │   │   ├── LanguageDAO.java             (Language database access)
│   │   │   │   │   ├── StudentDAO.java              (Student database access)
│   │   │   │   │   └── package-info.java
│   │   │   │   └── model/
│   │   │   │       ├── Language.java                (Language entity)
│   │   │   │       └── Student.java                 (Student entity)
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── cs151/
│   │           └── application/
│   │               ├── hello-view.fxml              (Home page)
│   │               ├── languages-view.fxml          (Languages page)
│   │               ├── search-view.fxml             (Search student page)
│   │               └── students-view.fxml           (Manage students page) 
├── student.db                                       (SQLite database)
├── ReadMe.md
├── pom.xml
└── .gitignore


```

## How to Run

### Prerequisites:
- Java Development Kit (JDK) 21 or higher
- Maven 3.6+ (for command line execution)
- JavaFX 21.0.1 (automatically downloaded via Maven)

### Method 1: Using Maven (Recommended)
```bash
mvn clean javafx:run
```

### Method 2: Using IDE (IntelliJ IDEA / Eclipse)
1. **Import Project:**
   - Open your IDE
   - Select "Import Project" or "Open"
   - Choose the project folder and import as Maven project

2. **Configure JDK:**
   - Ensure JDK 21+ is configured in your IDE
   - Set project SDK to Java 21 (Zulu recommended)

3. **Run Application:**
   - Navigate to `src/main/java/cs151/application/Main.java`
   - Right-click and select "Run Main.main()"

### What You'll Experience:

#### First Launch:
1. **Home Page** displays with three main options:
   - Define Programming Languages
   - Manage Student Profiles
   - Search Students (★ Required Feature)
   
2. **Pre-populated Data** automatically loads:
   - 3 programming languages (Java, Python, JavaScript)
   - 5 diverse student profiles

#### Key Features to Explore:

**Search Students Page** (Main Focus):
- 🔍 Real-time search bar (type to filter instantly)
- 📊 Results table showing: Name, Academic Status, Languages, DB Skills, Role
- 👁️ **"View" button** - Opens detailed student profile window
- ✏️ **"Edit" button** - Modify any student information
- 🗑️ **"Delete" button** - Remove student (with confirmation)
- All changes save permanently to SQLite database

**Manage Student Profiles Page:**
- Create new student profiles
- CheckBox multi-selection for languages and database skills
- Form validation with helpful error messages
- "Show List" button displays all students in sortable table

**Define Programming Languages Page:**
- Add/Edit/Delete programming languages
- Automatic alphabetical sorting
- Validation prevents duplicate entries

## Database Information

### Database Architecture

**Database File:** `student.db` (SQLite database)  
**Location:** Project root directory  
**JDBC Driver:** SQLite JDBC 3.50.3.0 (automatically managed via Maven)

### Database Schema

#### Table: `Language`
```sql
CREATE TABLE Language (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);
```
**Pre-populated with:** Java, Python, JavaScript

#### Table: `Student`
```sql
CREATE TABLE Student (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    academicStatus TEXT NOT NULL,
    email TEXT,
    languages TEXT,              -- Comma-separated programming languages
    dbSkills TEXT,               -- Comma-separated database skills
    role TEXT,                   -- Preferred role (Front-End, Back-End, etc.)
    employmentStatus TEXT,       -- "Employed" or "Not Employed"
    jobDetails TEXT,             -- Employment details if employed
    comments TEXT,               -- Faculty evaluation comments
    flag TEXT                    -- "Whitelist", "Blacklist", or NULL
);
```

**Pre-populated with 5 diverse student profiles:**
- Alice Johnson (Junior, Full-Stack)
- Bob Smith (Senior, Back-End)
- Carol Martinez (Sophomore, Front-End)
- David Lee (Graduate, Data)
- Emma Wilson (Freshman, Full-Stack)

### Data Persistence Features
- ✅ **Automatic Initialization:** Database and tables created on first run
- ✅ **ACID Compliance:** All transactions are atomic and durable
- ✅ **Referential Integrity:** Language deletion checks for student references
- ✅ **Data Validation:** Application-level validation before database operations
- ✅ **No Data Loss:** All CRUD operations immediately persist to disk

## Important Notes

### ⚠️ Data Persistence
- All data is **permanently stored** in SQLite database (`student.db`)
- Changes to student profiles are **immediately saved** and cannot be undone
- Deleting a student is **permanent** - confirmation dialog will appear
- Database file persists between application sessions
- Closing and reopening the app will show all previously saved data

### 🔐 Application Behavior
- **Single-user desktop application** - no login or authentication required
- **Automatic database initialization** on first run
- **No network connection required** - fully offline application
- Window can be resized, minimum size constraints apply

### 💡 Usage Tips
- Use the **Search page** for quick lookups by any criteria
- **Real-time search** updates results as you type - no need to click "Search" button
- **Edit functionality** available from both search results and student list
- Programming languages must be defined before assigning them to students
- Cannot delete a programming language if it's assigned to any student
- Use **Whitelist/Blacklist flags** to mark students for special consideration

### 🐛 Known Limitations
- Email field is optional (not used in current version)
- Reports/Analytics features coming in future versions
- No bulk import/export functionality yet
- Comments are plain text (no rich formatting)

## Version History

- **v0.7** (Current Version):
  - ✅ **Complete Requirements Satisfaction**
    - All 7 required specifications fully implemented and tested
    - Launch to home page functionality
    - Navigation to search page
    - Multi-criteria search implementation
    - Table display of search results
    - Profile detail viewing
    - Full edit capabilities for all student fields
    - Permanent SQLite data storage
  - ✅ **Enhanced User Experience**
    - Improved search interface with real-time filtering
    - Better error handling and user feedback
    - Streamlined navigation flow
    - Optimized table displays across all views
  - ✅ **Bug Fixes and Improvements**
    - Fixed edit mode form population issues
    - Improved data validation messages
    - Enhanced delete confirmation dialogs
    - Better handling of empty/null values
  - 📚 **Documentation Updates**
    - Comprehensive README with requirements mapping
    - Detailed database schema documentation
    - Enhanced usage instructions and tips
  
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

