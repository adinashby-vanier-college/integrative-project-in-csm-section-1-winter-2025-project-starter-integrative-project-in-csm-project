package edu.vanier;

import atlantafx.base.theme.CupertinoDark;
import edu.vanier.brickbybrick.allinonecalculator.tests.Driver;
import edu.vanier.brickbybrick.allinonecalculator.MainApp;

/**
 * The Launcher class serves as the entry point to the application.
 *
 * Currently, it launches the {@link MainApp} class's main method to start the
 * application. A commented-out line suggests the potential to launch another
 * class (e.g., {@link Driver}) in the future.
 */
public class Launcher {
    /**
     * The entry point of the application that invokes the
     * {@link MainApp#main(String[])} method to start the FX main application.
     *
     * @param args Command-line arguments passed to the application. These are
     * forwarded to {@link MainApp#main(String[])}.
     */
    public static void main(String[] args) {
        // Launch the main application.
        MainApp.main(args);
    }
}
