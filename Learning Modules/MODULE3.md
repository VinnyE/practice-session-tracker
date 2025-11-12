# Module 3: SessionManager - Core Logic

## Learning Objectives

By the end of this module, you'll understand:
- How Java's `ArrayList<T>` provides type-safe collections
- What generics are and why they exist (vs JavaScript's dynamic arrays)
- How to design a "manager" class that encapsulates business logic
- The separation of concerns pattern (data model vs business logic vs UI)
- Accumulator patterns in Java (calculating totals from collections)

## Why This Module Matters

In JavaScript, you'd probably just keep an array of sessions somewhere:
```javascript
const sessions = [];
sessions.push({ date: '2025-11-10', duration: 45 });
```

But this raises questions:
- Where does this array live?
- Who's responsible for adding/listing/calculating?
- How do you ensure you're only adding valid sessions?
- What happens when we add persistence in Module 4?

The **manager pattern** solves this by creating a dedicated class responsible for managing a collection and providing operations on it. This is a fundamental OOP pattern you'll see everywhere in Java.

## Concept 1: ArrayList and Generics

### The Problem Java Solves

In early Java (pre-Java 5), collections could hold ANY object:
```java
ArrayList list = new ArrayList();
list.add("hello");
list.add(42);
list.add(new Session(...));  // Mixed types!
```

This was dangerous. You'd get runtime errors when you assumed something was a String but it was actually an Integer.

**Generics** were introduced to provide compile-time type safety:
```java
ArrayList<Session> sessions = new ArrayList<>();
sessions.add(new Session(...));  // ✅ OK
sessions.add("hello");           // ❌ Compile error!
```

The `<Session>` tells the compiler: "This ArrayList ONLY holds Session objects."

### JavaScript Comparison

In JavaScript:
```javascript
const sessions = [];
sessions.push({ date: '2025-11-10', duration: 45 });
sessions.push("oops, wrong type");  // No error!
sessions[1].duration  // undefined or runtime error
```

You have to maintain discipline manually. In Java, the type system enforces it.

### Why `ArrayList` Instead of Arrays?

Java has built-in arrays (`Session[]`), but they're fixed-size. You must know the size upfront.

`ArrayList<T>` is a dynamic, resizable collection - like JavaScript arrays. It's part of the Java Collections Framework (java.util package).

Key methods you'll need:
- `add(element)` - Append to the end
- `get(index)` - Retrieve by index (like `arr[i]` in JS)
- `size()` - Number of elements (like `arr.length` in JS, but it's a method)
- Enhanced for-loop works: `for (Session s : sessions) { ... }`

**Search**: "Java ArrayList documentation" to see all methods.

## Concept 2: Separation of Concerns

### The Architecture

We're building three distinct layers:

1. **Data Model** (`Session.java`) - What a session IS
   - Just data representation
   - No business logic
   - Immutable (no setters)

2. **Business Logic** (`SessionManager.java`) - What you can DO with sessions
   - Manages the collection
   - Provides operations (add, list, total)
   - Enforces rules (validation, consistency)

3. **User Interface** (`PracticeTracker.java`) - How users interact
   - Parses commands
   - Calls manager methods
   - Formats output

### Why This Matters

When we add file persistence in Module 4, we'll only modify `SessionManager`. The `Session` class and `PracticeTracker` won't change. This is the power of separation of concerns.

In JavaScript, you might have all this in one file with functions scattered around. Java's class structure encourages explicit organization.

## Concept 3: Instance vs Static

`SessionManager` will be an **instance-based** class - you create an object of it:
```java
SessionManager manager = new SessionManager();
manager.addSession(...);
```

Why not make everything static like in `PracticeTracker.main()`?

Because the manager has **state** (the list of sessions). Static members are shared across the entire program. Instance members belong to each object.

Think of it this way:
- Static = "belongs to the class itself" (like utility functions)
- Instance = "each object has its own copy" (like objects with properties)

This is different from JavaScript where you might just have:
```javascript
const sessionManager = {
  sessions: [],
  addSession(s) { this.sessions.push(s); }
}
```

Java makes the distinction explicit with the `static` keyword.

## Your Challenge: Build SessionManager

Create a new file `SessionManager.java` with a class that:

### Requirements

1. **Private field** to store sessions
   - Should be an ArrayList of Session objects
   - Should NOT be accessible from outside the class

2. **Constructor** that initializes the empty collection
   - No parameters needed for now
   - Just set up the ArrayList

3. **addSession method** (public)
   - Takes a Session object as parameter
   - Adds it to the collection
   - Returns nothing (void)

4. **listSessions method** (public)
   - Returns the entire collection
   - No parameters needed
   - Think about: should you return the actual internal list, or something else?

5. **getTotalMinutes method** (public)
   - Calculates sum of all session durations
   - Returns an integer
   - Uses an accumulator pattern (loop through sessions, add up durations)

### API Pointers

- Import: `java.util.ArrayList`
- Creating an ArrayList: Look up "Java ArrayList instantiation with generics"
- The diamond operator `<>` can infer types: `new ArrayList<>()`
- Loop through a collection: `for (Type item : collection) { ... }`

### Design Questions to Consider

1. Should `listSessions()` return the actual internal ArrayList, or a copy? What are the tradeoffs?
2. What happens if someone calls `getTotalMinutes()` on an empty list?
3. Do you need validation in `addSession()`, or is the type system enough?

### Testing Your Implementation

In `PracticeTracker.main()`, test all three operations:

```java
SessionManager manager = new SessionManager();

// Test adding
manager.addSession(new Session(LocalDate.now(), 45));
manager.addSession(new Session(LocalDate.of(2025, 11, 10), 60));
manager.addSession(new Session(LocalDate.of(2025, 11, 9), 30));

// Test listing
ArrayList<Session> sessions = manager.listSessions();
System.out.println("All sessions:");
for (Session s : sessions) {
    System.out.println(s);
}

// Test total
int total = manager.getTotalMinutes();
System.out.println("Total minutes: " + total);
```

Expected output:
```
All sessions:
Session{date=2025-11-11, duration=45}
Session{date=2025-11-10, duration=60}
Session{date=2025-11-09, duration=30}
Total minutes: 135
```

## Common Mistakes to Watch For

1. **Forgetting to initialize the ArrayList in the constructor** - You'll get a NullPointerException when you try to add
2. **Using array syntax instead of ArrayList methods** - `sessions[0]` won't work, use `sessions.get(0)`
3. **Forgetting to import java.util.ArrayList** - Compilation error
4. **Mixing up `.length` (arrays) and `.size()` (ArrayList)**
5. **Not initializing the accumulator to 0** in getTotalMinutes

## Reinforcing Previous Concepts

This module reuses:
- **Class structure** (Module 1, 2) - Creating a class with fields, constructor, methods
- **Encapsulation** (Module 2) - Private fields, public methods
- **Static typing** (Module 1, 2) - Every variable has a declared type
- **The Session class** (Module 2) - You'll work with Session objects throughout

## Next Steps

After implementing and testing `SessionManager`, we'll add file persistence in Module 4. This manager will be the foundation - we'll just add save/load methods to it.

## Design Philosophy: Why Java Does It This Way

Java's verbosity serves a purpose here:
- `ArrayList<Session>` explicitly declares intent - can only hold Sessions
- Separate manager class makes responsibilities clear
- Type system catches errors at compile time, not 3am in production
- When the codebase grows to 100,000 lines, explicit structure makes it navigable

Compare to JavaScript where the same collection might be:
```javascript
let sessions = [];  // Could be anything
```

The tradeoff: more upfront specification, but less debugging and more maintainability.
