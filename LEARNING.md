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
- **Static Methods**: ✅ Understood - Belongs to class, not instance; contrasts with instance methods
- **Static Typing**: ✅ Understood - Types declared explicitly, enforced at compile time
- **Classes & Objects**: ✅ Understood - Class as blueprint, object as instance
- **Constructors**: ✅ Understood - Special method for initialization, no return type
- **Encapsulation**: ✅ Understood - Private fields with public getters for controlled access
- **Immutability**: ✅ Understood - No setters = object can't change after creation
- **Method Overriding**: 🌿 Practicing - Override toString() to customize behavior
- **Imports**: ✅ Understood - Must import classes from other packages (java.time.LocalDate)
- **File I/O**: 🌱 Not started
- **Exception Handling**: 🌱 Not started
- **Collections Framework**: 🌱 Not started

## Project Modules Completed
- [x] Module 1: Project Setup & Basic Structure
- [x] Module 2: Session Data Model
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

### Module 2: Session Data Model
- **Class as Data Model**: Define structure explicitly with fields and types
- **Private Fields**: `private LocalDate date` and `private int duration` - hide internal state
- **Constructor**: `public Session(LocalDate date, int duration)` - no return type, initializes object
- **this keyword**: Disambiguates between parameters and fields when names match
- **Getter Methods**: `getDate()` and `getDuration()` - Java Bean naming convention
- **LocalDate API**: `java.time.LocalDate` - type-safe date handling, immutable
- **@Override annotation**: Indicates intentional method override, catches typos
- **toString() Method**: Returns string representation for debugging, called automatically by println

## Questions Asked & Insights

### Module 1
**Insight**: Java's compile-time safety comes from explicit contracts. Arrays are always typed (`String[]`), never undefined or null. The type system ensures `args` is always a `String[]`, eliminating entire categories of runtime errors that would occur in JavaScript.

**Pattern Observed**: Senior engineering instincts transferred well - immediately thought about edge cases (empty args, too many args) rather than just the happy path.

### Module 2
**Insight**: Encapsulation and immutability aren't just "nice to have" - they're enforced by design. Private fields + no setters = compiler prevents mutation. In JS, immutability requires discipline; in Java, the type system enforces it.

**Design Choice Observed**: In toString(), chose to call getters (`this.getDate()`) rather than access fields directly (`this.date`). Both work, but getter approach is more defensive and would handle any future logic in getters.

**Key Realization**: The verbosity serves a purpose - `private LocalDate date` + constructor + getter is explicit specification that the compiler can verify. Can't create invalid Sessions, can't mutate them accidentally. The code IS the documentation.

## Common Mistakes & Corrections

### Module 1
- **None observed** - User correctly implemented bounds checking and handled multiple cases appropriately.

### Module 2
- **None observed** - Clean implementation with proper use of `this` keyword, correct getter naming, and @Override annotation.

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

## Review Before Next Session

### What We Built
- `PracticeTracker.java` - CLI entry point (Module 1)
- `Session.java` - Immutable data model with date and duration (Module 2)

### Key Takeaways
- **Encapsulation pattern**: private fields + constructor + getters = controlled, immutable objects
- **Type safety**: Compiler enforces contracts - impossible to create invalid Sessions
- **this keyword**: Critical for disambiguating parameters from fields
- **@Override**: Best practice for overriding methods, catches mistakes
- **Java Bean conventions**: `getFieldName()` pattern for getters

### Concepts to Reinforce in Module 3
- Static typing (will use `ArrayList<Session>` with generics)
- Class structure (will build SessionManager following same patterns)
- Imports (will need java.util imports)

### Ready For Module 3
Next we'll create the `SessionManager` class - business logic layer. This will introduce:
- ArrayList and the Collections framework
- Generics (`ArrayList<Session>`)
- Method design (public vs private)
- Accumulator patterns (calculating totals)
- Separation of concerns (manager vs data model)
