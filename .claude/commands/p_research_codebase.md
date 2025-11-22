# Research Codebase Comprehensively

Research the codebase using parallel specialized agents for thorough understanding.

## Your Role

You are coordinating a comprehensive codebase research effort. Spawn multiple specialized agents in parallel to explore different aspects of the codebase.

## Process

1. **Clarify Scope**: If user didn't specify what to research, ask:

   - What feature/component are you interested in?
   - What's the goal? (Understanding architecture, finding implementation, planning changes)
   - Any specific files or areas to focus on?

2. **Spawn Research Agents in Parallel**:

   Use Task tool to spawn these agents simultaneously (single message, multiple tool calls):

   - **Explore** (thoroughness: "medium" or "very thorough"): Overall codebase structure
   - **codebase-locator**: Find relevant files for the research topic
   - **codebase-analyzer**: Deep dive into specific implementations

   Example prompt for agents:

   ```
   Explore agent: "Map out the overall architecture and identify key components related to [topic]"
   codebase-locator: "Locate all files related to [feature/component]"
   codebase-analyzer: "Analyze the implementation details of [specific component]"
   ```

3. **Synthesize Findings**: Once all agents report back:

   - Combine insights into coherent understanding
   - Identify patterns and key design decisions
   - Highlight areas of complexity or potential issues
   - Note any gaps in understanding

4. **Present Results**: Structured report with:
   - Architecture overview
   - Key components and their relationships
   - Important implementation details
   - File locations with references (file:line format)
   - Recommendations or insights

## Parameters

- **Topic** (required if not in context): What to research
- **Depth** (optional): "quick", "medium", or "very thorough"

## Example Usage

```
//p_research_codebase session management
//p_research_codebase file persistence implementation
//p_research_codebase
```

## Output Format

```markdown
# Codebase Research: [Topic]

## Architecture Overview

[High-level structure]

## Key Components

### [Component 1]

- Location: file.java:line
- Responsibility: [what it does]
- Key methods: [list]

### [Component 2]

[...]

## Implementation Details

[Important patterns, design decisions]

## File Reference Map

- Primary: [files]
- Related: [files]
- Tests: [files]

## Insights & Recommendations

[Observations about the codebase]
```

## When to Use

- Starting work on a new feature
- Understanding how something works
- Planning refactoring or changes
- Debugging complex issues
- Learning the codebase architecture

## Remember

Spawn agents in PARALLEL (single message with multiple Task calls) for efficiency.
