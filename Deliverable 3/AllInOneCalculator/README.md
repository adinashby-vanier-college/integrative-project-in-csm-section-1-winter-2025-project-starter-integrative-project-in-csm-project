# All-in-One Calculator

Brick by Brick (CiHao Zhang, Lucas Chauveau, Qian Qian), Vanier College 420-204-RE Final Project, Winter 2025

This project **HAVE** be run on **Java 21**.

## Project Structure

This is a diagram of the project's structure, mainly describing the structure of the packages without the details of the classes (as those would be described in the Javadoc of the classes themselves).

For more detailed information about each package, refer to the Javadoc contained in the `package-info.java` of the package.

```
java.edu.vanier.brickbybrick.allinonecalculator
├── calclox: The language interperter for calcLox, a modified (simplified) version of Lox designed for the programmable feature of the calculator.
├── controllers: The package for all the JavaFX FXML controllers.
├── helpers: The package for all the helper classes that are used.
├── logic: The backend logics of the calculator (both arithmetic and graphing), including arithmetic operations, graphing engine, etc.
├── tests: The package containing all the tests for logical/mathematical/algorithmic methods.
├── MainApp: The main JavaFX application class that handles init, stop, and scene switching.
```

## Libraries Used

### Java

- JavaFX 22.0.2 - Java GUI Library
- Logback 1.4.11 - Logging Library

## Resources/Credits

- [JavaFX Project Template](https://github.com/frostybee/javafx-template) by [@frostybee](https://github.com/frostybee).
- [Crafting Interpreters](https://craftinginterpreters.com/) by Bob Nystrom. This book introduces the [Lox language](https://craftinginterpreters.com/the-lox-language.html), the language that `calcLox`, the programming language that powers the programmable feature of the calculator, is based on (`calcLox` is a simplified version of Lox, without some features like classes and inheritance). This book also contains [a guide for building a tree-walk interpreter](https://craftinginterpreters.com/a-tree-walk-interpreter.html), which was used as a reference for building the interpreter for `calcLox`.
