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
- **File I/O**: 🌱 Not started
- **Exception Handling**: 🌱 Not started

## Project Modules Completed
- [x] Module 1: Project Setup & Basic Structure
- [x] Module 2: Session Data Model
- [x] Module 3: SessionManager - Core Logic
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

## Common Mistakes & Corrections

### Module 1
- **None observed** - User correctly implemented bounds checking and handled multiple cases appropriately.

### Module 2
- **None observed** - Clean implementation with proper use of `this` keyword, correct getter naming, and @Override annotation.

### Module 3
- **None observed** - Clean implementation with proper encapsulation, correct use of generics, and good defensive programming instincts.

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

## Review Before Next Session

### What We Built
- `PracticeTracker.java` - CLI entry point (Module 1)
- `Session.java` - Immutable data model with date and duration (Module 2)
- `SessionManager.java` - Business logic layer managing collection of sessions (Module 3)

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

### Ready For Module 4
Next we'll add **file persistence - writing**. This will introduce:
- File I/O with `java.nio.file` package
- `try-with-resources` for automatic resource cleanup
- Checked exceptions and why Java forces you to handle them
- String formatting for serialization
- The difference between in-memory state and persistent storage
