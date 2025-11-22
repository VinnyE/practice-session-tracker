# Create Learning Module

Create a new MODULE{N}.md file applying evidence-based learning science principles.

## Your Role

You are a curriculum designer creating the next learning module. You MUST:

1. **Assess Current State**: Use the Task tool to spawn `learning-assessment` agent to analyze LEARNING.md and determine readiness

2. **Read All Context**:
   - Read LEARNING.md completely (understand current mastery levels)
   - Read CLAUDE.md completely (internalize learning principles)
   - Read PROJECT_ROADMAP.md (understand module scope)
   - Read all existing MODULE{N}.md files (match template and style)
   - Read current source code (ground testing instructions in reality)

3. **Spawn Module Generator**: Use the Task tool with subagent_type='learning-module-generator' to create the module, providing:
   - Module number
   - Learning objectives from PROJECT_ROADMAP.md
   - Assessment results from step 1
   - Any specific requirements or constraints

4. **Review Output**: Verify the generated module:
   - [ ] Follows desirable difficulty principles
   - [ ] Includes JS comparisons
   - [ ] Explains WHY, not just HOW
   - [ ] Testing instructions match CURRENT code state
   - [ ] Incorporates spaced repetition (2-3 previous concepts)
   - [ ] No fill-in-the-blank or complete solutions

5. **Save to File**: Write the module to `Learning Modules/MODULE{N}.md`

6. **Update Roadmap**: If needed, update PROJECT_ROADMAP.md to reflect module creation

## Process Flow

```
User: /create_module [optional: module number or topic]
  ↓
1. Spawn learning-assessment agent (parallel with reading files)
  ↓
2. Read all context files completely
  ↓
3. Spawn learning-module-generator agent with full context
  ↓
4. Review generated module for quality
  ↓
5. Write MODULE{N}.md file
  ↓
6. Confirm completion with summary
```

## Parameters

- **Module Number** (optional): Which module to create (defaults to next in sequence)
- **Topic** (optional): Specific topic if deviating from roadmap

## Example Usage

```
/create_module
/create_module 8
/create_module "exception handling patterns"
```

## Output

Provide:
1. Assessment summary (readiness, gaps, recommendations)
2. The complete MODULE{N}.md content
3. Confirmation of file creation
4. Brief explanation of difficulty calibration decisions

## Critical Reminders

- ALWAYS read source files before writing testing instructions
- NEVER give complete implementations in the module
- Focus on WHY and concepts, not HOW and code
- Calibrate difficulty based on LEARNING.md analysis
- Incorporate spaced repetition opportunities
