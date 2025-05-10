package edu.vanier.brickbybrick.allinonecalculator.calclox.exceptions;

/**
 * The exception the CalcLoxRunner might throw when handling an error.
 * <br/>
 * These exceptions are intended to replace the printed error handling used by the official Lox Interpreter
 * in the Crafting Interpreters book and are meant to be caught and handled by the method calling the CalcLoxRunner.
 *
 * @author Qian Qian
 */
public class CalcLoxRunnerError extends RuntimeException {
    private final int line;

    public CalcLoxRunnerError(String message, int line) {
        super(message);
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
