package edu.vanier.brickbybrick.allinonecalculator.controllers;

import edu.vanier.brickbybrick.allinonecalculator.MainApp;
import edu.vanier.brickbybrick.allinonecalculator.logic.ArithmeticCalculatorLogic;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * The FMXL Controller class for the Arithmetic Calculator.
 */
public class ArithmeticCalculatorFXMLController {
    private final static Logger logger = LoggerFactory.getLogger(ArithmeticCalculatorFXMLController.class);
    private final static ArithmeticCalculatorLogic logic = new ArithmeticCalculatorLogic();

    private String currentExpression = "";
    private String currentMathJSONStr = "";
    private String currentResult = "";

    @FXML
    private WebView inputField;
    @FXML
    private Button squareButton;
    @FXML
    private Button radiantButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button sinButton;
    @FXML
    private Button cosButton;
    @FXML
    private Button tanButton;
    @FXML
    private Button rootButton;
    @FXML
    private Button xrootButton;
    @FXML
    private Button fracButton;
    //    @FXML
//    private Button limitButton;
    @FXML
    private Button derButton;
    @FXML
    private Button intButton;
    @FXML
    private Button computeButton;

    private WebEngine engine;

    @FXML
    private Button arithmeticModeSwitch;
    @FXML
    private Button graphingModeSwitch;
    @FXML
    private Button programmingModeSwitch;

    @FXML
    private VBox historyVBox;

    @FXML
    private void initialize() {
        logger.info("Initializing ArithmeticCalculatorFXMLController...");

        // Setting up the WebView for the input field.
        URL url = this.getClass().getResource("/web/cortexjs.html");
        assert url != null;
        engine = inputField.getEngine();
        engine.load(url.toExternalForm());
        inputField.setStyle("color-scheme: dark;");
        
        // Setup event listeners for the WebView.
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                logger.info("WebView loaded successfully");
                try {
                    JSObject window = (JSObject) engine.executeScript("window");
                    window.setMember("app", this);
                    engine.executeScript("""
                            mf.addEventListener('input', (evt) => {
                                app.updateExpression(evt.target.value);
                                app.updateMathJSONStr(JSON.stringify(window.ce.parse(evt.target.value).json));
                                app.updateResult(logic.calculate(JSON.stringify(window.ce.parse(evt.target.value).json)));
                            });
                            """);
                    logger.info("WebView event listeners setup complete");
                } catch (Exception e) {
                    logger.error("Error setting up WebView event listeners: " + e.getMessage(), e);
                }
            }
        });

        historyVBox.getChildren().clear();

        // Setup the keyboard event listeners.
        setupKeyboard();

        arithmeticModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.ARITHMETIC_CALCULATOR);
        });
        graphingModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.GRAPHING_CALCULATOR);
        });
        programmingModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.PROGRAMMING_MODE);
        });

        // Compute button implementation
        computeButton.setOnAction(event -> {
            try {
                logger.info("Compute button pressed");
                
                // Get the current expression
                String currentValue = (String) engine.executeScript("mf.getValue()");
                logger.info("Current expression: " + currentValue);
                
                // Get the MathJSON
                String mathJson = (String) engine.executeScript("JSON.stringify(window.ce.parse(mf.getValue()).json)");
                logger.info("MathJSON: " + mathJson);
                
                // Calculate the result using our logic
                String result = logic.calculate(mathJson);
                logger.info("Calculated result: " + result);
                
                // Update the display with the result
                String script = "mf.setValue('" + result + "')";
                logger.info("Executing script: " + script);
                engine.executeScript(script);
                
                // Create and add history item
                logger.info("Creating history item for expression: " + currentValue + ", result: " + result);
                VBox historyItem = logic.createHistoryItem(currentValue, result);
                historyVBox.getChildren().add(historyItem);
                
                // Update internal state
                currentExpression = currentValue;
                currentMathJSONStr = mathJson;
                currentResult = result;
                
                logger.info("Compute operation completed successfully");
            } catch (Exception e) {
                logger.error("Error in compute operation: " + e.getMessage(), e);
                String errorMessage = "Error: " + e.getMessage();
                engine.executeScript("mf.setValue('" + errorMessage + "')");
            }
        });

        // Clear button implementation
        clearButton.setOnAction(event -> {
            if (engine != null) {
                engine.executeScript("mf.setValue('')");
                currentExpression = "";
                currentMathJSONStr = "";
                currentResult = "";
            }
        });

        // Square button implementation
        squareButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + "^2";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Radiant button implementation
        radiantButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + " rad";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Sin button implementation
        sinButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + "sin()";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Cos button implementation
        cosButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + "cos()";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Tan button implementation
        tanButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + "tan()";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Square root button implementation
        rootButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                String newValue = currentValue + "\\\\sqrt{x}";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Nth root button implementation
        xrootButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                // Clear previous content and set a clear template with placeholders
                String newValue = "\\\\sqrt[n]{x}";
                engine.executeScript("mf.setValue('" + newValue + "')");
            }
        });

        // Fraction button implementation
        fracButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                // Clear previous content and set a clear template with placeholders
                String newValue = "\\frac{\\text{numerator}}{\\text{denominator}}";
                engine.executeScript("mf.setValue('" + newValue + "')");
                // After setting the fraction template, call the handler
                handleFraction();
            }
        });

        // Derivative button implementation
        derButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                // Clear previous content and set a clear template with placeholders
                String newValue = "\\frac{d}{dx}{\\text{function}}";
                engine.executeScript("mf.setValue('" + newValue + "')");
                // After setting the derivative template, call the handler
                handleDerivative();
            }
        });

        // Integral button implementation
        intButton.setOnAction(event -> {
            if (engine != null) {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                // Clear previous content and set a clear template with placeholders
                String newValue = "\\int_{\\text{lower bound}}^{\\text{upper bound}}{\\text{function}}";
                engine.executeScript("mf.setValue('" + newValue + "')");
                // After setting the integral template, call the handler
                handleIntegral();
            }
        });
    }

    //> Keyboard Event Handlers
    private void handleDelete(boolean forward) {
        if (engine != null) {
            try {
                String currentValue = (String) engine.executeScript("mf.getValue()");
                
                if (currentValue.isEmpty()) return;
                
                // Always remove the last character, regardless of forward/backward
                String newValue = currentValue.substring(0, currentValue.length() - 1);
                engine.executeScript("mf.setValue('" + newValue + "')");
                
                // Move cursor to end after deletion
                engine.executeScript("mf.setCaretPosition(" + newValue.length() + ")");
            } catch (Exception e) {
                logger.error("Error handling delete: " + e.getMessage());
            }
        }
    }

    private void handleMove(boolean next) {
        if (engine != null) {
            try {
                // Move to end of expression
                String currentValue = (String) engine.executeScript("mf.getValue()");
                engine.executeScript("mf.setCaretPosition(" + currentValue.length() + ")");
            } catch (Exception e) {
                logger.error("Error handling move: " + e.getMessage());
            }
        }
    }

    private void setupKeyboard() {
        logger.info("Setting up the keyboard...");
        inputField.setOnKeyPressed(event -> {
            logger.info("Key pressed. Code: " + event.getCode());
            switch (event.getCode()) {
                case BACK_SPACE, DELETE -> handleDelete(true); // Both keys remove last character
                case LEFT, RIGHT -> handleMove(true); // Both keys move to end
                case HOME -> {
                    if (engine != null) {
                        engine.executeScript("mf.setCaretPosition(0)");
                    }
                }
                case END -> {
                    if (engine != null) {
                        String currentValue = (String) engine.executeScript("mf.getValue()");
                        engine.executeScript("mf.setCaretPosition(" + currentValue.length() + ")");
                    }
                }
            }
        });
        logger.info("Input Field Keyboard Event Handlers setup complete.");
    }
    //< Keyboard Event Handlers

    //> WebView Event Handlers

    /**
     * This method is called by the WebView when the expression is updated.
     * <p>
     * Notes:
     * <br/>
     * - This method *must* be public for WebView to be able to call it.
     * <br/>
     * - This method *is being used* despite the IDE's warning that it is not (as it is not called directly by Java code).
     * </p>
     *
     * @param expression the new expression
     */
    public void updateExpression(String expression) {
        logger.info("Expression Updates: " + expression);
        currentExpression = expression;
    }

    /**
     * This method is called by the WebView when the expression is updated.
     *
     * @param mathJSONStr the new math JSON string
     */
    public void updateMathJSONStr(String mathJSONStr) {
        logger.info("Math JSON String Updates: " + mathJSONStr);
        currentMathJSONStr = mathJSONStr;
    }

    /**
     * This method is called by the WebView when the expression is updated.
     *
     * @param result the result of the expression
     */
    public void updateResult(String result) {
        logger.info("Result Updates: " + result);
        currentResult = result;
    }
    //< WebView Event Handlers

    /**
     * Handles the evaluation of integrals using Riemann sum approximation
     */
    private void handleIntegral() {
        try {
            String expression = (String) engine.executeScript("mf.getValue()");
            // Parse the integral expression using regex to extract components
            // Format: \int_{lower}^{upper}{function}
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\\\int_\\{(.*?)\\}\\^\\{(.*?)\\}\\{(.*?)\\}");
            java.util.regex.Matcher matcher = pattern.matcher(expression);
            
            if (matcher.find()) {
                String lowerBound = matcher.group(1).replace("\\text{lower bound}", "").trim();
                String upperBound = matcher.group(2).replace("\\text{upper bound}", "").trim();
                String function = matcher.group(3).replace("\\text{function}", "").trim();
                
                if (lowerBound.isEmpty() || upperBound.isEmpty() || function.isEmpty()) {
                    engine.executeScript("mf.setValue('Please fill in all fields: lower bound, upper bound, and function')");
                    return;
                }
                
                String variable = "x"; // Default variable
                
                // Create MathJSON for integral
                String mathJson = String.format(
                    "[\"Integral\", \"%s\", \"%s\", %s, %s]",
                    function, variable, lowerBound, upperBound
                );
                
                String result = logic.calculate(mathJson);
                engine.executeScript("mf.setValue('" + result + "')");
            }
        } catch (Exception e) {
            logger.error("Error evaluating integral: " + e.getMessage());
            engine.executeScript("mf.setValue('Error: " + e.getMessage() + "')");
        }
    }

    /**
     * Handles the evaluation of derivatives using central difference approximation
     */
    private void handleDerivative() {
        try {
            String expression = (String) engine.executeScript("mf.getValue()");
            // Parse the derivative expression using regex to extract components
            // Format: \frac{d}{dx}{function}
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\\\frac\\{d\\}\\{dx\\}\\{(.*?)\\}");
            java.util.regex.Matcher matcher = pattern.matcher(expression);
            
            if (matcher.find()) {
                String function = matcher.group(1).replace("\\text{function}", "").trim();
                
                if (function.isEmpty()) {
                    engine.executeScript("mf.setValue('Please enter a function to differentiate')");
                    return;
                }
                
                String variable = "x"; // Default variable
                double point = 0.0; // Default evaluation point
                double h = 0.0001; // Small step size for numerical differentiation
                
                // Create MathJSON for derivative
                String mathJson = String.format(
                    "[\"Derivative\", \"%s\", \"%s\", %f, %f]",
                    function, variable, point, h
                );
                
                String result = logic.calculate(mathJson);
                engine.executeScript("mf.setValue('" + result + "')");
            }
        } catch (Exception e) {
            logger.error("Error evaluating derivative: " + e.getMessage());
            engine.executeScript("mf.setValue('Error: " + e.getMessage() + "')");
        }
    }

    /**
     * Handles the evaluation of fractions
     */
    private void handleFraction() {
        try {
            String expression = (String) engine.executeScript("mf.getValue()");
            // Parse the fraction expression using regex to extract components
            // Format: \frac{numerator}{denominator}
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\\\frac\\{(.*?)\\}\\{(.*?)\\}");
            java.util.regex.Matcher matcher = pattern.matcher(expression);
            
            if (matcher.find()) {
                String numerator = matcher.group(1).replace("\\text{numerator}", "").trim();
                String denominator = matcher.group(2).replace("\\text{denominator}", "").trim();
                
                if (numerator.isEmpty() || denominator.isEmpty()) {
                    engine.executeScript("mf.setValue('Please fill in both numerator and denominator')");
                    return;
                }
                
                // Create MathJSON for fraction
                String mathJson = String.format(
                    "[\"Fraction\", %s, %s]",
                    numerator, denominator
                );
                
                String result = logic.calculate(mathJson);
                
                // Display the result as a fraction with a horizontal bar
                String displayResult = String.format("\\frac{%s}{%s}", numerator, denominator);
                engine.executeScript("mf.setValue('" + displayResult + "')");
            }
        } catch (Exception e) {
            logger.error("Error evaluating fraction: " + e.getMessage());
            engine.executeScript("mf.setValue('Error: " + e.getMessage() + "')");
        }
    }
}
