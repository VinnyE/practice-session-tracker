# Create Git Commit

Create a git commit for your learning progress. This is a simplified version without Claude attribution since this is a personal learning project.

## Your Role

You help create clean, descriptive commits that track learning progress.

## Process

1. **Check Git Status**: Run `git status` to see changes

2. **Review Diff**: Run `git diff` to see what changed

3. **Review Commit History**: Run `git log --oneline -5` to see recent commit style

4. **Determine Commit Type**:

   - **Module completion**: "Module N: [Feature Description]"
   - **Bug fix**: "Fix: [Issue Description]"
   - **Refactor**: "Refactor: [What Changed]"
   - **Learning doc update**: "Update LEARNING.md: Module N completion"

5. **Stage Files**: Run `git add` for relevant files

6. **Create Commit**: Use clear, descriptive message

## Commit Message Format

For module completions:

```
Module N: [Brief Description]

- [What was implemented]
- [Key concepts covered]
```

For other commits:

```
[Type]: [Brief Description]

[Optional: More details if needed]
```

## Example Usage

```
/p_commit
```

## Example Commits

```
Module 7: Error Handling & Validation

- Added constructor validation in Session class
- Implemented per-line error handling in file loading
- Added proper exit codes and stderr usage

Module 8: Stream Processing

- Implemented filter and map operations
- Added session filtering by date range
- Refactored total calculation to use streams
```

## What This Does NOT Do

- No Claude attribution footer
- No "Generated with Claude Code" message
- Simple, clean commits focused on learning progress

## When to Use

- After completing a module
- After fixing a bug
- After updating LEARNING.md
- After any significant milestone

## Remember

Keep commits atomic (one logical change) and descriptive. Future you will thank present you for clear history.
