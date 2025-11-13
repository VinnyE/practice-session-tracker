# Module 4: File Persistence - Writing

**Estimated Time**: 30-45 minutes
**Prerequisites**: Modules 1-3 (classes, ArrayList, encapsulation, iteration)

## Learning Objectives

By the end of this module, you'll understand:
- How Java handles file I/O and why it differs from JavaScript
- What checked exceptions are and why Java forces you to handle them
- How `try-with-resources` prevents resource leaks automatically
- Design decisions around data serialization and persistence

## The Problem We're Solving

Right now, your SessionManager stores sessions in memory using an ArrayList. This works fine while the app is running, but the moment it exits, all your data disappears. Every time you run `java PracticeTracker list`, you get an empty list.

**Your mission**: Make sessions persist to a file so they survive across program runs.

## Conceptual Foundation

### JavaScript vs Java: File I/O Philosophy

**In JavaScript (Node.js):**
```javascript
// Async by default, uses callbacks/promises
const fs = require('fs');
fs.writeFileSync('data.txt', 'some data');  // or
await fs.promises.writeFile('data.txt', 'some data');
```

You can just... write to a file. If something goes wrong, you might get an error at runtime, but the language doesn't force you to think about it.

**In Java:**
```java
// Sync by default, forces you to handle errors
Files.write(path, data);  // <- This won't even compile!
```

Java won't let you compile this code unless you explicitly acknowledge: "Yes, I know this might fail, and here's what I'll do if it does."

**Why the difference?**

Java's philosophy: **Errors that can happen WILL happen in production.** File systems are inherently unreliable:
- Disk full
- Permission denied
- File locked by another process
- Network drive disconnected
- Hardware failure

By forcing you to handle these scenarios at compile-time, Java prevents you from shipping code that crashes mysteriously in production.

### Checked Exceptions: The Contract

Java distinguishes between:

1. **Unchecked exceptions** (RuntimeException): Bugs in your logic (NullPointerException, ArrayIndexOutOfBoundsException). You *could* catch these, but usually they indicate code that needs fixing.

2. **Checked exceptions** (Exception): External failures beyond your control (IOException, SQLException). You MUST handle these or declare that you throw them.

When a method signature says `throws IOException`, it's telling you: "I might fail in a way that's not your fault. You need a plan."

### Try-With-Resources: Automatic Cleanup

**The Problem:**
Files, network connections, database connections - these are "resources" that need to be closed after use. If you forget, you get:
- File handles leaking (eventually can't open new files)
- Memory leaks (buffers not released)
- Lock contention (files locked that should be free)

**JavaScript approach:**
```javascript
// Manual cleanup, easy to forget
const file = await open('data.txt');
try {
  await file.write('data');
} finally {
  await file.close();  // Must remember this!
}
```

**Java approach:**
```java
// Automatic cleanup, compiler enforces it
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
  writer.write("data");
}  // <- writer.close() called automatically, even if exception occurs
```

The `try-with-resources` syntax guarantees cleanup happens. The resource is closed automatically at the end of the try block, even if an exception is thrown.

## Design Decisions for This Module

### 1. What Format Should We Use?

You need to serialize sessions to text. Options:

**Option A: JSON**
```
[{"date":"2025-11-10","duration":45},{"date":"2025-11-11","duration":30}]
```
- ✅ Pro: Standard format, parseable by anything
- ❌ Con: Requires a JSON library, overkill for simple data

**Option B: CSV**
```
2025-11-10,45
2025-11-11,30
```
- ✅ Pro: Simple, human-readable, one line per session
- ✅ Pro: Easy to parse (split on comma)
- ✅ Pro: Matches our app's simplicity
- ❌ Con: Breaks if your data contains commas (not an issue here)

**Decision for V1: CSV-style, one session per line.**

### 2. When Should We Write?

**Option A: Write after every add**
- ✅ Pro: Data is immediately persisted, no data loss
- ❌ Con: More disk I/O, slower for batch operations

**Option B: Write only on exit**
- ✅ Pro: Fast, minimal I/O
- ❌ Con: Data lost if program crashes

**Decision for V1: Write after every add.** The performance difference is negligible for this use case, and immediate persistence is more reliable for a learning app.

### 3. How Should We Handle Errors?

**Option A: Crash on any I/O error**
```java
// Let exception propagate up and crash the app
```

**Option B: Catch and log, continue running**
```java
try {
  saveToFile();
} catch (IOException e) {
  System.err.println("Warning: Could not save to file");
  // Continue running with in-memory data
}
```

**Option C: Catch and re-throw as unchecked**
```java
try {
  saveToFile();
} catch (IOException e) {
  throw new RuntimeException("Failed to save sessions", e);
}
```

**Decision for Module 4: Let exceptions propagate** (simplest approach, errors are visible). In Module 7 (Polish & Error Handling), you'll make this more robust.

## Your Task

Modify `SessionManager.java` to save sessions to a file called `sessions.txt` in the current directory.

### Requirements

1. **After adding a session**, all sessions should be written to `sessions.txt`

2. **File format**: One session per line, date and duration separated by comma
   ```
   2025-11-10,45
   2025-11-11,30
   2025-11-12,60
   ```

3. **File location**: `sessions.txt` in the current working directory

4. **Error handling**: Use appropriate exception handling patterns (see API hints below)

### Constraints

- Don't change the public API of SessionManager (addSession, listSessions, getTotalMinutes should work the same)
- Don't modify Session.java or PracticeTracker.java yet
- Keep it simple: write entire file each time (we'll optimize later if needed)

## API Hints

### Working with Paths

```java
import java.nio.file.Path;
import java.nio.file.Paths;

Path filePath = Paths.get("sessions.txt");
```

The `Path` interface represents a file system path. It's like a string, but type-safe and platform-independent.

### Writing to Files

The `Files` utility class provides static methods for file operations:

```java
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

// Write a list of strings to a file
List<String> lines = new ArrayList<>();
lines.add("line 1");
lines.add("line 2");

Files.write(filePath, lines);  // Creates or overwrites file
```

**Key detail**: `Files.write()` expects a `List<String>` where each element becomes one line in the file.

### Try-With-Resources Pattern

While `Files.write()` handles resources internally, you should know the pattern:

```java
try (ResourceType resource = new ResourceType()) {
  // Use resource
}  // Automatically closed here
```

For this module, you may not need explicit try-with-resources since `Files.write()` handles it. But you DO need to handle the checked exception.

### Exception Handling Options

**Option 1: Declare that your method throws IOException**
```java
private void saveToFile() throws IOException {
  // File operations here
  // Let exception propagate to caller
}
```

If `addSession` calls `saveToFile`, then `addSession` must also declare `throws IOException`, and so on up the chain.

**Option 2: Catch and handle**
```java
private void saveToFile() {
  try {
    // File operations here
  } catch (IOException e) {
    // Handle error (print, log, re-throw, etc.)
  }
}
```

Choose based on: should the caller know about I/O failures, or should SessionManager hide them?

### String Formatting

To convert a session to CSV format:

```java
String line = session.getDate() + "," + session.getDuration();
// Result: "2025-11-10,45"
```

LocalDate.toString() returns ISO format (YYYY-MM-DD) which is perfect for our needs.

## Testing Your Implementation

Right now, PracticeTracker.java has three hardcoded test sessions (lines 17-19). This is perfect for testing file persistence! The actual command routing happens in Module 6.

### Test 1: File Creation
```bash
javac *.java
java PracticeTracker add 45
```

(The "add 45" arguments are just printed for now. The real test is the hardcoded sessions.)

**Check**: Look at `sessions.txt` - you should see three sessions:
```
2025-11-13,45
2025-11-10,60
2025-11-09,30
```

(First date will be today's date)

### Test 2: File Overwriting
```bash
java PracticeTracker
```

(Or with any arguments - they're ignored for now)

**Check**: Run the program again. The file should be overwritten with the same three sessions.

**Why overwriting?** Because SessionManager starts with an empty ArrayList each run, adds the three hardcoded sessions, then saves. In Module 5, when we add reading, the behavior will change - it'll load existing sessions first, then add to them.

### Test 3: Verify Format
Open `sessions.txt` in a text editor. Each line should be: `YYYY-MM-DD,duration`

No extra spaces, no brackets, no quotes. Just clean CSV.

## Common Pitfalls

### Forgetting Import Statements
You'll need:
```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
```

### Not Handling the Checked Exception
This won't compile:
```java
private void saveToFile() {
  Files.write(path, lines);  // ERROR: unhandled exception
}
```

You must either catch IOException or declare `throws IOException`.

### Resource Leaks
If you use `BufferedWriter` or similar manually instead of `Files.write()`, make sure to use try-with-resources:
```java
// ❌ Bad: resource leak
BufferedWriter writer = Files.newBufferedWriter(path);
writer.write("data");
// writer never closed!

// ✅ Good: automatic cleanup
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
  writer.write("data");
}  // closed automatically
```

### Path vs String Confusion
`Files.write()` expects a `Path`, not a `String`:
```java
// ❌ Wrong
Files.write("sessions.txt", lines);

// ✅ Correct
Path path = Paths.get("sessions.txt");
Files.write(path, lines);
```

## Search Terms & Further Reading

If you get stuck or want to understand deeper:

- "Java checked exceptions vs unchecked exceptions"
- "Java try-with-resources statement"
- "Java nio.file.Files API documentation"
- "Java Path interface"
- "When to catch vs throw exceptions in Java"

## What You're Learning (The Big Picture)

This module is about **defensive programming**. In production systems:
- Disks fill up
- Networks fail
- Permissions change
- Hardware dies

JavaScript lets you ignore this until it bites you. Java forces you to think about it upfront.

**The verbosity serves a purpose**: Your method signature declares `throws IOException`, and now every caller knows: "This operation might fail." That's not boilerplate - that's a compile-time contract that prevents 3am production incidents.

By the end of this module, you'll have internalized:
- File I/O patterns in Java
- How checked exceptions work
- Why try-with-resources exists
- The difference between "it works on my machine" and "it works in production"

And most importantly: why Java makes you write more code upfront to save debugging time later.

---

## Ready to Code?

You have everything you need:
- The concept (persistence via file I/O)
- The APIs (Files, Path, Paths)
- The format (CSV-style, one line per session)
- The strategy (write after each add)

Now it's your turn to implement it. Take your time, read the docs, and remember: productive struggle is where learning happens.

When you're done (or if you get stuck after 20-30 minutes), share your code and we'll review it together!
