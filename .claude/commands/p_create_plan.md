# Create Implementation Plan

Create a detailed implementation plan for a v2 feature through interactive research and iteration.

## Your Role

You are a collaborative technical planner. Work with the user iteratively to create a comprehensive, actionable implementation plan.

## Process Overview

This is an INTERACTIVE process. Don't rush to write the plan - research thoroughly first.

### Step 1: Understand the Task

If no task description provided:

- Ask user: What feature are you planning to add?
- Ask: What's the motivation? What problem does it solve?
- Ask: Any constraints or requirements to consider?

If task is provided:

- Read any referenced files completely
- Clarify any ambiguities before proceeding

### Step 2: Research Current State

Spawn research agents in PARALLEL (single message, multiple Task calls):

1. **codebase-locator**: Find files relevant to the planned feature
2. **codebase-analyzer**: Analyze current architecture and patterns
3. **learning-assessment** (if adding to learning modules): Check learning readiness

Example:

```
Planning to add "session filtering by date range"

Spawn:
- codebase-locator: "Find all files related to session querying and filtering"
- codebase-analyzer: "Analyze SessionManager implementation and query patterns"
```

Wait for ALL agents to complete before proceeding.

### Step 3: Clarify Unknowns

Based on research, identify:

- Unclear requirements
- Multiple valid approaches
- Design decisions to make

Ask user for input BEFORE writing the plan. No open questions should remain.

### Step 4: Propose Plan Structure

Before writing details, propose the phasing approach:

```
I'm thinking we could structure this in 3 phases:
1. Phase 1: Add filtering logic to SessionManager
2. Phase 2: Add command-line interface for date filters
3. Phase 3: Update tests and documentation

Does this structure make sense, or would you prefer a different breakdown?
```

Get approval on structure before writing full plan.

### Step 5: Write Detailed Plan

Use this template:

```markdown
# Implementation Plan: [Feature Name]

## Overview

[1-2 sentence description of what we're building]

## Motivation

[Why this feature? What problem does it solve?]

## Current State Analysis

### Relevant Files

- [file.java:line] - [description]
- [file.java:line] - [description]

### Current Architecture

[How the system works now]

### Constraints & Considerations

- [Constraint 1]
- [Constraint 2]

## Desired End State

[What the system will look like after implementation]

## Implementation Approach

### Design Decisions

1. **[Decision 1]**: [Choice made and why]
2. **[Decision 2]**: [Choice made and why]

### Alternative Approaches Considered

- [Alternative 1]: [Why not chosen]
- [Alternative 2]: [Why not chosen]

## Implementation Phases

### Phase 1: [Phase Name]

**Goal**: [What this phase accomplishes]

**Changes**:

1. [Change 1]

   - File: [file.java:line]
   - Details: [specific implementation notes]

2. [Change 2]
   - File: [file.java:line]
   - Details: [specific implementation notes]

**Success Criteria**:

_Automated Verification_:

- [ ] `javac *.java` compiles without errors
- [ ] [Other runnable tests]

_Manual Verification_:

- [ ] [Human testing step 1]
- [ ] [Human testing step 2]

### Phase 2: [Phase Name]

[Same structure as Phase 1]

### Phase 3: [Phase Name]

[Same structure as Phase 1]

## Testing Strategy

[How to verify the implementation works]

## Rollback Plan

[How to undo changes if something goes wrong]

## Questions & Assumptions

[Should be EMPTY by the time plan is finalized]
```

### Step 6: Iterate Based on Feedback

After presenting the plan:

- User may request changes
- Use `//p_iterate_plan` command to refine
- Don't start implementation until plan is approved

## Critical Guidelines

### Research BEFORE Planning

- Spawn research agents in parallel
- Read relevant files completely (no partial reads)
- Verify assumptions through code investigation
- Wait for all research to complete before writing plan

### No Open Questions in Final Plan

Every decision must be made:

- ❌ "We could use approach A or B" → Must choose ONE
- ❌ "Need to figure out..." → Figure it out during planning
- ✅ "We'll use approach A because [reasoning]"

### Success Criteria Must Be Specific

Split into two categories:

**Automated Verification** (runnable commands):

- Compilation: `javac *.java`
- Tests: `java TestClass`
- Linting: `checkstyle *.java`

**Manual Verification** (human testing):

- "Run `java PracticeTracker filter 2025-11-01 2025-11-30` and verify output"
- "Check that sessions.txt contains valid data"
- "Verify error handling with invalid dates"

### Use Specific File References

Every change should reference specific files:

- ✅ "Update SessionManager.java:45 (addSession method)"
- ❌ "Update the session manager"

## When to Use

- Planning v2 features
- Planning significant refactors
- Before starting complex implementations
- When you need to think through architecture

## Example Usage

```
/p_create_plan Add session filtering by date range
/p_create_plan
```

## Remember

- Be skeptical - verify through code, don't assume
- Be thorough - read files completely, spawn research agents
- Be interactive - clarify with user before committing to decisions
- Be specific - file:line references, concrete success criteria
