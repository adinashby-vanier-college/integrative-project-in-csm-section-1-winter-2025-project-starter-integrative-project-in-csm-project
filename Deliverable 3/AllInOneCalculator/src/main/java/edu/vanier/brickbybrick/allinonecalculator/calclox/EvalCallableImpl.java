package edu.vanier.brickbybrick.allinonecalculator.calclox;

import edu.vanier.brickbybrick.allinonecalculator.calclox.ast.Statement;
import edu.vanier.brickbybrick.allinonecalculator.calclox.token.Token;
import edu.vanier.brickbybrick.allinonecalculator.logic.ComputeEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.vanier.brickbybrick.allinonecalculator.calclox.token.TokenType.IDENTIFIER;

public class EvalCallableImpl extends LoxFunction {
    private final ComputeEngine computeEngine = new ComputeEngine();

    private static final Statement.Function declaration = new Statement.Function(
            new Token(IDENTIFIER, "eval", null, -1),
            List.of(new Token(IDENTIFIER, "expression", null, -1)),
            null
    );

    public EvalCallableImpl(Environment closure) {
        super(declaration, closure);
    }

    @Override
    public String toString() {
        return "<fun " + declaration.name.lexeme + ">";
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Map<String, Double> variables = new HashMap<>();
        super.closure.getValues().forEach(
                (key, value) -> {
                    if (value instanceof Double) {
                        variables.put(key, (Double) value);
                    }
                }
        );
        computeEngine.setVariables(variables);
        String expression = (String) arguments.getFirst();
        expression = expression.replace("\\\"", "\"");
        return computeEngine.evaluate(expression);
    }
}
