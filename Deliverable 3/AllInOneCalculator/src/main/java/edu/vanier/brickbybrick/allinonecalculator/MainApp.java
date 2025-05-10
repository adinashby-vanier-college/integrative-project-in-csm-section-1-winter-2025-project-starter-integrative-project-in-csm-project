package edu.vanier.brickbybrick.allinonecalculator;

import atlantafx.base.theme.CupertinoDark;
import edu.vanier.brickbybrick.allinonecalculator.controllers.*;
import edu.vanier.brickbybrick.allinonecalculator.helpers.FxUIHelper;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {
    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);

    // The FXML file name of the login scene
    public static final String LOGIN_SCENE = "login_layout";
    // The FXML file name of the main (welcome) scene of the application.
    public static final String MAIN_SCENE = "main_scene_layout";
    // The FXML file name of the arithmetic calculator.
    public static final String ARITHMETIC_CALCULATOR = "arithmetic_calculator_layout";
    // The FXML file name of the graphing calculator.
    public static final String GRAPHING_CALCULATOR = "graphing_calculator_layout";
    // The FXML file name of the calculator's programming mode.
    public static final String PROGRAMMING_MODE = "programming_mode_layout";

    private static Scene scene;
    private static SceneController sceneController;

    private static LoginFXMLController loginFXMLController;
    private static MainSceneFXMLController mainSceneController;
    private static ArithmeticCalculatorFXMLController arithmeticCalculatorController;
    private static GraphingCalculatorFXMLController graphingCalculatorController;
    private static ProgrammingModeFXMLController programmingModeController;

    @Override
    public void start(Stage primaryStage) {
        // Set the JavaFX Theme
        MainApp.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        // Set the primary stage.
        try {
            logger.info("Bootstrapping the application...");
            // Load the login scene first
            loginFXMLController = new LoginFXMLController();
            Parent root = FxUIHelper.loadFXML(LOGIN_SCENE, loginFXMLController);
            scene = new Scene(root, 800, 500);
            // Add the primary scene to the scene-switching controller.
            sceneController = new SceneController(scene);
            sceneController.addScene(LOGIN_SCENE, root);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.setTitle("All In One Calculator, made by Brick by Brick");
            // Always show the application on top of other windows upon startup.

            primaryStage.setMinHeight(525);
            primaryStage.setMinWidth(800);

            primaryStage.setResizable(true);

            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        // TODO: Add any cleanup/teardown/stopping operations here.
    }

    /**
     * Switches between scenes based on the provided FXML file name. This method
     * checks the type of scene (primary or secondary) and either activates an
     * existing scene or loads the specified FXML scene for the first time and
     * adds it to the scene controller.
     *
     * @param fxmlFileName the name of the FXML file that represents the scene
     *  to switch to.
     */
    public static void switchScene(String fxmlFileName) {
        try {
            switch (fxmlFileName) {
                case LOGIN_SCENE -> {
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        loginFXMLController = new LoginFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, loginFXMLController);
                        sceneController.addScene(LOGIN_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                }
                case MAIN_SCENE -> {
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        mainSceneController = new MainSceneFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, mainSceneController);
                        sceneController.addScene(MAIN_SCENE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                }
                case ARITHMETIC_CALCULATOR -> {
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        arithmeticCalculatorController = new ArithmeticCalculatorFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, arithmeticCalculatorController);
                        sceneController.addScene(ARITHMETIC_CALCULATOR, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                }
                case GRAPHING_CALCULATOR -> {
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        graphingCalculatorController = new GraphingCalculatorFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, graphingCalculatorController);
                        sceneController.addScene(GRAPHING_CALCULATOR, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                }
                case PROGRAMMING_MODE -> {
                    if (!sceneController.sceneExists(fxmlFileName)) {
                        programmingModeController = new ProgrammingModeFXMLController();
                        Parent root = FxUIHelper.loadFXML(fxmlFileName, programmingModeController);
                        sceneController.addScene(PROGRAMMING_MODE, root);
                    }
                    sceneController.activateScene(fxmlFileName);
                }
                default -> logger.error("The specified FXML file name does not match any of the registered scenes.");
            }
            // It is also possible register or activate additional scenes here,
            // based on the logic used in the above if-else block.
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
