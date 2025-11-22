# Iterate Implementation Plan

Update an existing implementation plan based on user feedback.

## Your Role

You are refining an existing plan. Be skeptical, surgical, and thorough.

## Process

### Step 1: Get Plan Location

If plan file path not provided:

- Look in `thoughts/shared/plans/` directory
- Or ask: "Which plan file should I update?"

### Step 2: Get Change Request

If changes not specified:

- Ask: "What would you like to change about the plan?"
- Clarify the scope of changes needed

### Step 3: Read Plan Completely

- Read the ENTIRE plan file (no partial reads)
- Understand current structure and decisions

### Step 4: Research If Needed

Only spawn research agents if the change requires NEW technical understanding:

- Adding a new component → research that area
- Changing approach → research alternative approaches
- Otherwise, skip research and proceed to editing

If researching:

```
Spawn in parallel (single message, multiple Task calls):
- codebase-locator: "Find [relevant files]"
- codebase-analyzer: "Analyze [specific component]"
```

### Step 5: Confirm Understanding

Before making changes, present:

```
My understanding of your requested changes:
1. [Change 1]
2. [Change 2]

This will affect:
- [Section A] of the plan
- [Section B] of the plan

Is this correct?
```

Get confirmation before proceeding.

### Step 6: Make Surgical Edits

Use the Edit tool to make PRECISE changes:

- Don't rewrite entire sections unnecessarily
- Preserve existing content that's still valid
- Update only what needs to change

### Step 7: Present Changes

Show what was modified:

```
Updated [plan-name].md:

Changes made:
1. [Section]: [What changed and why]
2. [Section]: [What changed and why]

The plan now [summarize new approach/structure].
```

## Critical Guidelines

### Be Skeptical

Question vague requests:

- "Make it better" → Better how? What's the concern?
- "Add more detail" → Which section? What's missing?
- "Change approach" → Why? What's wrong with current approach?

### Be Surgical

Don't over-edit:

- ✅ Update specific section with new information
- ❌ Rewrite entire plan for small change

### Be Thorough

Read the ENTIRE plan before editing:

- Understand context and dependencies
- Ensure changes are consistent throughout
- Don't break references between sections

### No Open Questions

Don't add unresolved questions to the plan:

- ❌ "We could use A or B - TBD"
- ✅ "We'll use A because [reasoning]"

If you're unsure, research or ask user - then decide.

## When to Use

- User provides feedback on a plan
- Requirements changed after initial planning
- Need to add more detail to specific sections
- Need to adjust phasing or approach

## Example Usage

```
//p_iterate_plan thoughts/shared/plans/session-filtering.md "Add error handling details"
//p_iterate_plan
```

## Example Iteration Scenarios

### Adding Detail

```
User: "Add more detail about the error handling approach"

You:
1. Read complete plan
2. Ask: "Which error cases specifically should I detail?"
3. Get clarification
4. Edit the relevant section surgically
5. Present changes
```

### Changing Approach

```
User: "Actually, let's use streams instead of loops for filtering"

You:
1. Read complete plan
2. Research: Spawn codebase-analyzer to check current stream usage
3. Confirm: "So we'll replace the loop-based filtering in Phase 1 with stream operations?"
4. Edit Implementation Approach and Phase 1 sections
5. Update success criteria if needed
6. Present changes
```

### Adjusting Phasing

```
User: "Can we combine Phase 1 and Phase 2?"

You:
1. Read complete plan
2. Confirm: "Combining these phases means [X]. Is that correct?"
3. Merge the phases surgically
4. Update phase numbers for remaining phases
5. Adjust dependencies between phases
6. Present changes
```

## Remember

- Interactive - confirm understanding before editing
- Surgical - change only what needs changing
- Skeptical - question vague requests
- Thorough - read complete plan, maintain consistency
- Decisive - resolve questions, don't leave them open
