# Module 5: File Persistence - Reading

**Estimated Time**: 30-45 minutes

## Learning Objectives

By the end of this module, you will understand:
- How to read files line-by-line in Java using the NIO API
- How to parse structured text (CSV) back into objects
- How to convert strings to other types (`String` → `int`, `String` → `LocalDate`)
- How to handle the case where a file might not exist yet
- The complete persistence cycle: load on startup, save on changes

## The Problem We're Solving

Right now, `SessionManager` writes sessions to `sessions.txt` every time you add one. But when you create a new `SessionManager`, it starts with an empty ArrayList. The data exists in the file, but we're not loading it!

In JavaScript, you might do something like:
```javascript
const sessions = fs.existsSync('sessions.txt')
  ? fs.readFileSync('sessions.txt', 'utf-8')
      .split('\n')
      .map(line => parseLine(line))
  : [];
```

In Java, the approach is similar in concept but different in execution:
- File operations throw checked exceptions (must handle)
- Type conversions are explicit (no implicit coercion)
- We use the NIO File APIs we learned in Module 4

## The Serialization Round Trip

**Serialization** = Converting objects → text (Module 4)
**Deserialization** = Converting text → objects (Module 5)

Module 4: `Session{date=2025-11-10, duration=45}` → `"2025-11-10,45"`
Module 5: `"2025-11-10,45"` → `Session{date=2025-11-10, duration=45}`

This is called a "round trip" - data goes from memory → disk → memory and arrives intact.

## Design Decision: Where to Load?

You need to decide: when should sessions be loaded from the file?

**Option A**: Load in the constructor
- `new SessionManager()` automatically loads existing sessions
- Pro: Automatic, can't forget to do it
- Pro: SessionManager is always in a valid state
- Con: Constructor does I/O (some consider this a code smell)

**Option B**: Explicit load method
- Call `manager.load()` after creating the manager
- Pro: Constructor stays simple
- Pro: More control over when loading happens
- Con: Easy to forget to call it

For this learning project, **Option A** makes more sense. The manager should "just work" when you create it.

## Key Java APIs You'll Need

### Reading Files

`Files.readAllLines(Path)` - Returns `List<String>`, one element per line
```java
Path path = Paths.get("data.txt");
List<String> lines = Files.readAllLines(path); // throws IOException
```

Alternative: `Files.lines(Path)` returns a Stream<String>, but we haven't covered streams yet. Stick with `readAllLines()` for now.

### Checking if a File Exists

`Files.exists(Path)` - Returns boolean
```java
Path path = Paths.get("data.txt");
if (Files.exists(path)) {
    // file exists, safe to read
}
```

### Parsing Strings

`String.split(String regex)` - Splits a string by delimiter, returns `String[]`
```java
String line = "2025-11-10,45";
String[] parts = line.split(","); // ["2025-11-10", "45"]
```

**Important**: The array has a fixed length. For CSV with 2 fields, `parts.length` should be 2.

### Type Conversions

**String → int**:
```java
String numStr = "45";
int num = Integer.parseInt(numStr); // throws NumberFormatException if invalid
```

**String → LocalDate**:
```java
String dateStr = "2025-11-10";
LocalDate date = LocalDate.parse(dateStr); // ISO-8601 format (YYYY-MM-DD)
```

Both of these throw exceptions if the format is wrong. For now, assume the file format is always correct. We'll handle malformed data in Module 7.

## JavaScript vs Java: File Reading

**JavaScript** (Node.js):
```javascript
if (fs.existsSync('sessions.txt')) {
  const content = fs.readFileSync('sessions.txt', 'utf-8');
  const lines = content.split('\n');
  for (const line of lines) {
    const [dateStr, durationStr] = line.split(',');
    const duration = parseInt(durationStr); // implicit coercion if needed
    const date = new Date(dateStr); // flexible parsing
  }
}
```

**Java**:
- Must use `Files.exists()` explicitly
- `Files.readAllLines()` returns `List<String>`, already split by newlines
- Must explicitly convert `String → int` with `Integer.parseInt()`
- Must explicitly convert `String → LocalDate` with `LocalDate.parse()`
- All file operations declare `throws IOException`

**Key Difference**: Java's explicit type conversions prevent subtle bugs. In JS, `parseInt("45abc")` returns `45` (!!). In Java, `Integer.parseInt("45abc")` throws an exception. No silent failures.

## Your Challenge

Modify `SessionManager` to load existing sessions from `sessions.txt` when constructed.

**Requirements**:
1. Load sessions in the constructor (after initializing the ArrayList)
2. Check if the file exists before trying to read it
3. If the file exists, read all lines
4. Parse each line (format: "YYYY-MM-DD,duration") back into a `Session` object
5. Add each parsed session to the `sessions` ArrayList
6. If the file doesn't exist, that's fine - start with an empty list

**Edge cases to consider**:
- File doesn't exist yet (first run)
- File exists but is empty (no lines)
- File exists with data (normal case)

**Method signature considerations**:
- The constructor will need to handle `IOException` (either with try-catch or `throws`)
- If you use `throws IOException` in the constructor, callers must handle it
- You might want a private helper method like `loadFromFile()` for separation of concerns (similar to `saveToFile()`)

**Hints**:
- You already have `Paths`, `Path`, and `Files` imported from Module 4
- You'll need to import `List` (already imported) for `Files.readAllLines()`
- `LocalDate.parse()` expects ISO-8601 format (YYYY-MM-DD), which matches what `saveToFile()` writes
- Enhanced for-loops work great for iterating through `List<String>`

**Search terms** if you get stuck:
- "Java Files.readAllLines example"
- "Java String split method"
- "Java Integer.parseInt"
- "Java LocalDate.parse"

## Testing Your Implementation

Since PracticeTracker currently creates hardcoded test sessions, here's how to test:

1. **Compile**: `javac *.java`

2. **Run to create initial data**: `java PracticeTracker`
   - This creates/overwrites `sessions.txt` with 3 hardcoded sessions

3. **Verify the file**: `cat sessions.txt`
   - You should see 3 lines in CSV format

4. **Edit PracticeTracker temporarily**: Comment out or delete the 3 `addSession()` calls so the manager starts empty

5. **Run again**: `java PracticeTracker`
   - The sessions should still print (loaded from file, not from hardcoded calls)

6. **Verify totals match**: The total minutes should be the same before and after

This proves the round trip works: write → read → write produces the same data.

## Common Mistakes to Watch For

1. **Forgetting to check if file exists**: Calling `Files.readAllLines()` on a non-existent file throws `NoSuchFileException`. Check first!

2. **Array index errors**: After `split(",")`, make sure you access `parts[0]` and `parts[1]` safely. What if a line is malformed?

3. **Parsing in wrong order**: If you mix up which part is the date vs duration, you'll get parsing errors.

4. **Creating sessions but not adding them**: After parsing, don't forget `sessions.add(newSession)`.

5. **Modifying constructor signature**: If you add `throws IOException` to the constructor, you'll need to update PracticeTracker where `new SessionManager()` is called.

## What You're Learning (The Bigger Picture)

This module completes your understanding of **data persistence fundamentals**:

1. **Serialization**: Converting in-memory objects to a format that can be stored
2. **Deserialization**: Converting stored data back into in-memory objects
3. **File I/O**: Reading and writing to the file system with proper error handling
4. **Parsing**: Breaking down structured text into meaningful pieces
5. **Type safety**: Explicit conversions that catch errors at parse time, not later

In real applications, you'd often use:
- **JSON** with libraries like Jackson or Gson (more structured than CSV)
- **Databases** with JDBC or ORM frameworks like Hibernate
- **Binary formats** like Protocol Buffers for performance

But the concepts are the same: serialize → store → deserialize → reconstruct.

## Extension Question (After Completion)

After you've implemented loading, think about:

**Q**: What happens if someone manually edits `sessions.txt` and introduces invalid data?
- Wrong date format?
- Non-numeric duration?
- Extra commas or missing commas?

For now, your app will crash with an exception. That's okay! Module 7 (Polish & Error Handling) will address this. Just be aware of the limitation.

## Success Criteria

You'll know you're done when:
- [ ] `SessionManager` constructor loads existing sessions from file
- [ ] Creating a new manager with an existing file loads the data
- [ ] The test (create data → restart app → data still there) passes
- [ ] You can explain the round trip: object → CSV → object
- [ ] You understand why Java requires explicit type conversions
- [ ] You can describe what exceptions might be thrown and why

Take your time. Parse one line successfully before worrying about loops. Test incrementally.
