# Module 1: Project Setup & Basic Structure

**Time Estimate**: 30-45 minutes
**Difficulty**: Beginner

## Goal
Create a Java file with a `main` method that prints back whatever command you give it.

## Learning Objectives
- Understand Java project structure (where files go, naming conventions)
- Learn the `main` method signature and why it looks that way
- Work with `String[] args` (command-line arguments)
- Use `System.out.println()` for basic output

## Background: Understanding Java Project Structure

### JavaScript vs Java File Organization

**JavaScript world:**
```
project/
  src/
    index.js
    utils.js
  package.json
```

**Java world (our simple version):**
```
project/
  PracticeTracker.java
  Session.java
  SessionManager.java
```

For this simple project, we'll keep all `.java` files in the root. In larger projects, you'd use **packages** (like folders that create namespaces), but let's keep it simple for now.

## Key Concept: The Entry Point

### The Magic Incantation: `public static void main(String[] args)`

Every Java program needs an **entry point**. Unlike JavaScript where the file just executes top-to-bottom, Java needs to know "which method should run when I start this program?"

That method is always:
```java
public static void main(String[] args)
```

### Breaking Down Each Keyword:

- **`public`** - Can be called from outside this class (the JVM needs to call it)
- **`static`** - Belongs to the class itself, not an instance (no `new PracticeTracker()` needed)
- **`void`** - Returns nothing (like JS functions without a return)
- **`main`** - The name the JVM looks for (convention, like `index.js`)
- **`String[] args`** - Array of command-line arguments (like `process.argv` in Node)

### Why Static?

When Java starts your program, **no objects exist yet**. It needs to run `main` before any objects are created. So `main` can't depend on an object - it must be static (belongs to the class itself).

Think of it like: The JVM says "Hey PracticeTracker class, run your main method" - not "Hey specific PracticeTracker object, run your method."

## Your Task

**Create a file called `PracticeTracker.java`** with this structure:

1. A class named `PracticeTracker` (must match the filename exactly)
2. A `main` method inside it
3. Code that prints the command-line arguments

### What You Need to Know:

- `String[] args` is an array of strings (the command-line arguments)
- `System.out.println("text")` prints a line (like `console.log`)
- You can access array elements with `args[0]`, `args[1]`, etc. (just like JS)
- Arrays have a `.length` property (not `.length()`, no parentheses!)

### Expected Behavior:

When you run:
```bash
java PracticeTracker.java add 45
```

It should print something like:
```
Command: add
Argument: 45
```

Or however you want to format it - the point is to show you're reading the command-line arguments.

## Hints:

1. You'll need to check if arguments exist before accessing them (avoid array out of bounds)
2. The class name and filename must match exactly (case-sensitive)
3. Java is compiled, but with modern Java you can run `.java` files directly and it compiles on the fly
4. Array indices start at 0, just like JavaScript

## Template to Get Started:

```java
public class PracticeTracker {
    public static void main(String[] args) {
        // Your code here
        // Check if args has elements
        // Print them out
    }
}
```

## Testing Your Work:

Try running with different inputs:
```bash
java PracticeTracker.java add 45
java PracticeTracker.java list
java PracticeTracker.java total
java PracticeTracker.java
```

Make sure it handles all cases without crashing!

## Success Criteria:

- [ ] File is named `PracticeTracker.java` (exact match to class name)
- [ ] Class contains a properly structured `main` method
- [ ] Program prints command-line arguments when run
- [ ] Program doesn't crash when run with no arguments
- [ ] You understand why each keyword in `main` is necessary

## Common Mistakes to Watch For:

1. **Filename doesn't match class name** - Java will complain
2. **Forgetting `String[] args`** - The signature must be exact
3. **Accessing `args[0]` without checking length** - ArrayIndexOutOfBoundsException
4. **Using `args.length()` instead of `args.length`** - length is a property, not a method

## Next Steps:

Once this works, we'll commit it and move on to Module 2 where we'll create the `Session` data model class.

---

**Give it a shot! Write the code and share your attempt.**
