# junit-best-practices-ai
AI Coding Agent Instructions file with best practices of Junit Test Cases  


# Unit Test Cases Best Practices
Claude was used to search the best practices of unit testing, generate the code examples and coding agent instruction markdown file.

## Follow the FIRST Principles
**Summary:** Tests should be Fast, Independent/Isolated, Repeatable, Self-validating, and Timely to ensure reliability and maintainability.

### Best Practices
- **F - Fast:** Execute in milliseconds to encourage frequent runs during development
- **I - Independent/Isolated:** Each test should be completely independent of other tests. Tests shouldn't rely on the execution order or share state. One test's success or failure shouldn't affect another.
  - No shared mutable state between tests
  - Each test sets up its own data
  - Tests can run in any order or in parallel
- **R - Repeatable:** Produce consistent results regardless of environment or execution order - Repeatable
  - Avoid dependencies on:
    - Current date/time (use mocking or fixed values)
    - External systems (databases, APIs - use mocks or in-memory alternatives)
    - Network conditions
    - Random values (use seeded random or fixed values)
- **S - Self-Valididating:** Automatically verify pass/fail without manual inspection
- **T - Timely:** Tests should be written at the right time - ideally just before or alongside the production codeCode Example

### Code Examples
[Examples with Fast Principle ] (examples/follow-FIRST-principles/FastPrincipleExample.java)
[Examples with Independent/Isolated Principle - Good Example] (examples/follow-FIRST-principles/IndependentPrincipleGoodExample.java)
[Examples with Independent/Isolated Principle - Bad Example] (examples/follow-FIRST-principles/IndependentPrincipleBadExample.java)
[Example with Repeatable Principle - Good Example] (examples/follow-FIRST-principles/RepeatablePrincipleGoodExample.java)
[Example with Repeatable Principle - Bad Example] (examples/follow-FIRST-principles/RepeatablePrincipleBadExample.java)
[Example with Self-Validation - Good Example] (examples/follow-FIRST-principles/SelfValidationPrincipleGoodExample.java)
[Example with Self-Validation - Bad Example] (examples/follow-FIRST-principles/SelfValidationPrincipleBadExample.java)

