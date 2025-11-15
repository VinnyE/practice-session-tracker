# Module 6: Commands Implementation

## Learning Objectives

By the end of this module, you will:
- Understand switch statements as a pattern for command routing
- Parse and validate command-line arguments
- Format output for end users (vs debug output)
- Convert string input to appropriate types with error handling
- Integrate all components into a working CLI application

## Why This Matters

This is where your app becomes **usable**. Up until now, you've built the engine - data models, business logic, persistence. Now you're building the **interface** - the layer users actually interact with.

This is a critical skill: separating internal representations (toString() for debugging) from external presentations (formatted output for users). Real applications need both.

## Concept 1: Command Routing with Switch Statements

### The Problem

You have a string from the user ("add", "list", "total") and need to execute different code based on which command it is. You could write:

```java
if (command.equals("add")) {
    // add logic
} else if (command.equals("list")) {
    // list logic
} else if (command.equals("total")) {
    // total logic
} else {
    // error
}
```

This works, but for multiple commands, a **switch statement** is more idiomatic and readable.

### Switch Statements in Java

A switch statement takes a value and "routes" to different cases based on that value:

```java
String fruit = "apple";

switch (fruit) {
    case "apple":
        System.out.println("It's an apple!");
        break;
    case "banana":
        System.out.println("It's a banana!");
        break;
    default:
        System.out.println("Unknown fruit");
        break;
}
```

**Key points:**
- Each `case` is a potential match
- `break` prevents "falling through" to the next case
- `default` is the "else" - runs if no case matches
- Works with strings (since Java 7), integers, enums, etc.

### JavaScript Comparison

JS has switch statements too, with nearly identical syntax:

```javascript
switch (fruit) {
    case "apple":
        console.log("It's an apple!");
        break;
    case "banana":
        console.log("It's a banana!");
        break;
    default:
        console.log("Unknown fruit");
}
```

Java's switch works the same way. The difference is that Java (14+) also has **enhanced switch expressions**, but the traditional switch statement works perfectly for command routing.

### String Comparison in Java

**CRITICAL**: In Java, you MUST use `.equals()` to compare strings, not `==`.

```java
// ❌ Wrong - compares memory addresses, not content
if (command == "add") { ... }

// ✅ Correct - compares actual string content
if (command.equals("add")) { ... }
```

Switch statements handle this correctly internally - you just write `case "add":` and it works.

## Concept 2: Parsing User Input

### Type Conversion

Your SessionManager expects an `int` for duration, but `args[1]` is a `String`. You need to convert it.

Java provides static methods for this:
- `Integer.parseInt(String s)` - converts String to int
- `Double.parseDouble(String s)` - converts String to double
- `LocalDate.parse(String s)` - converts String to LocalDate (you've used this!)

**These throw exceptions if the input is invalid.** For example:

```java
int duration = Integer.parseInt("abc"); // ❌ Throws NumberFormatException
int duration = Integer.parseInt("45");  // ✅ Returns 45
```

### JavaScript Comparison

In JavaScript, you might use:
- `parseInt("45")` - similar, but more forgiving (returns NaN instead of throwing)
- `Number("45")` - coerces to number
- `+"45"` - unary plus coercion

Java is **stricter** - it throws an exception rather than returning a special value. This forces you to handle the error case explicitly (Module 7 will focus on this).

## Concept 3: User-Facing Output vs Debug Output

### The Difference

You already have `Session.toString()` which returns:
```
Session{date=2025-11-10, duration=45}
```

This is perfect for **debugging** - you can see the raw data structure.

But **users** don't want to see "Session{...}". They want to see:
```
2025-11-10  45 minutes
```

Or even more formatted:
```
Date: 2025-11-10 | Duration: 45 minutes
```

### Design Decision: toString() vs User Formatting

**toString() should remain debug-friendly.** It's used when you print objects for development, logging, or debugging.

**User output should be formatted separately** in the CLI layer (PracticeTracker), not in the data model (Session).

This is **separation of concerns**:
- Session knows how to represent itself for debugging
- PracticeTracker knows how to present data to users

### String Formatting Options

You have several options for formatting output:

1. **String concatenation** (what you've been using):
```java
System.out.println(session.getDate() + "  " + session.getDuration() + " minutes");
```

2. **String.format()** (similar to printf):
```java
System.out.println(String.format("%s  %d minutes", session.getDate(), session.getDuration()));
```

3. **printf directly** (shorter):
```java
System.out.printf("%s  %d minutes%n", session.getDate(), session.getDuration());
```

For this module, simple concatenation is fine. You can explore fancier formatting later.

## Concept 4: Argument Validation

### The CLI Contract

Your app expects:
- `add <duration>` - exactly 2 arguments
- `list` - exactly 1 argument
- `total` - exactly 1 argument

What should happen if the user provides:
- Wrong number of arguments?
- Invalid duration (not a number)?
- Unknown command?

**Good software validates input and provides helpful error messages.**

Example validation logic:
- Check `args.length` to ensure correct number of arguments
- Use try-catch around `Integer.parseInt()` to catch invalid numbers
- Use switch `default` case to catch unknown commands

## Your Challenge

Transform PracticeTracker from test code into a working CLI application.

### Requirements

**Command: `add <duration>`**
- Takes duration in minutes as an integer
- Creates a session with today's date and the provided duration
- Saves it via SessionManager.addSession()
- Prints confirmation message (e.g., "Added session: 45 minutes")
- Handle IOException (already declared in main signature)

**Command: `list`**
- Retrieves all sessions via SessionManager.listSessions()
- Prints each session in user-friendly format (not toString())
- Should show date and duration clearly
- If no sessions, print an appropriate message

**Command: `total`**
- Gets total minutes via SessionManager.getTotalMinutes()
- Prints the total in a user-friendly way (e.g., "Total practice time: 135 minutes")

**Invalid commands:**
- Unknown command should print a helpful error message
- Wrong number of arguments should print usage instructions

### Current State

Your PracticeTracker currently:
- Prints arguments to verify it received them (lines 7-14)
- Creates a SessionManager (line 16)
- Has commented-out test sessions (lines 17-19)
- Tests listSessions() and getTotalMinutes() with hardcoded output (lines 21-29)

You'll replace this test code with actual command routing.

### API References

**You already know:**
- `args.length` - get number of arguments
- `args[0]`, `args[1]` - access specific arguments
- `manager.addSession(session)` - add a session
- `manager.listSessions()` - get all sessions
- `manager.getTotalMinutes()` - get total
- `LocalDate.now()` - today's date
- `new Session(date, duration)` - create session

**New APIs to use:**
- `Integer.parseInt(String s)` - convert string to int
- Switch statement syntax (see examples above)
- String `.equals()` method (though switch handles it for you)

### Testing Your Implementation

Since you're replacing the test code in main(), here's how to test each command:

**Test add command:**
```bash
java PracticeTracker add 45
```

Expected: Adds a session with today's date and 45 minutes, prints confirmation.

Check sessions.txt - should have a new line with today's date and 45.

**Test list command:**
```bash
java PracticeTracker list
```

Expected: Displays all sessions from sessions.txt in a readable format.

**Test total command:**
```bash
java PracticeTracker total
```

Expected: Displays total minutes across all sessions.

**Test error cases:**
```bash
java PracticeTracker
java PracticeTracker add
java PracticeTracker add abc
java PracticeTracker unknown
```

Expected: Appropriate error messages for each case.

### Common Mistakes to Watch For

1. **Using `==` instead of `.equals()` for strings** (though switch handles this)

2. **Forgetting to parse duration from String to int** - args[1] is a String, not an int

3. **Not checking args.length before accessing args[1]** - IndexOutOfBoundsException if user doesn't provide enough arguments

4. **Forgetting `break` in switch cases** - causes fall-through to next case

5. **Not handling the IOException from addSession()** - already declared in main signature with `throws IOException`, so this is handled

6. **Printing toString() to users** - remember to format output separately

## Design Considerations

### Why Switch vs If-Else Chain?

- **Readability**: Switch makes it clear you're routing based on one value
- **Intent**: Signals "these are discrete options" vs "complex conditional logic"
- **Extensibility**: Easy to add more commands later

### Why Parse in PracticeTracker, Not SessionManager?

- **Separation of concerns**: SessionManager works with proper types (int, LocalDate)
- **Reusability**: SessionManager could be used by a GUI, API, or other interface that doesn't have string arguments
- **Single responsibility**: PracticeTracker handles CLI concerns (parsing, formatting), SessionManager handles business logic

### Where Should Validation Live?

For this module, basic validation (args.length checks, parseInt try-catch) can live in PracticeTracker. This is CLI-specific validation.

SessionManager could also add validation (e.g., "duration must be positive"), but we'll keep it simple for now.

## What Success Looks Like

After implementing this module, you should be able to:

```bash
$ java PracticeTracker add 45
Added session: 45 minutes

$ java PracticeTracker add 30
Added session: 30 minutes

$ java PracticeTracker list
2025-11-14  45 minutes
2025-11-14  30 minutes

$ java PracticeTracker total
Total practice time: 75 minutes
```

Clean, user-friendly output. No debug information like "Session{...}". Just the information users need.

## Stretch Goal (Optional)

If you finish the basic implementation and want more challenge:

**Better total formatting**: Convert total minutes to hours and minutes.
- 75 minutes → "1 hour 15 minutes"
- 45 minutes → "45 minutes"
- 120 minutes → "2 hours"

This requires some arithmetic and conditional formatting logic. Totally optional, but good practice with integer division and modulo operators.

## Next Module Preview

Module 7 will focus on **polish and error handling**:
- Proper try-catch for NumberFormatException
- Validation (duration must be positive, not zero)
- Better error messages with usage instructions
- Handling edge cases (empty sessions list, file corruption, etc.)

For now, basic validation is enough. Focus on getting the core commands working.
