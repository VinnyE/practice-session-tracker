# Practice Session Tracker V2 - SQLite with Normalized Schema

## Overview

Rebuild the practice session tracker with SQLite database using normalized schema design, adding categories (separate table with foreign keys) and notes to sessions, and implementing Java Streams for statistics. This continues the systematic learning approach from V1, introducing ONE new concept per module with productive struggle.

## Current State Analysis

**What exists now (V1):**
- Working CLI application with three commands: `add`, `list`, `total`
- Text file persistence using `sessions.txt` (CSV format: date,duration)
- Clean 3-layer architecture: PracticeTracker → SessionManager → Session
- 53 Java concepts mastered through 7 modules
- Immutable Session class with validation
- Robust error handling and user feedback

**Current Limitations:**
- No relational structure for categories
- No way to enforce category consistency
- Full file rewrite inefficient for large datasets
- No support for querying/filtering data

## Desired End State

After V2 completion, you will have:
- SQLite database with normalized schema (categories + sessions tables)
- Foreign key constraints enforcing data integrity
- Support for optional notes on sessions
- Statistics using Java Streams (totals by category, averages, etc.)
- Understanding of relational database design and SQL JOIN operations

### Database Schema Design:

**Two-table normalized structure:**
- **categories table**: id (PK), name (UNIQUE)
- **sessions table**: id (PK), date, duration, category_id (FK), notes

**Why normalized?** Eliminates duplicate category names, enforces consistency through foreign keys, enables efficient querying.

### Verification of Success:
- Database enforces referential integrity (can't use invalid category)
- Can perform JOIN queries to get session data with category names
- Statistics calculated correctly using both SQL and Java Streams
- Application handles database errors gracefully

## What We're NOT Doing

- Migrating sessions.txt data (clean slate with new database)
- ORM frameworks (raw JDBC to learn fundamentals)
- Connection pooling (simple connect/close per operation)
- Edit/delete functionality (save for V3)
- Transactions (basic operations only)
- Authentication or multi-user support

## Implementation Approach

Five modules, each 30-75 minutes, each introducing ONE new concept. You'll write all the code yourself with conceptual guidance and API pointers.

---

## Module 8: SQLite Setup & Basic JDBC (45-60 min)

### Learning Objectives

Understand how Java connects to databases through JDBC - the standard database API that works with any database (SQLite, PostgreSQL, MySQL, etc.). Learn the fundamental JDBC workflow: Connection → Statement → ResultSet.

### Why JDBC Exists

**JavaScript comparison:** In Node.js, each database has its own library (pg for PostgreSQL, mysql for MySQL). Java uses **one API (JDBC)** with different **drivers** underneath. You learn JDBC once, use it everywhere.

**The JDBC abstraction:**
- Connection = database session
- Statement = SQL command executor
- ResultSet = cursor for query results

### Key Concept: JDBC Driver

A JDBC driver is a JAR file that translates JDBC calls into database-specific protocol. For SQLite, you need `sqlite-jdbc-<version>.jar`.

**How it works:**
1. Your code calls JDBC interfaces (Connection, Statement, etc.)
2. SQLite driver implements those interfaces
3. Driver translates to SQLite's C library calls

This is why you can swap databases by just changing the driver and connection string.

### Key Concept: Connection Strings

Format: `jdbc:sqlite:practice_tracker.db`
- `jdbc:` - JDBC protocol
- `sqlite:` - driver type
- `practice_tracker.db` - database file path (created if doesn't exist)

Different databases have different formats:
- PostgreSQL: `jdbc:postgresql://localhost:5432/dbname`
- MySQL: `jdbc:mysql://localhost:3306/dbname`

### Key Concept: try-with-resources

**Problem:** Database connections are expensive OS resources that MUST be closed, or you'll leak file handles.

**JavaScript comparison:** Like `finally` blocks or `.close()` callbacks, but Java has language-level support.

**Pattern:**
```java
try (Connection conn = getConnection()) {
    // Use connection
} // Automatically closed here, even if exception thrown
```

Works with any `AutoCloseable` resource. The compiler guarantees cleanup.

### Key Concept: SQLException

All JDBC operations throw `SQLException` - a checked exception for database errors. Covers:
- Connection failures
- Invalid SQL syntax
- Constraint violations
- Permission errors

You must handle or declare `throws SQLException`.

### Your Tasks

**1. Set up SQLite JDBC driver:**
- Download `sqlite-jdbc.jar` from Maven Central (search "sqlite jdbc download")
- Learn how to compile with classpath: `javac -cp ".:sqlite-jdbc.jar" ClassName.java`
- Learn how to run with classpath: `java -cp ".:sqlite-jdbc.jar" ClassName`

**2. Create DatabaseConnection utility class:**

**Requirements:**
- Static method `getConnection()` that returns a `Connection` object
- Connection string should point to `practice_tracker.db` file
- Static method `initializeDatabase()` that creates a test table if not exists

**APIs to explore:**
- `DriverManager.getConnection(String url)` - creates database connection
- `Connection.createStatement()` - creates Statement object
- `Statement.execute(String sql)` - runs DDL commands like CREATE TABLE
- Look up: "JDBC Connection tutorial"
- Look up: "SQLite CREATE TABLE IF NOT EXISTS"

**3. Test your database connection:**
- In a main method, call `initializeDatabase()`
- Create a simple test table
- Insert a test row
- Query it back and print the result
- Verify the `.db` file appears in your directory

**How to verify it works:**
- No exceptions thrown
- Database file created (check with `ls`)
- Can query the test data back successfully

### Common Mistakes to Watch For

- Forgetting to add driver JAR to classpath (ClassNotFoundException)
- Not using try-with-resources (resource leaks)
- SQL syntax errors in string literals (hard to debug)
- Forgetting `throws SQLException` on methods

### Search Terms

- "JDBC tutorial Oracle"
- "SQLite JDBC driver usage"
- "Java try-with-resources AutoCloseable"
- "DriverManager getConnection example"

---

## Module 9: Categories Table & Foreign Key Basics (45-60 min)

### Learning Objectives

Learn relational database design by creating a normalized schema with two tables. Understand primary keys, foreign keys, and how databases enforce referential integrity.

### Why Normalized Design?

**Denormalized (what you might do first):**
```
sessions: id, date, duration, category_name
```
Problem: "Java", "java", "JAVA" are three different categories. No consistency.

**Normalized (what we're building):**
```
categories: id, name (UNIQUE)
sessions: id, date, duration, category_id (FK to categories)
```
Benefits:
- Category names guaranteed unique and consistent
- Can rename category once, affects all sessions
- Database prevents orphaned sessions (FK constraint)
- Can query "which categories exist?" separately

### Key Concept: Primary Keys

`id INTEGER PRIMARY KEY AUTOINCREMENT`

- **Primary key**: Unique identifier for each row
- **AUTOINCREMENT**: Database assigns sequential IDs (1, 2, 3...)
- **Why integers?** Fast lookups, small storage, language-agnostic

**JavaScript comparison:** Like array indices, but permanent and database-managed.

### Key Concept: Foreign Keys

`FOREIGN KEY (category_id) REFERENCES categories(id)`

This tells SQLite: "Every category_id in sessions MUST match an id in categories."

**Referential integrity enforcement:**
- Can't insert session with category_id=99 if category 99 doesn't exist
- Can't delete category if sessions reference it (by default)
- Database guarantees data consistency

This is a core relational database feature that file-based storage can't provide.

### Key Concept: UNIQUE Constraint

`name TEXT NOT NULL UNIQUE`

Prevents duplicate category names. Database rejects:
- Adding "Java" when "Java" already exists
- NULL names (NOT NULL + UNIQUE)

**Tradeoff:** Makes inserts slightly slower (checks index) but guarantees data quality.

### Key Concept: PreparedStatement

**The problem (SQL injection):**
```java
// NEVER DO THIS
String sql = "SELECT * FROM users WHERE name = '" + userName + "'";
// If userName = "'; DROP TABLE users; --" you just deleted your data
```

**The solution (PreparedStatement):**
- Uses `?` placeholders for values
- Database treats values as DATA, never as SQL CODE
- Automatically escapes special characters

**Pattern:**
```java
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
pstmt.setString(1, userName); // 1-indexed, not 0-indexed
ResultSet rs = pstmt.executeQuery();
```

This is mandatory for production code. Always use PreparedStatement for user input.

### Key Concept: Generated Keys

When you INSERT with AUTOINCREMENT, database assigns the ID. How do you get it back?

**Pattern:**
```java
PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
pstmt.executeUpdate();
ResultSet generatedKeys = pstmt.getGeneratedKeys();
if (generatedKeys.next()) {
    int id = generatedKeys.getInt(1);
    // Now you know what ID was assigned
}
```

This is important for foreign key relationships - you need the category ID to reference it.

### Your Tasks

**1. Define the schema in initializeDatabase():**

**Requirements:**
- categories table with id (PK, AUTOINCREMENT) and name (TEXT, NOT NULL, UNIQUE)
- sessions table with id (PK, AUTOINCREMENT), date (TEXT), duration (INTEGER), category_id (INTEGER, NOT NULL), notes (TEXT)
- Foreign key from sessions.category_id to categories.id

**SQL to research:**
- CREATE TABLE IF NOT EXISTS
- PRIMARY KEY, AUTOINCREMENT
- FOREIGN KEY syntax in SQLite
- UNIQUE constraint

**2. Create Category class (data model):**

**Requirements:**
- Represents a category from the database
- Fields: id (int), name (String)
- Constructor validation: name cannot be null or empty
- Getters for both fields
- toString() for debugging

**3. Create CategoryManager class (business logic):**

**Requirements:**
- Method to add a category (INSERT) - should return the generated ID
- Method to list all categories (SELECT) - returns List<Category>
- Method to find category by name - returns Category or null
- Use PreparedStatement for all SQL operations
- Handle UNIQUE constraint violations gracefully

**APIs to explore:**
- `Connection.prepareStatement(sql)` and `prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)`
- `PreparedStatement.setString(int index, String value)`
- `PreparedStatement.setInt(int index, int value)`
- `PreparedStatement.executeUpdate()` - for INSERT/UPDATE/DELETE
- `PreparedStatement.executeQuery()` - for SELECT
- `ResultSet.next()` - moves cursor, returns false when no more rows
- `ResultSet.getInt(String columnName)` or `ResultSet.getInt(int columnIndex)`
- `ResultSet.getString(String columnName)`

**4. Update PracticeTracker with category commands:**

**Requirements:**
- New command: `add-category <name>` - adds category, shows generated ID
- New command: `list-categories` - shows all categories
- Route these commands in your switch statement
- Show helpful error if category already exists

**How to verify it works:**
- Can add categories successfully
- Duplicate category names rejected
- Empty names rejected
- list-categories shows all added categories in order
- Each category has unique sequential ID

### Common Mistakes to Watch For

- PreparedStatement parameters are 1-indexed (not 0-indexed like arrays)
- Forgetting to call `executeUpdate()` or `executeQuery()` - PreparedStatement doesn't auto-execute
- Not checking `ResultSet.next()` before calling `getInt()` - crashes if no results
- SQL syntax errors in string literals (use SQLite docs for reference)
- Forgetting NOT NULL constraint allows null values by default

### Search Terms

- "SQLite foreign key constraint"
- "PreparedStatement setString example"
- "JDBC ResultSet iteration"
- "Statement RETURN_GENERATED_KEYS"
- "SQL injection attack example"

---

## Module 10: Sessions with Foreign Keys & JOINs (60-75 min)

### Learning Objectives

Rebuild Session and SessionManager to use the database with foreign key relationships. Learn SQL JOIN operations - one of the most powerful features of relational databases.

### Why JOINs Matter

**The problem:** sessions table stores category_id (integer), but users want to see category names (strings).

**Bad solution:** Query categories separately for each session (N+1 queries problem).

**Good solution:** JOIN - one query that combines data from both tables.

**JOIN types:**
- **INNER JOIN**: Only rows where FK matches PK (sessions WITH categories)
- **LEFT JOIN**: All sessions, even if category missing (we won't use this - FK prevents missing categories)

### Key Concept: INNER JOIN

**Syntax:**
```sql
SELECT sessions.*, categories.name as category_name
FROM sessions
INNER JOIN categories ON sessions.category_id = categories.id
```

**What this does:**
1. For each row in sessions
2. Find matching row in categories where category_id = id
3. Combine columns from both tables
4. Return as single result set

**Aliases for readability:**
```sql
SELECT s.id, s.date, s.duration, c.name as category_name
FROM sessions s
INNER JOIN categories c ON s.category_id = c.id
```

`s` and `c` are aliases - shorthand for table names.

### Key Concept: Mapping JOIN Results to Objects

When you JOIN tables, ResultSet contains columns from BOTH tables. You need to extract them carefully.

**Pattern:**
```java
while (rs.next()) {
    int id = rs.getInt("id"); // or rs.getInt(1) for column position
    String date = rs.getString("date");
    String categoryName = rs.getString("category_name"); // from JOIN
    // Build object with all data
}
```

**Column naming:** Use `AS` in SQL to rename columns if names conflict.

### Key Concept: Denormalization for Display

**Tradeoff:** Session object could store EITHER:
1. Just category_id (minimal, always requires JOIN to display)
2. Both category_id AND category_name (redundant, but convenient)

**Recommended approach:** Store both. category_id for database operations, category_name for display without constant JOINs.

This is a practical denormalization - the database is normalized, but your in-memory objects can cache display data.

### Your Tasks

**1. Update Session class:**

**Requirements:**
- Add fields: id (int, 0 if not yet saved), categoryId (int), categoryName (String), notes (String, nullable)
- Keep existing fields: date (LocalDate), duration (int)
- Constructor for creating new sessions (before database save): takes date, duration, categoryId, categoryName, notes
- Constructor for loading from database (after save): takes all fields including id
- Add getters for new fields
- Update toString() to show category and notes
- Keep validation: duration 1-1440, date not null, categoryId > 0

**2. Rebuild SessionManager:**

**Requirements:**
- Remove ALL file I/O code (loadFromFile, saveToFile)
- Constructor no longer needs to load - queries database on demand
- Method to add session: takes date, duration, categoryId, notes - does INSERT, returns generated session ID
- Method to list sessions: does SELECT with INNER JOIN to get category names, returns List<Session>
- Method to get total minutes: either SUM in SQL, or load all and sum in Java
- All methods use PreparedStatement

**SQL you'll need:**
- INSERT INTO sessions (date, duration, category_id, notes) VALUES (?, ?, ?, ?)
- SELECT s.id, s.date, s.duration, s.category_id, c.name, s.notes FROM sessions s INNER JOIN categories c ON s.category_id = c.id
- SELECT SUM(duration) FROM sessions (for total)

**APIs to explore:**
- `LocalDate.toString()` - converts to "YYYY-MM-DD" for database storage
- `LocalDate.parse(String)` - converts from "YYYY-MM-DD" to LocalDate
- `PreparedStatement.setString()` and `setInt()` for parameters
- `ResultSet.getString()` may return null for nullable columns - handle this

**3. Update PracticeTracker add command:**

**Requirements:**
- Change to: `add <duration> <category-name> [notes...]`
- Look up category by name using CategoryManager
- If category doesn't exist, show error + list available categories
- If category exists, use its ID to create session
- Handle optional notes (remaining args after category)

**4. Update PracticeTracker list command:**

**Requirements:**
- Display sessions with category names (from JOIN)
- Show date, duration, category
- Optionally show first 50 chars of notes if present
- Format nicely for users

**How to verify it works:**
- Can add session with valid category name
- Can't add session with invalid category (helpful error shown)
- List shows all sessions with correct category names
- Sessions with notes display notes
- Sessions without notes don't show "null"
- Total still calculates correctly
- Data persists between app restarts

### Common Mistakes to Watch For

- JOIN syntax errors (typos in ON clause break everything)
- Forgetting table aliases with ambiguous column names (s.id vs c.id)
- Not handling NULL notes (ResultSet.getString() returns null)
- Category lookup case sensitivity ("Java" ≠ "java")
- Parsing command-line args incorrectly when notes contain spaces

### Search Terms

- "SQL INNER JOIN tutorial"
- "SQLite JOIN syntax"
- "Java LocalDate format string"
- "ResultSet getString null handling"
- "SQL table aliases"

---

## Module 11: Java Streams for Statistics (60-75 min)

### Learning Objectives

Learn functional programming in Java using the Streams API. Compare imperative (loops) vs declarative (streams) approaches to data processing.

### Why Streams Matter

**JavaScript comparison:** Streams are like Array methods (map, filter, reduce) but lazily evaluated.

**The paradigm shift:**
- **Imperative**: "HOW to do it" - loops, accumulators, temporary variables
- **Declarative**: "WHAT to compute" - pipeline of transformations

**Example - total minutes by category:**

**Imperative (loops):**
```java
Map<String, Integer> totals = new HashMap<>();
for (Session s : sessions) {
    totals.put(s.getCategory(), totals.getOrDefault(s.getCategory(), 0) + s.getDuration());
}
```

**Declarative (streams):**
```java
Map<String, Integer> totals = sessions.stream()
    .collect(Collectors.groupingBy(Session::getCategory, Collectors.summingInt(Session::getDuration)));
```

Streams are more concise once you understand the vocabulary.

### Key Concept: Stream Pipeline

**Pattern:**
1. **Source**: Create stream from collection
2. **Intermediate operations**: Transform stream (lazy - don't execute yet)
3. **Terminal operation**: Trigger computation and produce result

**Example:**
```java
sessions.stream()              // source
    .filter(s -> ...)          // intermediate
    .map(s -> ...)             // intermediate
    .collect(Collectors.toList()); // terminal
```

Nothing happens until terminal operation called (lazy evaluation).

### Key Concept: Method References

Instead of lambda `s -> s.getDuration()`, you can use method reference `Session::getDuration`.

**Types:**
- `Session::getDuration` - instance method
- `Integer::parseInt` - static method
- `System.out::println` - instance method on specific object

Cleaner syntax for simple cases.

### Key Concept: Collectors

Collectors are terminal operations that accumulate stream elements.

**Common collectors:**
- `Collectors.toList()` - collect to ArrayList
- `Collectors.groupingBy(keyFunction, valueCollector)` - group by key
- `Collectors.summingInt(intFunction)` - sum integer values
- `Collectors.counting()` - count elements
- `Collectors.averagingInt(intFunction)` - calculate average

These handle the accumulation logic so you don't write loops.

### Key Concept: Optional

`Optional<T>` represents a value that might not exist. Forces you to handle the "no result" case.

**Why it exists:** In Java, methods can't return null without documentation saying so. Optional makes "maybe no value" explicit in the type system.

**Pattern:**
```java
Optional<Session> longest = sessions.stream()
    .max(Comparator.comparingInt(Session::getDuration));

if (longest.isPresent()) {
    Session s = longest.get();
    // use it
} else {
    // handle no sessions case
}
```

Or use `longest.orElse(defaultValue)` for fallback.

### Stream Operations vs SQL

**Key insight:** Many statistics can be computed EITHER in SQL OR with Streams.

**SQL approach (more efficient):**
```sql
SELECT category_name, SUM(duration) FROM sessions GROUP BY category_name
```
Computation happens in database.

**Stream approach (more flexible):**
```java
sessions.stream().collect(Collectors.groupingBy(...))
```
Computation happens in Java after loading all data.

**Tradeoff:**
- SQL: Faster for large datasets (doesn't load all into memory)
- Streams: More flexible (can combine with business logic), works with any data source

For this project, implement BOTH to understand the tradeoff.

### Your Tasks

**1. Add statistics methods to SessionManager:**

**Requirements using Streams:**
- Total minutes by category: returns Map<String, Integer> (use groupingBy + summingInt)
- Average session duration: returns double (use averagingInt, handle empty with orElse)
- Sessions in date range: takes start/end dates, returns filtered List<Session>
- Longest session: returns Optional<Session> (use max with Comparator)
- Session count by category: returns Map<String, Long> (use groupingBy + counting)

**Alternative: some statistics in SQL:**
- Total by category: SELECT category_name, SUM(duration) FROM ... GROUP BY category_name
- Average duration: SELECT AVG(duration) FROM sessions
- Count by category: SELECT category_name, COUNT(*) FROM ... GROUP BY category_name

Implement BOTH approaches for at least one statistic to compare.

**APIs to explore:**
- `list.stream()` - create stream from List
- `stream.filter(predicate)` - keep elements matching condition
- `stream.map(function)` - transform elements
- `stream.collect(collector)` - terminal operation
- `Collectors.groupingBy(keyFunc, valueCollector)` - group and aggregate
- `Collectors.summingInt(intFunction)` - sum integers
- `Collectors.counting()` - count elements
- `stream.max(comparator)` - find maximum (returns Optional)
- `Comparator.comparingInt(intFunction)` - create comparator
- Look up: "Java 8 Streams tutorial"
- Look up: "Collectors groupingBy examples"

**2. Add statistics commands to PracticeTracker:**

**Requirements:**
- `stats` command: show total sessions, total minutes, average duration, longest session
- `breakdown` command: show minutes and session count per category (table format)
- `recent <days>` command: show sessions from last N days

**3. Handle edge cases:**
- Empty database (no sessions): show zeros, not errors
- No sessions in date range: show helpful message
- Division by zero in average: use Optional.orElse() or check before dividing

**How to verify it works:**
- Add 20+ sessions across multiple categories
- Stats calculations match manual math
- Breakdown shows correct groupings
- Recent filter works with various day counts
- Empty database doesn't crash

### Common Mistakes to Watch For

- Forgetting terminal operation (stream does nothing without it)
- Reusing streams (can only consume once)
- NullPointerException on Optional.get() without checking isPresent()
- Type mismatches in Collectors (Integer vs int)
- Date comparisons with wrong logic (isBefore vs isAfter)

### Search Terms

- "Java Stream API tutorial"
- "Collectors groupingBy summingInt"
- "Java Optional best practices"
- "Method references Java"
- "Comparator comparingInt"
- "SQL GROUP BY aggregate functions"

---

## Testing Strategy

### Manual Testing Steps:
1. Start fresh database
2. Add 3-5 categories (Java, Writing, Exercise, etc.)
3. Add 20+ sessions across different categories and dates
4. Add sessions with and without notes
5. Verify all list/query commands show correct data
6. Manually calculate statistics to verify accuracy
7. Try to add session with invalid category (should fail gracefully)
8. Test edge cases: empty database, single session, all same category

### Verification Checklist:
- [ ] Foreign key constraint prevents invalid category
- [ ] JOIN queries return correct category names
- [ ] NULL notes handled without NullPointerException
- [ ] Stream statistics match SQL GROUP BY results
- [ ] Date range filtering works across year boundaries
- [ ] Performance acceptable with 100+ sessions

## Learning Path Summary

Each module introduces ONE new concept:

1. **Module 8**: JDBC fundamentals (Connection, Statement, ResultSet, try-with-resources, SQLException)
2. **Module 9**: Normalized schema (PRIMARY KEY, FOREIGN KEY, UNIQUE, PreparedStatement, generated keys)
3. **Module 10**: Relational operations (INNER JOIN, foreign key enforcement, object mapping from complex queries)
4. **Module 11**: Functional programming (Streams, Collectors, Optional, method references, declarative vs imperative)

Total estimated time: 4-5 hours of focused learning

You'll write all implementations yourself. I'll provide concept explanations, API pointers, and review your code.

## References

- JDBC Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/
- SQLite Documentation: https://www.sqlite.org/docs.html
- Java Streams Guide: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
- PreparedStatement JavaDoc: Search "Java PreparedStatement"