# Claude Code Configuration

This directory contains specialized agents and slash commands designed to support learning-oriented Java development with evidence-based educational principles.

## Philosophy

This project applies **cognitive science principles** to technical education. The agents and commands here implement:

- **Desirable Difficulties**: Creating productive struggle without frustration
- **Spaced Repetition**: Systematically reinforcing concepts over time
- **Mastery Learning**: No progression until fundamentals are solid
- **Active Retrieval**: Teaching concepts, not giving solutions
- **Cognitive Load Management**: One major concept per module

See `CLAUDE.md` in the project root for complete learning philosophy.

## Agents

Specialized subagents that can be spawned for specific tasks.

### Learning-Focused Agents

#### `learning-module-generator`

Creates MODULE{N}.md files with appropriate difficulty based on LEARNING.md progress.

**When to use**: Creating new learning modules for the curriculum

**Key capabilities**:

- Analyzes LEARNING.md to calibrate difficulty
- Applies desirable difficulty principles
- Incorporates spaced repetition
- Reads source code to ground testing instructions
- Follows established module template

**Example**:

```
Used by /create_module command
```

#### `learning-assessment`

Analyzes learning progress and identifies gaps.

**When to use**: Before starting new modules, after completion, when uncertain about progress

**Key capabilities**:

- Identifies spaced repetition gaps
- Detects shaky understanding despite ✅ marks
- Assesses prerequisites for next module
- Provides actionable recommendations

**Example**:

```
Used by /assess_learning command
```

#### `java-concept-explainer`

Deep explanations of Java concepts with WHY focus.

**When to use**: User asks "why does Java do X?", needs tradeoff analysis

**Key capabilities**:

- Explains design philosophy and tradeoffs
- Compares to JavaScript
- Grounds in CS fundamentals
- Provides mental models

**Example**:

```
Spawn with: "Explain why Java uses checked exceptions"
```

### Code Analysis Agents

(Note: These are provided by Claude Code by default, but documented here for completeness)

#### `Explore`

Fast exploration of codebase structure and patterns.

**Thoroughness levels**: "quick", "medium", "very thorough"

#### `codebase-locator`

Finds files and directories relevant to a feature or task.

#### `codebase-analyzer`

Deep analysis of specific implementations.

## Commands

Slash commands for common workflows.

### Learning Workflow

#### `/create_module [optional: number or topic]`

Creates a new learning module with appropriate difficulty.

**Process**:

1. Assesses current learning state
2. Reads all context (LEARNING.md, CLAUDE.md, etc.)
3. Spawns learning-module-generator
4. Writes MODULE{N}.md file

**Example**:

```
/create_module
/create_module 8
/create_module "stream processing"
```

#### `/assess_learning`

Comprehensive analysis of learning progress.

**Output**:

- Overall status
- Spaced repetition alerts
- Mastery concerns
- Readiness for next module
- Recommendations

**Example**:

```
/assess_learning
```

### Development Workflow

#### `//p_research_codebase [topic]`

Comprehensive parallel research using specialized agents.

**What it does**:

- Spawns Explore, codebase-locator, and codebase-analyzer in parallel
- Synthesizes findings into coherent understanding
- Provides file references and architecture overview

**Example**:

```
//p_research_codebase session management
//p_research_codebase file persistence
```

#### `/p_commit`

Create clean git commits for learning progress.

**What it does**:

- Checks status and diff
- Reviews commit history for style
- Creates descriptive commit
- No Claude attribution (personal learning project)

**Example**:

```
/p_commit
```

### Planning Workflow

#### `/p_create_plan [optional: description]`

Create detailed implementation plan through interactive research.

**Process**:

1. Clarify requirements
2. Spawn parallel research agents
3. Resolve all open questions
4. Propose structure for approval
5. Write detailed plan with phases and success criteria

**Saves to**: `thoughts/shared/plans/`

**Example**:

```
/p_create_plan Add session filtering by date range
/p_create_plan
```

#### `//p_iterate_plan [plan-file] [changes]`

Update existing plan based on feedback.

**Process**:

1. Read complete plan
2. Research if needed
3. Confirm understanding
4. Make surgical edits
5. Present changes

**Example**:

```
//p_iterate_plan thoughts/shared/plans/filtering.md "Add error handling details"
```

#### `//p_validate_plan [plan-file]`

Verify implementation matches plan.

**Process**:

1. Read plan and implementation
2. Run automated verification
3. Check manual verification steps
4. Identify gaps and issues
5. Provide validation report

**Example**:

```
//p_validate_plan thoughts/shared/plans/filtering.md
```

#### `//p_implement_plan [plan-file]`

Systematically implement an approved plan.

**Process**:

1. Read complete plan
2. Confirm approach with user
3. Implement phase-by-phase
4. Verify after each phase
5. Final validation

**Example**:

```
//p_implement_plan thoughts/shared/plans/filtering.md
```

## Directory Structure

```
.claude/
├── README.md (this file)
├── settings.local.json
├── agents/
│   ├── learning-module-generator.md
│   ├── learning-assessment.md
│   └── java-concept-explainer.md
└── commands/
    ├── create_module.md
    ├── assess_learning.md
    ├── /p_research_codebase.md
    ├── commit.md
    ├── p_create_plan.md
    ├── /p_iterate_plan.md
    ├── /p_validate_plan.md
    └── /p_implement_plan.md

thoughts/
└── shared/
    └── plans/  (implementation plans stored here)
```

## Workflow Examples

### Creating a New Learning Module

```bash
# Assess current state
/assess_learning

# Create next module
/create_module

# After completing module, commit
/p_commit
```

### Planning and Implementing a V2 Feature

```bash
# Research the relevant code
//p_research_codebase session filtering

# Create implementation plan
/p_create_plan Add filtering by date range

# (Review and iterate on plan as needed)
//p_iterate_plan thoughts/shared/plans/filtering.md "Clarify error handling"

# Implement the plan
//p_implement_plan thoughts/shared/plans/filtering.md

# Validate implementation
//p_validate_plan thoughts/shared/plans/filtering.md

# Commit the feature
/p_commit
```

### Understanding a Java Concept

```bash
# Spawn java-concept-explainer agent
Task tool with subagent_type='java-concept-explainer'
Prompt: "Explain why Java uses static typing and what tradeoffs it makes vs JavaScript"
```

## Key Principles

### Agents Follow Learning Science

All learning-focused agents implement principles from CLAUDE.md:

- Desirable difficulties (productive struggle)
- Spaced repetition (systematic reinforcement)
- Mastery learning (no progression with gaps)
- Active learning (teach concepts, not solutions)

### Commands Enable Systematic Work

Commands provide structure for:

- Consistent learning module creation
- Thorough implementation planning
- Verified phase-by-phase implementation
- Quality assurance through validation

### Parallel Agent Execution

For efficiency, spawn independent agents in parallel:

```
Use single message with multiple Task tool calls:
- Task: Explore agent
- Task: codebase-locator
- Task: codebase-analyzer
```

### Interactive, Not Automatic

Commands are designed for collaboration:

- Ask clarifying questions
- Confirm approach before proceeding
- Present options for user decision
- Verify understanding iteratively

## Customization

These agents and commands are templates. Modify them to match your learning style and project needs.

Key files to review:

- **CLAUDE.md**: Core learning philosophy and guidelines
- **LEARNING.md**: Current understanding state (read by agents)
- **PROJECT_ROADMAP.md**: Curriculum structure

## For V2 Development

When adding features to v2:

1. Use `/p_research_codebase` to understand current implementation
2. Use `/p_create_plan` for thoughtful design
3. Use `/p_implement_plan` for systematic implementation
4. Use `/p_validate_plan` to ensure quality
5. Use `/p_commit` for clean git history

The learning workflow commands still apply - v2 features can become new learning modules if desired.
