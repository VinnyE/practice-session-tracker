# Module 2: Session Data Model

**Time Estimate**: 30-45 minutes
**Difficulty**: Beginner-Intermediate

## Goal
Create a `Session` class that represents one practice session with a date and duration.

## Learning Objectives
- Create a class to model data (not just contain logic)
- Use constructors to initialize objects
- Understand private fields + public getters (encapsulation)
- Work with `LocalDate` for dates
- Override `toString()` for debugging

## Background: Data Modeling in Java vs JavaScript

### The JavaScript Approach

In JavaScript, you might represent a session as a plain object:
```javascript
const session = {
  date: '2025-11-10',
  duration: 45
};
```

Or with a class:
```javascript
class Session {
  constructor(date, duration) {
    this.date = date;
    this.duration = duration;
  }
}
```

### The Java Approach

Java encourages you to:
1. **Define the shape explicitly** - What fields exist, what types are they?
2. **Control access** - Who can read/write these fields?
3. **Use proper types** - `LocalDate` for dates, not strings

This seems verbose, but it prevents bugs. You can't accidentally create a session with `duraton: 45` (typo) - the compiler won't let you.

## Key Concept: Encapsulation

### Why Private Fields?

In Java, you typically make fields `private` and provide `public` methods to read them (getters).

**Why not just make fields public?**

1. **Control**: You control how data is accessed (e.g., could add validation)
2. **Immutability**: No setter method = no one can change the value after creation
3. **Flexibility**: Can change internal representation without breaking code that uses the class

In JavaScript, you might use plain objects and trust yourself. Java says "make it impossible to misuse."

**Your Challenge**: How will you allow external code to READ your fields without allowing them to WRITE to them?

**Naming Convention**: Java uses "Bean" naming conventions - if you have a field, the method to get it follows a pattern. Research: "Java getter naming convention"

## Key Concept: Constructors

### The Initialization Contract

A constructor is a special method that runs when you create an object with `new`.

**What makes constructors special:**
- No return type (not even `void`)
- Same name as the class (must match exactly)
- Called automatically when you use `new ClassName(...)`
- The `this` keyword refers to the object being created (like JS)

**Your Challenge**: How will you ensure that both `date` and `duration` are always set when creating a Session? A Session without these values should be impossible to create.

**Design Question**: If your constructor parameter is named `date` and your field is also named `date`, how do you distinguish between them?

**Search terms if needed**: "Java constructor syntax", "Java this keyword"

## Key Concept: LocalDate

### Java's Date/Time API

Java has a modern date/time API in `java.time`:

```java
import java.time.LocalDate;

LocalDate today = LocalDate.now();
LocalDate specific = LocalDate.of(2025, 11, 10);
```

**Why LocalDate?**
- Immutable (can't be changed after creation)
- Type-safe (can't accidentally treat it as a string)
- No timezone issues (just a date, not a date-time)

Compare to JavaScript's `Date` object or date strings - Java's approach is more explicit and less error-prone.

## Key Concept: toString()

### Making Objects Printable

When you print an object with `System.out.println(object)`, Java calls the object's `toString()` method.

All classes automatically inherit a default `toString()` from `Object` (Java's universal base class), but it just returns something like `Session@1a2b3c4d` (class name + memory address). Not helpful!

**Your Challenge**: Override the `toString()` method to return something useful when someone prints a Session.

**What you need to know:**
- Method signature: `public String toString()` - takes no parameters, returns a String
- Use `@Override` annotation (tells compiler you're overriding, catches typos like `tostring`)
- You can use `+` to concatenate strings, and Java will convert your fields to strings automatically
- Good format: `"Session{date=2025-11-10, duration=45}"`

**Search terms if needed**: "Java override toString", "Java @Override annotation"

## Your Task

**Create a file called `Session.java`** that represents a practice session.

This task has two phases:
1. **Phase 1**: Build the core class structure (fields, constructor, getters)
2. **Phase 2**: Implement a key design decision (toString method)

### Phase 1: Core Class Structure

### Requirements

**Data:**
- A session has a date (use `LocalDate` from `java.time`)
- A session has a duration in minutes (use `int`)

**Design Constraints:**
- Fields should not be directly accessible from outside the class
- It must be impossible to create a Session without providing both date and duration
- Once created, a Session's data cannot be modified (immutable)
- External code must be able to read the date and duration values

**Technical:**
- Filename must match class name exactly
- Import what you need from `java.time`
- Follow Java Bean naming conventions for getters

**What to implement:**
- Private fields for date and duration
- A constructor that accepts both date and duration parameters
- Public getter methods for both fields

**Stop here** - Don't implement toString() yet. You'll do that in Phase 2 as a "Learn by Doing" exercise.

### Testing Phase 1

Add this to your `PracticeTracker.java` main method temporarily:

```java
Session session = new Session(LocalDate.now(), 45);
System.out.println("Date: " + session.getDate());
System.out.println("Duration: " + session.getDuration());
```

Run it: `java PracticeTracker.java Session.java`

You should see output like:
```
Date: 2025-11-10
Duration: 45
```

Once this works, you're ready for Phase 2!

---

## Phase 2: Learn by Doing

After completing Phase 1, you'll implement the toString() method as a design exercise. This will be your first "Learn by Doing" contribution - a small but meaningful piece of code that involves design decisions.

**When you're ready for Phase 2**, I'll add a TODO(human) marker in your Session.java file and explain the design considerations for toString(). This keeps you actively engaged in design decisions rather than just implementing specifications.

### Expected Final Output

After completing both phases, this test code:

```java
Session session = new Session(LocalDate.now(), 45);
System.out.println(session);
System.out.println("Date: " + session.getDate());
System.out.println("Duration: " + session.getDuration());
```

Should produce something like:
```
Session{date=2025-11-10, duration=45}
Date: 2025-11-10
Duration: 45
```

## Success Criteria

**Phase 1:**
- [ ] File is named `Session.java` (matches class name)
- [ ] Two private fields: `LocalDate date` and `int duration`
- [ ] Constructor takes both parameters and sets fields
- [ ] Public getters return the field values
- [ ] Can create a Session and access its fields from PracticeTracker

**Phase 2:**
- [ ] User implemented `toString()` method with their own design decisions
- [ ] `toString()` returns a readable string representation
- [ ] Can print a Session object and see meaningful output

## Common Mistakes to Watch For

**Phase 1:**
1. **Constructor has return type** - Constructors never have return types (not even `void`)
2. **Fields are public** - Should be private with public getter methods
3. **Forgetting `this`** - When parameter names match field names, you need a way to distinguish
4. **Wrong getter names** - Java Bean convention has a specific pattern (look it up!)
5. **Forgetting `import`** - LocalDate is in `java.time` package

**Phase 2:**
6. **Forgetting `@Override`** - Compiler will catch mistakes if you use this annotation
7. **Not returning a String** - toString() must return a String, not print directly
8. **Forgetting to use fields** - Your toString() should reference `this.date` and `this.duration`

## Getting Unstuck

**Phase 1 - Core Structure:**
- **Constructors**: Search "Java constructor syntax" or look at how other classes initialize
- **Getters**: Search "Java getter method" or "Java Bean naming convention"
- **this keyword**: Think about how you'd distinguish between two variables with the same name
- **Compilation errors**: Read them carefully - Java's compiler is helpful and tells you exactly what's wrong

**Phase 2 - toString():**
- **Method signature**: Search "override toString Java" for examples
- **String concatenation**: You can use `+` to build strings in Java
- **Design decisions**: Think about what information would be helpful when debugging
- **Format ideas**: Look at how other languages format object output (Python's `__repr__`, JS console output)

## Why This Matters

This is **object-oriented design** at its core. You're defining what a Session IS:
- What data does it hold?
- How is it created?
- How is it accessed?

In JavaScript, you might use plain objects and trust yourself. Java makes you explicit about the contract - what's possible and what's not.

This verbosity prevents bugs. No one can create a Session without a date. No one can accidentally change the date after creation (if you don't provide a setter). The compiler enforces your design.

---

**Give it a shot! Create Session.java and test it.**
