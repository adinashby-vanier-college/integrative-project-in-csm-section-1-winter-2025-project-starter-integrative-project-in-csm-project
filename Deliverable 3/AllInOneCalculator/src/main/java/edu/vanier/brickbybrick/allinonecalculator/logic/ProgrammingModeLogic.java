package edu.vanier.brickbybrick.allinonecalculator.logic;

import edu.vanier.brickbybrick.allinonecalculator.calclox.CalcLoxRunner;

import java.util.ArrayList;

/**
 * This class contains the logic for the programming mode of the calculator.
 */
public class ProgrammingModeLogic {
    private final ComputeEngine computeEngine = new ComputeEngine();
    private final CalcLoxRunner calcLoxRunner = new CalcLoxRunner();

    private ArrayList<String> instructions = new ArrayList<>();

    public String generateCode() {
        return null;
    }

    public void addInstruction(String instruction) {
    }
}
