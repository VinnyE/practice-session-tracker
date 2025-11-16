# Module 7: Polish & Error Handling

## Learning Objectives

By the end of this module, you'll understand:
- Input validation patterns and where validation should live in your architecture
- Defensive programming principles for production-quality code
- Exit codes and their role in CLI applications
- Robust file parsing that handles corrupted or malformed data
- The difference between "it works" and "it's bulletproof"

## The Production-Quality Mindset

Your app works great when used correctly. But production software must handle the **unhappy path** just as well as the happy path:

- What if the sessions.txt file gets corrupted by a text editor?
- What if someone tries to add a 10,000-minute session?
- What if someone creates a Session with a null date or negative duration?
- What if the file has blank lines, or lines with wrong number of fields?

**JavaScript comparison**: In Node.js, you might rely on try-catch around everything and hope for the best. Java encourages **fail-fast** validation at boundaries - catch problems as early as possible, where they're easiest to diagnose.

## Core Concepts

### 1. Validation at Boundaries (The "Constructor Guard" Pattern)

**The Problem**: Currently, you can create a `Session` with invalid data:
```java
Session broken = new Session(null, -500);  // This compiles and runs!
```

**The Principle**: Objects should validate their own invariants. A Session with a null date or negative duration is **never valid**, so the constructor should prevent it from being created.

**Why it matters**: This is called "defensive programming." If validation lives in the constructor, you can NEVER have an invalid Session object anywhere in your program. Every Session that exists is guaranteed to be valid.

**JavaScript comparison**: In JS, you might do `if (!date || duration < 0) throw new Error(...)`. Java has the same pattern, but you'll use `IllegalArgumentException` - a runtime exception designed for "this argument is not acceptable."

**Search terms**: "Java IllegalArgumentException", "defensive programming constructor validation"

### 2. Exit Codes - Speaking the Language of the Shell

**The Problem**: When your program encounters an error, it currently prints a message and returns normally. To the shell, this looks like success.

**The Principle**: CLI programs communicate success/failure through **exit codes**:
- Exit code 0 = success
- Exit code 1 (or any non-zero) = failure

This matters for shell scripts, CI/CD pipelines, and automation. If someone writes a script that runs your app, they need to know if it succeeded.

**API Pointers**:
- `System.exit(0)` - exit with success code
- `System.exit(1)` - exit with error code
- Normal return from main() = exit code 0 (success)

**When to use**:
- After printing an error message, call `System.exit(1)`
- Don't exit for successful operations - just return normally
- Don't exit from methods other than main (makes code hard to test)

**JavaScript comparison**: In Node.js, you'd use `process.exit(1)`. Same concept, different API.

### 3. Consistent Error Output Streams

**The Problem**: Your code mixes `System.out.println()` and `System.err.println()` for errors.

**The Principle**: UNIX convention (which Java follows):
- **stdout** (`System.out`) - normal program output, success messages
- **stderr** (`System.err`) - error messages, warnings

Why separate them? Users can redirect them independently:
```bash
java PracticeTracker list > results.txt 2> errors.txt
```

**Consistency rule**: ALL error messages should go to `System.err`, ALL normal output to `System.out`.

### 4. Robust File Parsing (The "Try Each Line" Pattern)

**The Problem**: Your `loadFromFile()` assumes every line is perfectly formatted. One bad line crashes the entire load.

**The Principle**: When parsing user data files, be tolerant but vigilant:
- Parse each line in its own try-catch block
- Skip invalid lines instead of crashing
- Log warnings to stderr for skipped lines (so user knows something's wrong)
- Continue loading valid lines

**Tradeoff consideration**: Should you:
- **Option A**: Skip bad lines silently (resilient but might hide bugs)
- **Option B**: Skip bad lines with warnings (resilient AND visible)
- **Option C**: Crash on first bad line (strict but fragile)

For a personal CLI tool, Option B is usually best. For critical financial data, Option C might be better. Understanding the tradeoff is what makes you a senior engineer.

**Pattern**:
```
for each line:
    try:
        parse line
        create object
        add to collection
    catch parsing exception:
        print warning to stderr with line number
        continue to next line
```

### 5. Upper Bounds Validation

**The Problem**: Nothing prevents someone from adding a 999,999-minute session.

**The Principle**: Define reasonable constraints based on your domain. A practice session can't be:
- Negative (you already check this)
- Zero (not useful, right?)
- More than 24 hours = 1440 minutes (unrealistic for a single session)

Where should these rules live? In the Session constructor - the boundary of your domain model.

## Your Challenge

Improve your application's error handling and validation:

### Part 1: Session Validation

Add validation to the `Session` constructor to ensure:
- Date cannot be null
- Duration must be positive (> 0, not just >= 0)
- Duration must be reasonable (≤ 1440 minutes)

**What to throw**: `IllegalArgumentException` with a descriptive message.

**Tip**: Check the IllegalArgumentException constructor - it takes a String message.

### Part 2: Robust File Loading

Improve `loadFromFile()` in SessionManager to handle corrupted data:
- Wrap the parsing of EACH line in a try-catch
- Catch `DateTimeParseException` (for bad dates) and `NumberFormatException` (for bad durations)
- Also catch `ArrayIndexOutOfBoundsException` (for lines without enough fields)
- When a line fails to parse:
  - Print a warning to stderr: "Warning: Skipping invalid line: [line content]"
  - Continue loading other lines
- Consider: what if a line is blank or whitespace-only?

**Search terms**: "Java DateTimeParseException", "Java multiple exception types catch", "Java String trim"

### Part 3: Exit Codes and Error Consistency

Update `PracticeTracker.java`:
- ALL error messages should use `System.err.println()` (not System.out)
- After printing an error message, call `System.exit(1)` to exit with failure code
- Normal operations should just return (implicit exit code 0)

**Places to review**:
- Invalid command
- Wrong number of arguments
- Invalid duration argument
- Negative duration
- NumberFormatException handling

### Part 4: Better Error Messages

Improve error messages to be **actionable** - help the user fix the problem:

Instead of:
```
"A valid duration argument is needed."
```

Consider:
```
"Error: Duration argument required. Usage: java PracticeTracker add <minutes>"
```

Make each error message:
- State what went wrong
- Suggest how to fix it (when obvious)
- Use consistent format (start with "Error:" for errors)

### Part 5: Handle IllegalArgumentException from Session

Now that Session constructor can throw IllegalArgumentException, your `add` command needs to catch it:
- Catch IllegalArgumentException when creating the Session
- Print the exception's message to stderr (it contains your validation message)
- Exit with code 1

## Testing Your Error Handling

After implementing these improvements, test ALL the error cases:

1. **Invalid Session creation** (you'll need to test this by temporarily adding code to main):
   ```java
   // Should throw IllegalArgumentException
   Session bad = new Session(null, 50);
   Session bad2 = new Session(LocalDate.now(), -10);
   Session bad3 = new Session(LocalDate.now(), 2000); // Too long
   ```

2. **Corrupt sessions.txt file**:
   Create a sessions.txt with various bad lines:
   ```
   2025-11-16,45
   2025-11-15,60
   this-is-garbage
   2025-11-14
   2025-11-13,not-a-number
   2025-BAD-DATE,30

   2025-11-12,90
   ,,,,
   ```

   Then run `java PracticeTracker list` - it should:
   - Print warnings for bad lines to stderr
   - Successfully load and display the valid sessions
   - Not crash

3. **Invalid commands**:
   ```bash
   java PracticeTracker
   java PracticeTracker add
   java PracticeTracker add hello
   java PracticeTracker add -50
   java PracticeTracker add 5000
   java PracticeTracker invalid
   java PracticeTracker add 30 extra
   ```

4. **Exit codes** (test in terminal):
   ```bash
   java PracticeTracker add 45
   echo $?    # Should print 0 (success)

   java PracticeTracker invalid
   echo $?    # Should print 1 (failure)
   ```

## Why This Matters

This module is about the difference between:
- **Code that works** (Modules 1-6)
- **Code you'd trust in production** (Module 7)

The core functionality hasn't changed. But now your app:
- Prevents invalid state from ever existing
- Handles real-world messiness (corrupted files, bad input)
- Communicates properly with the shell environment
- Helps users fix their mistakes with clear messages

This is the **polish** that separates hobby projects from professional software.

## Common Mistakes to Watch For

- **Over-catching exceptions**: Don't wrap everything in try-catch. Only catch exceptions you can meaningfully handle.
- **Swallowing exceptions silently**: If you skip a bad line, log it. Silent failures are debugging nightmares.
- **Validating in the wrong place**: Validation should be as early as possible, at the boundary where data enters your system.
- **Generic error messages**: "Invalid input" doesn't help anyone. Be specific.
- **Forgetting to exit after errors**: Printing an error but continuing execution is confusing.

## Reflection Questions (After Implementation)

- Where did you place validation logic, and why?
- What exceptions did you choose to catch vs propagate, and why?
- How did you decide what constitutes "reasonable" bounds for duration?
- What tradeoffs did you make in file parsing (strict vs tolerant)?

## What You've Built

After this module, you have a complete, production-quality CLI application that:
- Models data with immutable, validated objects
- Persists to disk with robust error handling
- Provides a clean user interface with helpful error messages
- Follows UNIX conventions for exit codes and output streams
- Handles real-world messiness gracefully

More importantly, you understand:
- The architecture of a well-structured Java application
- How Java's type system and exception model prevent bugs
- The difference between JavaScript's flexibility and Java's strictness
- When to validate, what to validate, and how to handle failures

You're ready to build more complex Java applications.
