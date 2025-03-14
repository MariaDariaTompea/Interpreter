
# Java Interpreter Project

This project is a Java-based interpreter for a custom programming language. It includes various components such as expressions, statements, and program state management.

## Project Structure

- `src/main/java/com/interpreter/`: Contains the main source code for the interpreter.
    - `model/`: Contains the core models such as expressions, statements, and program state.
    - `exceptions/`: Contains custom exception classes used throughout the project.
    - `adt/`: Contains abstract data types used in the interpreter.
- `src/test/java/com/interpreter/`: Contains unit tests for the interpreter components.

## Key Components

### Expressions

Expressions are used to evaluate values. Examples include:
- `VariableExpression`
- `RelationalExpression`

### Statements

Statements are used to perform actions. Examples include:
- `AssignStatement`
- `ForStatement`
- `WhileStatement`

### Program State

The `PrgState` class represents the state of a program during execution, including the symbol table, execution stack, and heap.

## Usage

To run the interpreter, you need to set up a repository and controller, and then execute the program states. Example setup:

```java
IRepository repo = new Repository("log.txt");
repo.addPrgState(prgState);
Controller controller = new Controller((Repository) repo);
controller.executeAll();