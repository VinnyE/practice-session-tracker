# Java Learning Journey: Practice Session Tracker

## My Background
- **Primary Language**: JavaScript (Senior Frontend Engineer)
- **Goal**: Transition to backend engineering, understand Java fundamentals
- **Learning Style**: Want to understand the "why" behind Java's design, not just the "how"
- **Key Questions**: Why is Java verbose? How does static typing work? How does Java OOP differ from JS?

## Current Understanding Level
*Legend: 🌱 Learning | 🌿 Practicing | ✅ Understood*

### Core Concepts
- **Java Project Structure**: ✅ Understood - Files must match class names, one public class per file
- **main Method Signature**: ✅ Understood - `public static void main(String[] args)` entry point
- **Command-line Arguments**: ✅ Understood - `String[] args` array, access with bounds checking
- **Static Methods**: 🌿 Practicing - Understand belongs to class, not instance
- **Static Typing**: 🌿 Practicing - Arrays are typed, length is property not method
- **Classes & Objects**: 🌱 Know OOP basics, new to Java's approach
- **File I/O**: 🌱 Not started
- **Exception Handling**: 🌱 Not started
- **Collections Framework**: 🌱 Not started

## Project Modules Completed
- [x] Module 1: Project Setup & Basic Structure
- [ ] Module 2: Session Data Model
- [ ] Module 3: SessionManager - Core Logic
- [ ] Module 4: File Persistence - Writing
- [ ] Module 5: File Persistence - Reading
- [ ] Module 6: Commands Implementation
- [ ] Module 7: Polish & Error Handling

## Concepts Covered

### Module 1: Project Setup & Basic Structure
- **Class Declaration**: `public class ClassName` - must match filename exactly
- **Entry Point**: `public static void main(String[] args)` - the JVM's starting point
- **Static Methods**: Methods that belong to the class itself, not instances
- **Array Access**: Must check bounds before accessing elements
- **String Concatenation**: Uses `+` operator like JavaScript
- **Conditional Logic**: if/else if/else works identically to JavaScript

## Questions Asked & Insights

### Module 1
**Insight**: Java's compile-time safety comes from explicit contracts. Arrays are always typed (`String[]`), never undefined or null. The type system ensures `args` is always a `String[]`, eliminating entire categories of runtime errors that would occur in JavaScript.

**Pattern Observed**: Senior engineering instincts transferred well - immediately thought about edge cases (empty args, too many args) rather than just the happy path.

## Common Mistakes & Corrections

### Module 1
- **None observed** - User correctly implemented bounds checking and handled multiple cases appropriately.

## JavaScript vs Java Comparisons

### Arrays
- **JS**: `const args = process.argv.slice(2)` - always an array, can be empty
- **Java**: `String[] args` - typed array, always exists, use `.length` (property not method)

### Entry Points
- **JS**: File executes top-to-bottom, or `package.json` specifies entry
- **Java**: Must have `public static void main(String[] args)` - explicit contract with JVM

### File Structure
- **JS**: One file can export multiple functions/classes
- **Java**: One public class per file, filename must match class name exactly

## Review Before Next Session

### What We Built
- `PracticeTracker.java` with entry point that reads and prints command-line arguments
- Handles multiple cases: 0 args, 1 arg, 2 args, 3+ args

### Key Takeaways
- Java requires explicit structure: matching filenames, specific method signatures
- Static methods belong to the class, not instances (important for entry point)
- Array bounds checking is your responsibility (runtime exception if you don't)

### Ready For Module 2
Next we'll create the `Session` class - our first data model. This will introduce:
- Object-oriented design in Java
- Constructors
- Private fields with public getters (encapsulation)
- Working with `LocalDate` for dates
