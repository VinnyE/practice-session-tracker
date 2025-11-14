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
- **Static vs Instance**: ✅ Understood - Static belongs to class, instance belongs to objects with state
- **Static Typing**: ✅ Understood - Types declared explicitly, enforced at compile time
- **Classes & Objects**: ✅ Understood - Class as blueprint, object as instance
- **Constructors**: ✅ Understood - Special method for initialization, no return type
- **Encapsulation**: ✅ Understood - Private fields with public getters for controlled access
- **Immutability**: ✅ Understood - No setters = object can't change after creation
- **Method Overriding**: ✅ Understood - Override toString() to customize behavior
- **Imports**: ✅ Understood - Must import classes from other packages (java.time, java.util)
- **Generics**: ✅ Understood - Type parameters like `<Session>` for compile-time type safety
- **ArrayList**: ✅ Understood - Dynamic, resizable collection from java.util
- **Enhanced For-Loop**: ✅ Understood - `for (Type item : collection)` syntax
- **Accumulator Pattern**: ✅ Understood - Initialize, loop, accumulate (sum/count/etc)
- **Defensive Copying**: ✅ Understood - Return copies to protect internal state
- **Separation of Concerns**: ✅ Understood - Data model vs business logic vs UI layers
- **Checked Exceptions**: ✅ Understood - Must handle or declare `throws`, enforced at compile time
- **File I/O (NIO)**: ✅ Understood - Path, Paths, Files APIs for modern Java file operations
- **Exception Propagation**: ✅ Understood - Using `throws` to let exceptions bubble up call stack
- **File Reading (NIO)**: ✅ Understood - Files.readAllLines() to read entire file into List<String>
- **File Existence Check**: ✅ Understood - Files.exists() to safely check before reading
- **String Parsing**: ✅ Understood - String.split() to parse CSV format into String array
- **Type Conversion**: ✅ Understood - Integer.parseInt() and LocalDate.parse() for explicit conversions
- **Deserialization**: ✅ Understood - Converting text back into typed objects (inverse of serialization)
- **Constructor I/O**: ✅ Understood - Loading state in constructor, declaring throws IOException

## Project Modules Completed
- [x] Module 1: Project Setup & Basic Structure
- [x] Module 2: Session Data Model
- [x] Module 3: SessionManager - Core Logic
- [x] Module 4: File Persistence - Writing
- [x] Module 5: File Persistence - Reading
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

### Module 2: Session Data Model
- **Class as Data Model**: Define structure explicitly with fields and types
- **Private Fields**: `private LocalDate date` and `private int duration` - hide internal state
- **Constructor**: `public Session(LocalDate date, int duration)` - no return type, initializes object
- **this keyword**: Disambiguates between parameters and fields when names match
- **Getter Methods**: `getDate()` and `getDuration()` - Java Bean naming convention
- **LocalDate API**: `java.time.LocalDate` - type-safe date handling, immutable
- **@Override annotation**: Indicates intentional method override, catches typos
- **toString() Method**: Returns string representation for debugging, called automatically by println

### Module 3: SessionManager - Core Logic
- **ArrayList<T>**: Dynamic, resizable collection from `java.util` package
- **Generics**: `ArrayList<Session>` enforces type safety - can only hold Session objects
- **Diamond Operator**: `new ArrayList<>()` - compiler infers type from declaration
- **Manager Pattern**: Dedicated class for business logic, encapsulates collection and operations
- **Instance State**: Manager holds data (sessions list), so it's instance-based not static
- **add() Method**: `sessions.add(session)` - append to ArrayList
- **Enhanced For-Loop**: `for (Session s : sessions)` - iterate without index
- **Accumulator Pattern**: Initialize to 0, loop through collection, accumulate sum
- **Defensive Copy**: `new ArrayList<>(this.sessions)` - copy constructor protects internal state
- **Separation of Concerns**: Session (data) + SessionManager (logic) + PracticeTracker (UI)

### Module 4: File Persistence - Writing
- **java.nio.file Package**: Modern file I/O APIs - Path, Paths, Files
- **Path Interface**: Type-safe representation of file system paths (vs raw strings)
- **Paths.get()**: Factory method to create Path objects from strings
- **Files.write()**: High-level method to write List<String> to file, one element per line
- **Checked Exceptions**: IOException must be caught or declared with `throws`
- **Exception Propagation**: `throws IOException` in method signature passes responsibility to caller
- **Private Helper Methods**: `saveToFile()` as private method for separation of concerns
- **CSV Serialization**: Converting objects to text format (date,duration per line)
- **Call Chain**: saveToFile() → addSession() → main(), each declares throws
- **Automatic Resource Management**: Files.write() handles file handles internally

### Module 5: File Persistence - Reading
- **Files.readAllLines()**: Reads entire file, returns List<String> with one element per line
- **Files.exists()**: Boolean check for file existence before attempting to read
- **String.split()**: Parses delimited strings into String arrays (CSV parsing)
- **Integer.parseInt()**: Explicit conversion from String to int (throws NumberFormatException if invalid)
- **LocalDate.parse()**: Parses ISO-8601 date strings (YYYY-MM-DD) into LocalDate objects
- **Deserialization Pattern**: Text → parse → convert types → reconstruct objects
- **Constructor with I/O**: loadFromFile() called from constructor, which declares throws IOException
- **State Reconstruction vs State Change**: Loading doesn't trigger persistence (internal sessions.add() vs public addSession())
- **Round Trip**: Complete persistence cycle: Object → CSV → Object produces same data
- **Performance Consideration**: Avoid unnecessary file writes during bulk loading operations

## Questions Asked & Insights

### Module 1
**Insight**: Java's compile-time safety comes from explicit contracts. Arrays are always typed (`String[]`), never undefined or null. The type system ensures `args` is always a `String[]`, eliminating entire categories of runtime errors that would occur in JavaScript.

**Pattern Observed**: Senior engineering instincts transferred well - immediately thought about edge cases (empty args, too many args) rather than just the happy path.

### Module 2
**Insight**: Encapsulation and immutability aren't just "nice to have" - they're enforced by design. Private fields + no setters = compiler prevents mutation. In JS, immutability requires discipline; in Java, the type system enforces it.

**Design Choice Observed**: In toString(), chose to call getters (`this.getDate()`) rather than access fields directly (`this.date`). Both work, but getter approach is more defensive and would handle any future logic in getters.

**Key Realization**: The verbosity serves a purpose - `private LocalDate date` + constructor + getter is explicit specification that the compiler can verify. Can't create invalid Sessions, can't mutate them accidentally. The code IS the documentation.

### Module 3
**Insight**: Generics provide compile-time type safety. `ArrayList<Session>` isn't just documentation - the compiler enforces it. Try to add a String to it? Compile error. This is Java's philosophy: catch mistakes at compilation, not at 3am in production.

**Design Decision**: Initially returned `Session[]` array from `listSessions()` to prevent external modification of internal ArrayList. After discussing tradeoffs (type consistency vs defensive copying), refactored to return `new ArrayList<>(this.sessions)` - defensive copy with same type. This reduces cognitive load while maintaining encapsulation.

**Key Realization**: The manager pattern solves the "where does this logic live?" question. In JS, you might scatter functions around. Java encourages explicit organization: Session = data, SessionManager = logic, PracticeTracker = UI. When we add persistence in Module 4, we'll only touch SessionManager.

**Refactoring Experience**: Had working code (array version), identified improvement (type consistency), made the change, verified it works. This is how code evolves - not just "make it work" but "make it right."

### Module 4
**Insight**: Checked exceptions are compile-time contracts. When a method declares `throws IOException`, it's saying "this operation interacts with the outside world and might fail in ways beyond my control." The compiler forces you to acknowledge this. In JavaScript, you can ignore file I/O failures until they bite you in production. In Java, you can't even compile without a plan.

**Philosophy Comparison**: JavaScript says "be optimistic, handle errors if they happen." Java says "be defensive, prove you have a plan before you ship." Neither is wrong - they optimize for different things. JS optimizes for velocity and flexibility. Java optimizes for reliability and catching bugs before production.

**Key Realization**: Files.write() does SO MUCH under the hood - opens file, creates BufferedWriter, writes lines, flushes buffers, closes handles, all with automatic resource management. In Node.js, you'd handle much of this manually. Java's stdlib gives you high-level operations that "just work" but forces you to handle failures.

**Design Pattern**: Created private `saveToFile()` helper method. This keeps `addSession()` focused on its core responsibility while making file I/O reusable. Good separation of concerns - if we change how we persist (database instead of files), we only touch one method.

### Module 5
**Insight**: Deserialization is the inverse of serialization - it's the complete round trip. The text format you write must exactly match what you expect to parse. Java's explicit type conversions (`Integer.parseInt()`, `LocalDate.parse()`) throw exceptions immediately if the format is wrong, unlike JavaScript's loose parsing which can silently fail or coerce incorrectly.

**Design Realization**: Public methods (`addSession()`) enforce business logic and side effects (persistence). Internal code can manipulate state directly (`sessions.add()`) when those side effects aren't appropriate. Loading existing data is state *reconstruction*, not state *change*, so it shouldn't trigger the persistence side effect. This distinction matters for performance (avoid redundant writes) and correctness (avoid triggering logs/events for reconstructed state).

**Key Pattern Recognition**: The serialization round trip (object → text → object) is fundamental to all data persistence, whether it's CSV files, JSON APIs, SQL databases, or binary protocols. The concepts learned here (serialize, deserialize, type safety, format consistency) transfer directly to those more complex scenarios.

## Common Mistakes & Corrections

### Module 1
- **None observed** - User correctly implemented bounds checking and handled multiple cases appropriately.

### Module 2
- **None observed** - Clean implementation with proper use of `this` keyword, correct getter naming, and @Override annotation.

### Module 3
- **None observed** - Clean implementation with proper encapsulation, correct use of generics, and good defensive programming instincts.

### Module 4
- **Minor typo**: Initially wrote `"session.txt"` instead of `"sessions.txt"` (missing 's'). Caught quickly and fixed.

### Module 5
- **Initial implementation issue**: First version called `this.addSession()` inside `loadFromFile()`, which triggered unnecessary file writes during loading. Recognized the performance issue when prompted and fixed by using `this.sessions.add()` directly instead.

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

### Data Modeling
- **JS**: `const session = { date: '2025-11-10', duration: 45 }` - plain object, no enforcement
- **Java**: Explicit class with typed fields, constructor contract, controlled access through getters

### Immutability
- **JS**: Use `Object.freeze()` or trust/discipline to not mutate
- **Java**: Design it in - private fields + no setters = compiler enforces immutability

### Type Safety
- **JS**: `session.duraton = 45` (typo) - silent bug, undefined behavior
- **Java**: `session.getDuraton()` - compilation error, caught immediately

### Collections
- **JS**: `const sessions = []` - can hold any type, no enforcement
- **Java**: `ArrayList<Session> sessions = new ArrayList<>()` - can ONLY hold Sessions, compiler enforces

### Iteration
- **JS**: `for (const session of sessions)` - works on any iterable
- **Java**: `for (Session session : sessions)` - enhanced for-loop, type declared explicitly

### File I/O
- **JS**: `fs.writeFileSync('file.txt', data)` or `await fs.promises.writeFile()` - optional error handling
- **Java**: `Files.write(path, lines)` - MUST handle IOException, won't compile otherwise

### Error Handling Philosophy
- **JS**: Try the operation, catch errors if they happen (optional)
- **Java**: Declare what can fail in method signature (`throws`), compiler enforces handling

## Review Before Next Session

### What We Built
- `PracticeTracker.java` - CLI entry point (Module 1)
- `Session.java` - Immutable data model with date and duration (Module 2)
- `SessionManager.java` - Business logic layer managing collection of sessions (Module 3)
- `sessions.txt` - Persistent storage file with CSV format (Module 4)

### Key Takeaways
- **Manager pattern**: Encapsulates collection and operations in dedicated class
- **Generics**: `ArrayList<Session>` - compile-time type safety for collections
- **Defensive copying**: Return `new ArrayList<>(this.sessions)` to protect internal state
- **Separation of concerns**: Data model (Session) vs logic (SessionManager) vs UI (PracticeTracker)
- **Accumulator pattern**: Initialize to 0, loop, accumulate - standard algorithm for sums
- **Copy constructor**: `new ArrayList<>(existingList)` creates shallow copy

### Concepts to Reinforce in Module 4
- Encapsulation (SessionManager already uses private fields)
- Class structure (will continue same patterns for persistence methods)
- ArrayList operations (will iterate when writing to file)
- Enhanced for-loop (will use when saving sessions)

### Concepts to Reinforce in Module 5
- Checked exceptions (will continue using throws or try-catch)
- File I/O with NIO (will use Files.readAllLines or similar)
- String manipulation (will parse CSV lines)
- ArrayList operations (will load into sessions list)
- Enhanced for-loop (will iterate through lines)

### Concepts to Reinforce in Module 6
- Exception handling (will continue with IOException from file operations)
- String parsing (will parse command-line arguments)
- LocalDate operations (will use LocalDate.now() when adding sessions)
- ArrayList operations (will list sessions, calculate totals)
- Method calls between classes (PracticeTracker → SessionManager)

### Ready For Module 6
Next we'll implement **command routing and CLI interface**. This will introduce:
- Switch statements (or enhanced switch) for command routing
- Command-line argument parsing and validation
- String formatting for user-friendly output
- Integrating all the pieces: CLI → SessionManager → Session → File
- Converting user input (string duration) to proper types
- Error messages for invalid commands/arguments
