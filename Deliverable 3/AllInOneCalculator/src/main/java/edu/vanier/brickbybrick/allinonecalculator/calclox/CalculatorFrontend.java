package edu.vanier.brickbybrick.allinonecalculator.calclox;

import java.util.Map;

/**
 * This abstract class defines all the methods that needs to be implemented by the
 * class calling CalcLoxRunner to run a piece of code.
 * This contains all the necessary methods to handle the frontend and CalcLoxRunner interaction.
 * @author Qian Qian
 */
public abstract class CalculatorFrontend {
    public Map<String, Double> variables = null;

    /**
     * This method is called when the "output" statement is used in CalcLox,
     * and the result is ready to be displayed.
     * @param result the result to be displayed
     */
    public abstract void output(String result);
}
