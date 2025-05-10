import edu.vanier.brickbybrick.allinonecalculator.calclox.CalcLoxRunner;
import edu.vanier.brickbybrick.allinonecalculator.calclox.CalculatorFrontend;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class SuccessResult extends RuntimeException {
    public final String result;

    public SuccessResult(String result) {
        this.result = result;
    }
}

class CalculatorFrontendImpl extends CalculatorFrontend {
    public CalculatorFrontendImpl() {
        this.variables = new HashMap<>();
    }

    public void addVariable(String variable, Double value) {
        this.variables.put(variable, value);
    }

    @Override
    public void output(String result) {
        throw new SuccessResult(result);
    }
}

public class CalcLoxTest {
    @Test
    public void testOutput() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                output 3;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("3.0");
            return;
        }

        assert false;
    }

    @Test
    public void testCalculation() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var a = 1;
                var b = 2;
                output a + b;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("3.0");
            return;
        }

        assert false;
    }

    @Test
    public void testIfElse1() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var a = 1;
                var b = 2;
                if (a < b) {
                    output "a is less than b";
                } else {
                    output "a is not less than b";
                }
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("a is less than b");
            return;
        }

        assert false;
    }

    @Test
    public void testIfElse2() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var a = 2;
                var b = 1;
                if (a < b) {
                    output "a is less than b";
                } else {
                    output "a is not less than b";
                }
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("a is not less than b");
            return;
        }

        assert false;
    }

    @Test
    public void testWhileLoop() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var a = 0;
                while (a < 5) {
                    a = a + 1;
                }
                output a;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("5.0");
            return;
        }

        assert false;
    }

    @Test
    public void testForLoop() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var a = 0;
                for (var i = 0; i < 5; i = i + 1) {
                    a = a + 1;
                }
                output a;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("5.0");
            return;
        }

        assert false;
    }

    @Test
    public void testFunction() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                fun addOne(a, b) {
                    return a + b + 1;
                }
                
                var result = addOne(1, 2);
                output result;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("4.0");
            return;
        }

        assert false;
    }

    @Test
    public void basicEvalTest() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();

        String source = """
                var result = eval("[\\"Add\\", 1, 2]");
                output result;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("3.0");
            return;
        }

        assert false;
    }

    @Test
    public void defineTest() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();
        frontend.addVariable("a", 1.0);
        frontend.addVariable("b", 2.0);

        String source = """
                define a;
                define b;
                output a + b;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("3.0");
            return;
        }

        assert false;
    }

    @Test
    public void defineInEvalTest() {
        CalculatorFrontendImpl frontend = new CalculatorFrontendImpl();
        frontend.addVariable("a", 1.0);
        frontend.addVariable("b", 2.0);

        String source = """
                define a;
                define b;
                var result = eval("[\\"Add\\", \\"a\\", \\"b\\"]");
                output result;
                """;

        try {
            CalcLoxRunner.run(frontend, source);
        } catch (SuccessResult e) {
            assert e.result != null;
            assert e.result.equals("3.0");
            return;
        }

        assert false;
    }
}
