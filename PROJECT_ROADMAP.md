# Practice Session Tracker - Learning Roadmap (V1)

## Project Vision
A CLI app that lets you log coding practice sessions with date and duration, then view them back. Simple, focused, and teaches Java fundamentals.

## Architecture Overview
```
PracticeTracker (main class)
    ├── Handles command-line arguments
    ├── Routes to appropriate commands
    └── Uses SessionManager

Session (data model)
    ├── Represents one practice session
    └── Fields: date, duration

SessionManager (core logic)
    ├── Manages collection of sessions
    ├── Handles file persistence
    └── Provides add/list/total operations
```

## Module Breakdown

### Module 1: Project Setup & Basic Structure (30-45 min)
**What We're Building**: The entry point of our app - a main class that can read command-line arguments and print them back.

**Learning Objectives**:
- Understand Java project structure (where files go, naming conventions)
- Learn the `main` method signature and why it looks that way
- Work with `String[] args` (command-line arguments)
- Use `System.out.println()` for basic output

**Java Concepts**:
- `public static void main(String[] args)` - the magic incantation
- Classes as containers for code (vs JS modules/files)
- Static methods (no object needed)
- Package structure and imports

**Why It Matters**: Unlike JS where you can just write code in a file, Java requires a specific structure. Understanding this foundation makes everything else click.

**Deliverable**: A `PracticeTracker.java` file that prints "add", "list", or "total" when you run `java PracticeTracker add`

---

### Module 2: Session Data Model (30-45 min)
**What We're Building**: A `Session` class that represents one practice session (date + duration).

**Learning Objectives**:
- Create a class to model data
- Use constructors to initialize objects
- Understand private fields + public getters (encapsulation)
- Work with `LocalDate` for dates

**Java Concepts**:
- Class definition and object instantiation
- Constructors (vs JS constructor functions)
- `private` vs `public` access modifiers
- Getters (and why we don't just make fields public)
- The `LocalDate` API (Java's date handling)
- `toString()` method for debugging

**Why It Matters**: This is OOP at its core. In JS you might use plain objects `{date, duration}`, but Java encourages you to define the "shape" of your data explicitly. This seems verbose but prevents entire categories of bugs.

**Deliverable**: A `Session.java` class you can instantiate and print. Test it in main.

---

### Module 3: SessionManager - Core Logic (45-60 min)
**What We're Building**: A `SessionManager` class that stores sessions in memory and provides methods to add/list/calculate total.

**Learning Objectives**:
- Use `ArrayList<Session>` to store multiple sessions
- Understand generics (the `<Session>` syntax)
- Implement business logic methods
- Separate concerns (manager vs data model vs main)

**Java Concepts**:
- `ArrayList<T>` and the Collections framework
- Generics and type parameters
- Method design (what's public vs private)
- Loops: for-each syntax for collections
- Accumulator patterns (calculating totals)

**Why It Matters**: JavaScript has arrays and you can push anything into them. Java's `ArrayList<Session>` says "this list ONLY holds Sessions". The compiler enforces this. It's verbose, but you catch errors at compile-time instead of at 3am in production.

**Deliverable**: A `SessionManager.java` class that works in memory. Test all three operations from main.

---

### Module 4: File Persistence - Writing (30-45 min)
**What We're Building**: Add the ability to save sessions to a text file when you add a new one.

**Learning Objectives**:
- Work with `Files` and `Path` APIs
- Use `try-with-resources` for automatic cleanup
- Handle basic exceptions with try-catch
- Design a simple text format for data

**Java Concepts**:
- File I/O with `java.nio.file.*`
- `try-with-resources` statement
- Checked exceptions (why Java forces you to handle them)
- String formatting for output

**Why It Matters**: Unlike JS where you might use callbacks or promises for file operations, Java uses exceptions to handle errors. This is a fundamentally different approach to error handling.

**Deliverable**: Sessions persist to `sessions.txt`. Each line is a session.

---

### Module 5: File Persistence - Reading (30-45 min)
**What We're Building**: Load existing sessions from the file when the app starts.

**Learning Objectives**:
- Read and parse text files
- Convert strings back to objects
- Handle parsing errors gracefully
- Understand data serialization concepts

**Java Concepts**:
- Reading files line-by-line
- String splitting and parsing
- `Integer.parseInt()` and type conversion
- `LocalDate.parse()` with formats
- More exception handling

**Why It Matters**: This completes the persistence cycle. You'll see the difference between Java's explicit type conversions (String → int) vs JavaScript's implicit coercion.

**Deliverable**: App loads sessions on startup. Add a session, restart the app, list sessions - they're still there.

---

### Module 6: Commands Implementation (30-45 min)
**What We're Building**: Wire up the three commands properly: add today's session, list all sessions nicely formatted, show total minutes.

**Learning Objectives**:
- Route commands using switch statements
- Format output for users
- Work with LocalDate.now()
- Parse command-line arguments properly

**Java Concepts**:
- `switch` statements (or enhanced switch in newer Java)
- String formatting and alignment
- Command-line argument parsing
- User-facing vs internal representations

**Why It Matters**: This is where your app becomes usable. You'll learn how to make Java output readable and how to structure a simple CLI.

**Deliverable**: A working CLI: `java PracticeTracker add 45`, `java PracticeTracker list`, `java PracticeTracker total`

---

### Module 7: Polish & Error Handling (30-45 min)
**What We're Building**: Make the app bulletproof - handle bad input, missing files, invalid data, etc.

**Learning Objectives**:
- Validate user input
- Provide helpful error messages
- Handle edge cases (empty file, no sessions, etc.)
- Think about user experience

**Java Concepts**:
- Input validation patterns
- Defensive programming
- Better exception handling
- Exit codes (System.exit)

**Why It Matters**: Real software handles errors gracefully. You'll learn the difference between "it works on my machine" and "it works for users".

**Deliverable**: An app that doesn't crash and tells users what went wrong.

---

## Key Learning Themes Across Modules

1. **Explicitness Over Brevity**: Java makes you say what you mean. Types, access modifiers, exceptions - all explicit. This feels verbose compared to JS, but prevents entire categories of bugs.

2. **Compile-Time vs Runtime**: Many errors you'd catch at runtime in JS (typos, wrong types) are caught by the Java compiler before you even run the code.

3. **Objects All The Way Down**: In Java, everything is in a class. Even your main program. This is different from JS where you can have loose functions and code at the top level.

4. **Exceptions Over Error Objects**: Java uses exceptions for error handling, not return values or error objects. This changes how you structure code.

5. **The Standard Library**: Java's standard library is massive and well-organized. Learning to navigate it (LocalDate, ArrayList, Files) is a key skill.

## After Completion

You'll understand:
- How to structure a Java project
- Basic OOP in Java (classes, encapsulation, separation of concerns)
- Working with the type system
- File I/O and exception handling
- Collections (ArrayList)
- Building a CLI application

You'll be ready to:
- Add features to this app (Version 2: topics, notes, streaks)
- Build other Java applications
- Understand Java codebases you encounter
- Learn more advanced Java concepts (interfaces, inheritance, etc.)
