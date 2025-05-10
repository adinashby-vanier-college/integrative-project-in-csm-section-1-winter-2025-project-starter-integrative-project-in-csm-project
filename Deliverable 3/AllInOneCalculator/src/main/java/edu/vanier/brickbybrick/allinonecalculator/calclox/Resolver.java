package edu.vanier.brickbybrick.allinonecalculator.calclox;

import edu.vanier.brickbybrick.allinonecalculator.calclox.ast.Expr;
import edu.vanier.brickbybrick.allinonecalculator.calclox.ast.Statement;
import edu.vanier.brickbybrick.allinonecalculator.calclox.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Resolver implements Expr.Visitor<Void>, Statement.Visitor<Void> {
    private final static Logger logger = LoggerFactory.getLogger(Resolver.class);

    private final Interpreter interpreter;
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();
    private FunctionType currentFunction = FunctionType.NONE;

    Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    private enum FunctionType {
        NONE,
        FUNCTION
    }

    public void resolve(List<Statement> statements) {
        for (Statement statement : statements) {
            resolve(statement);
        }
    }

    private void resolve(Statement Statement) {
        Statement.accept(this);
    }

    private void resolve(Expr expr) {
        expr.accept(this);
    }

    private void resolveFunction(Statement.Function function, FunctionType type) {
        FunctionType enclosingFunction = currentFunction;
        currentFunction = type;

        beginScope();
        for (Token param : function.params) {
            declare(param);
            define(param);
        }
        resolve(function.body);
        endScope();
        currentFunction = enclosingFunction;
    }

    private void beginScope() {
        scopes.push(new HashMap<String, Boolean>());
    }

    private void endScope() {
        scopes.pop();
    }

    private void declare(Token name) {
        if (scopes.isEmpty()) return;
        Map<String, Boolean> scope = scopes.peek();
        if (scope.containsKey(name.lexeme)) {
            logger.error("Already a variable with the name {} in this scope.", name);
        }
        scope.put(name.lexeme, false);
    }

    private void define(Token name) {
        if (scopes.isEmpty()) return;
        scopes.peek().put(name.lexeme, true);
    }

    private void resolveLocal(Expr expr, Token name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.lexeme)) {
                interpreter.resolve(expr, scopes.size() - 1 - i);
                return;
            }
        }
    }

    @Override
    public Void visitAssignExpr(Expr.Assign expr) {
        resolve(expr.value);
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitBinaryExpr(Expr.Binary expr) {
        resolve(expr.left);
        resolve(expr.right);
        return null;
    }

    @Override
    public Void visitCallExpr(Expr.Call expr) {
        resolve(expr.callee);

        for (Expr argument : expr.arguments) {
            resolve(argument);
        }

        return null;
    }

    @Override
    public Void visitGroupingExpr(Expr.Grouping expr) {
        resolve(expr.expression);
        return null;
    }

    @Override
    public Void visitLiteralExpr(Expr.Literal expr) {
        return null;
    }

    @Override
    public Void visitLogicalExpr(Expr.Logical expr) {
        resolve(expr.left);
        resolve(expr.right);
        return null;
    }

    @Override
    public Void visitUnaryExpr(Expr.Unary expr) {
        resolve(expr.right);
        return null;
    }

    @Override
    public Void visitVariableExpr(Expr.Variable expr) {
        if (!scopes.isEmpty() && scopes.peek().get(expr.name.lexeme) == Boolean.FALSE) {
            logger.error("Can't read local variable {} in its own initializer.", expr.name);
        }
        resolveLocal(expr, expr.name);
        return null;
    }

    @Override
    public Void visitBlockStatement(Statement.Block Statement) {
        beginScope();
        resolve(Statement.statements);
        endScope();
        return null;
    }

    @Override
    public Void visitDefineStatement(Statement.Define statement) {
        declare(statement.name);
        define(statement.name);
        return null;
    }

    @Override
    public Void visitExpressionStatement(Statement.Expression Statement) {
        resolve(Statement.expression);
        return null;
    }

    @Override
    public Void visitFunctionStatement(Statement.Function Statement) {
        declare(Statement.name);
        define(Statement.name);
        resolveFunction(Statement, FunctionType.FUNCTION);
        return null;
    }

    @Override
    public Void visitIfStatement(Statement.If Statement) {
        resolve(Statement.condition);
        resolve(Statement.thenBranch);
        if (Statement.elseBranch != null) resolve(Statement.elseBranch);
        return null;
    }

    @Override
    public Void visitOutputStatement(Statement.Output statement) {
        resolve(statement.expression);
        return null;
    }

    @Override
    public Void visitReturnStatement(Statement.Return Statement) {
        if (currentFunction == FunctionType.NONE) {
            logger.error("Can't return from top-level code. Statement: {}", Statement.keyword);
        }

        if (Statement.value != null) {
            resolve(Statement.value);
        }

        return null;
    }

    @Override
    public Void visitVarStatement(Statement.Var Statement) {
        declare(Statement.name);
        if (Statement.initializer != null) {
            resolve(Statement.initializer);
        }
        define(Statement.name);
        return null;
    }

    @Override
    public Void visitWhileStatement(Statement.While Statement) {
        resolve(Statement.condition);
        resolve(Statement.body);
        return null;
    }
}
