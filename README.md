# AutoDerivR

This program can calculate calculate derivatives based on a computation tree.

## Capabilities

The class [`Node`](src/main/java/io/github/danthe1st/autoderivr/operations/Node.java) represents a calculation.
`Node`s can be evaluated with certain values of variables.
Also, it is possible to symbolically calculate the derivative of a `Node` with respect to a given `Variable` using the `derivative` method.

Examples can be found in the [tests](src/test/java), e.g. the [`CombinedTests`](src/test/java/io/github/danthe1st/autoderivr/tests/CombinedTests.java) or [`TrigTests`](src/test/java/io/github/danthe1st/autoderivr/tests/arithmetic/concrete/TrigTests.java) classes.

### Supported operations
The following operations are supported:
- basic arithmetic: addition, subtraction, multiplication, division
- powers (with a constant exponent), exponentials (with a constant base), logarithms (with a constant base) and roots (with a constant index)
- trigonometric functions: sine, cosine, tangent and their inverses
- Piecewise defined functions

## Setup
This project uses [Maven](https://maven.apache.org/download.cgi) and requires [Java 21](https://jdk.java.net/21/).

If Maven and Java 21 is installed, the tests can be run using the command `mvn test`

### Eclipse
This project requires Eclipse 2023-09 (4.29) and the plugin [Java 21 Support for Eclipse 2023-09 (4.29)](https://marketplace.eclipse.org/content/java-21-support-eclipse-2023-09-429).

For importing this project in Eclipse, select `File`>`Import`>`Maven`>`Existing Maven Project` and import this project.

### IntelliJ
Import this project as a Maven project.

It is possible that IntelliJ shows false compiler errors due to the use of [Record Patterns](https://openjdk.org/jeps/440).
If this is the case, it should still be possible to run this project.