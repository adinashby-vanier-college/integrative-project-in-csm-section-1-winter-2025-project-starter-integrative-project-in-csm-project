package edu.vanier.brickbybrick.allinonecalculator.controllers;

import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The FMXL Controller class of the app's main (welcome) scene.
 */
public class MainSceneFXMLController {
    private final static Logger logger = LoggerFactory.getLogger(MainSceneFXMLController.class);

    @FXML
    private void initialize() {
        logger.info("Initializing MainSceneFXMLController...");
    }
}
