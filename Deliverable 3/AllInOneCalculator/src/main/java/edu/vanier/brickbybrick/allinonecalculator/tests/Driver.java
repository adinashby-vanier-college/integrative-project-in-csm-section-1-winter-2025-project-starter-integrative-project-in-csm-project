package edu.vanier.brickbybrick.allinonecalculator.tests;

import edu.vanier.brickbybrick.allinonecalculator.logic.ComputeEngine;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Driver class is responsible for running test cases or other testing logic
 * within the application.
 *
 * @author frostybee
 */
public class Driver {
    // Logger instance for logging messages
    private final static Logger logger = LoggerFactory.getLogger(Driver.class);
    private final static ComputeEngine engine = new ComputeEngine();

    /**
     * Tests the integral calculation functionality
     */
    private static void testIntegrals() {
        logger.info("Testing integrals...");
        
        // Test integral of x^2 from 0 to 1 (exact value: 1/3)
        try {
            String result = engine.evaluate("[\"Integral\", \"x^2\", \"x\", 0, 1]");
            double value = Double.parseDouble(result);
            logger.info("Integral of x^2 from 0 to 1: " + value);
            // Should be approximately 0.333...
            if (Math.abs(value - 1.0/3.0) > 0.001) {
                logger.error("Error: Expected approximately 0.333, got " + value);
            }
        } catch (Exception e) {
            logger.error("Error testing basic integral: " + e.getMessage());
        }

        // Test integral of sin(x) from 0 to Pi (exact value: 2)
        try {
            String result = engine.evaluate("[\"Integral\", \"sin(x)\", \"x\", 0, \"Pi\"]");
            double value = Double.parseDouble(result);
            logger.info("Integral of sin(x) from 0 to Pi: " + value);
            // Should be approximately 2
            if (Math.abs(value - 2.0) > 0.001) {
                logger.error("Error: Expected approximately 2.0, got " + value);
            }
        } catch (Exception e) {
            logger.error("Error testing trigonometric integral: " + e.getMessage());
        }

        // Test integral of x from -1 to 1 (exact value: 0)
        try {
            String result = engine.evaluate("[\"Integral\", \"x\", \"x\", -1, 1]");
            double value = Double.parseDouble(result);
            logger.info("Integral of x from -1 to 1: " + value);
            // Should be approximately 0
            if (Math.abs(value) > 0.001) {
                logger.error("Error: Expected approximately 0.0, got " + value);
            }
        } catch (Exception e) {
            logger.error("Error testing integral with negative bounds: " + e.getMessage());
        }

        // Test integral of e^x from 0 to 1 (exact value: e - 1)
        try {
            String result = engine.evaluate("[\"Integral\", \"e^x\", \"x\", 0, 1]");
            double value = Double.parseDouble(result);
            logger.info("Integral of e^x from 0 to 1: " + value);
            // Should be approximately e - 1 ≈ 1.718
            if (Math.abs(value - (Math.E - 1)) > 0.001) {
                logger.error("Error: Expected approximately " + (Math.E - 1) + ", got " + value);
            }
        } catch (Exception e) {
            logger.error("Error testing exponential integral: " + e.getMessage());
        }

        // Test integral of 1/x from 1 to 2 (exact value: ln(2))
        try {
            String result = engine.evaluate("[\"Integral\", \"1/x\", \"x\", 1, 2]");
            double value = Double.parseDouble(result);
            logger.info("Integral of 1/x from 1 to 2: " + value);
            // Should be approximately ln(2) ≈ 0.693
            if (Math.abs(value - Math.log(2)) > 0.001) {
                logger.error("Error: Expected approximately " + Math.log(2) + ", got " + value);
            }
        } catch (Exception e) {
            logger.error("Error testing logarithmic integral: " + e.getMessage());
        }
    }

    /**
     * Tests the nth root calculation functionality
     */
    private static void testNthRoot() {
        logger.info("Testing nth root...");
        
        // Test square root
        try {
            String result = engine.evaluate("[\"NthRoot\", 16, 2]");
            logger.info("Square root of 16: " + result);
            // Expected result: 4
        } catch (Exception e) {
            logger.error("Error testing square root: " + e.getMessage());
        }

        // Test cube root
        try {
            String result = engine.evaluate("[\"NthRoot\", 27, 3]");
            logger.info("Cube root of 27: " + result);
            // Expected result: 3
        } catch (Exception e) {
            logger.error("Error testing cube root: " + e.getMessage());
        }

        // Test negative number with odd root
        try {
            String result = engine.evaluate("[\"NthRoot\", -8, 3]");
            logger.info("Cube root of -8: " + result);
            // Expected result: -2
        } catch (Exception e) {
            logger.error("Error testing negative number with odd root: " + e.getMessage());
        }

        // Test error case: even root of negative number
        try {
            String result = engine.evaluate("[\"NthRoot\", -4, 2]");
            logger.error("Should have thrown error for even root of negative number");
        } catch (Exception e) {
            logger.info("Correctly caught error for even root of negative number");
        }
    }

    /**
     * Tests the derivative calculation functionality
     */
    private static void testDerivatives() {
        logger.info("Testing derivatives...");
        
        // Test derivative of polynomial
        try {
            String result = engine.evaluate("[\"Derivative\", [\"Power\", \"x\", 2], \"x\", 2, 0.0001]");
            logger.info("Derivative of x^2 at x=2: " + result);
            // Expected result should be approximately 4
        } catch (Exception e) {
            logger.error("Error testing polynomial derivative: " + e.getMessage());
        }

        // Test derivative of trigonometric function
        try {
            String result = engine.evaluate("[\"Derivative\", [\"Sin\", \"x\"], \"x\", 0, 0.0001]");
            logger.info("Derivative of sin(x) at x=0: " + result);
            // Expected result should be approximately 1
        } catch (Exception e) {
            logger.error("Error testing trigonometric derivative: " + e.getMessage());
        }

        // Test derivative of constant
        try {
            String result = engine.evaluate("[\"Derivative\", 5, \"x\", 0, 0.0001]");
            logger.info("Derivative of 5: " + result);
            // Expected result should be 0
        } catch (Exception e) {
            logger.error("Error testing constant derivative: " + e.getMessage());
        }
    }

    /**
     * Tests the fraction calculation functionality
     */
    private static void testFractions() {
        logger.info("Testing fractions...");
        
        // Test simple fraction
        try {
            String result = engine.evaluate("[\"Fraction\", 1, 2]");
            logger.info("Fraction 1/2: " + result);
            // Expected result: \frac{1}{2}
        } catch (Exception e) {
            logger.error("Error testing simple fraction: " + e.getMessage());
        }

        // Test improper fraction
        try {
            String result = engine.evaluate("[\"Fraction\", 5, 2]");
            logger.info("Fraction 5/2: " + result);
            // Expected result: \frac{5}{2}
        } catch (Exception e) {
            logger.error("Error testing improper fraction: " + e.getMessage());
        }

        // Test negative fraction
        try {
            String result = engine.evaluate("[\"Fraction\", -3, 4]");
            logger.info("Fraction -3/4: " + result);
            // Expected result: \frac{-3}{4}
        } catch (Exception e) {
            logger.error("Error testing negative fraction: " + e.getMessage());
        }

        // Test error case: division by zero
        try {
            String result = engine.evaluate("[\"Fraction\", 1, 0]");
            logger.error("Should have thrown error for division by zero");
        } catch (Exception e) {
            logger.info("Correctly caught error for division by zero");
        }
    }

    /**
     * The main method which serves as the entry point for running test cases.
     */
    public static void main(String[] args) {
        logger.info("Running test cases...");
        
        // Run all test suites
        testIntegrals();
        testNthRoot();
        testDerivatives();
        testFractions();
        
        logger.info("All test cases completed.");
    }
}
