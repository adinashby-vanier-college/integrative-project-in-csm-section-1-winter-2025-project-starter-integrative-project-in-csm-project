package edu.vanier.brickbybrick.allinonecalculator.controllers;

import edu.vanier.brickbybrick.allinonecalculator.MainApp;
import edu.vanier.brickbybrick.allinonecalculator.logic.ProgrammingModeLogic;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The FMXL Controller class for the scene for the calculator's programming mode.
 */
public class ProgrammingModeFXMLController {
    private final static Logger logger = LoggerFactory.getLogger(ProgrammingModeFXMLController.class);
    private final static ProgrammingModeLogic logic = new ProgrammingModeLogic();

    private final List<String> variables = new ArrayList<>();
    private final List<String> addedInstructions = new ArrayList<>();

    @FXML
    private Button addButton;

    @FXML
    private Button variablesButton;

    @FXML
    private Text variablesText;

    @FXML
    private VBox variablesVBox;
    @FXML
    private VBox secondVBox;
    @FXML
    private VBox instructionsVBox;

    @FXML
    private Button arithmeticModeSwitch;
    @FXML
    private Button graphingModeSwitch;
    @FXML
    private Button programmingModeSwitch;

    @FXML
    private void initialize() {
        logger.info("Initializing CalculatorProgrammingFXMLController...");

        variablesVBox.setAlignment(Pos.TOP_CENTER);
        variablesVBox.setSpacing(10);

        arithmeticModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.ARITHMETIC_CALCULATOR);
        });
        graphingModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.GRAPHING_CALCULATOR);
        });
        programmingModeSwitch.setOnAction(event -> {
            MainApp.switchScene(MainApp.PROGRAMMING_MODE);
        });

        addButton.setOnAction(event -> {
            variablesVBox.getChildren().clear();
            variablesVBox.getChildren().add(variablesText);

            variablesText.setText("Block Storage");

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER); 

            Text text1 = new Text("If condition then,");
            text1.setStyle("-fx-font-size: 20px;");
            vBox.getChildren().add(text1);
            Text text2 = new Text("Else then,");
            text2.setStyle("-fx-font-size: 20px;");
            vBox.getChildren().add(text2);
            Text text3 = new Text("While condition do,");
            text3.setStyle("-fx-font-size: 20px;");
            vBox.getChildren().add(text3);
            Text text4 = new Text("When clicked do,");
            text4.setStyle("-fx-font-size: 20px;");
            vBox.getChildren().add(text4);

            text1.setOnMouseClicked(event2 -> {
                System.out.println("Clicked on If condition");
                VBox vBox2 = new VBox();
                vBox2.getChildren().add(new Text("If condition"));
                vBox2.getChildren().add(new TextField("Enter condition"));
                vBox2.setPadding(new Insets(5, 0, 5, 0));
                instructionsVBox.getChildren().add(vBox2);
            });

            text2.setOnMouseClicked(event2 -> {
                System.out.println("Clicked on else condition");
                VBox vBox2 = new VBox();
                vBox2.getChildren().add(new Text("Else condition"));
                vBox2.getChildren().add(new TextField("Enter condition"));
                vBox2.setPadding(new Insets(5, 0, 5, 0));
                instructionsVBox.getChildren().add(vBox2);
            });

            text3.setOnMouseClicked(event2 -> {
                System.out.println("Clicked on While condition");
                VBox vBox2 = new VBox();
                vBox2.getChildren().add(new Text("While condition"));
                vBox2.getChildren().add(new TextField("Enter condition"));
                vBox2.setPadding(new Insets(5, 0, 5, 0));
                instructionsVBox.getChildren().add(vBox2);
            });

            text4.setOnMouseClicked(event2 -> {
                System.out.println("clicked on When clicked");
                VBox vBox2 = new VBox();
                vBox2.getChildren().add(new Text("When clicked"));
                vBox2.getChildren().add(new TextField("Enter event"));
                vBox2.setPadding(new Insets(5, 0, 5, 0));
                instructionsVBox.getChildren().add(vBox2);
            });

            vBox.setAlignment(Pos.CENTER); 
            vBox.setPadding(new Insets(10));

            Region spacerTop = new Region();
            spacerTop.setMinHeight(10);
            VBox.setVgrow(spacerTop, Priority.ALWAYS);
            variablesVBox.getChildren().add(spacerTop);

            variablesVBox.getChildren().add(vBox);

            Region spacerBottom = new Region();
            spacerBottom.setMinHeight(10);
            VBox.setVgrow(spacerBottom, Priority.ALWAYS);
            variablesVBox.getChildren().add(spacerBottom);

            variablesButton.setText("Click to leave");
            Region spacerBeforeButton = new Region();
            spacerBeforeButton.setMinHeight(10);
            VBox.setVgrow(spacerBeforeButton, Priority.ALWAYS);
            variablesVBox.getChildren().add(spacerBeforeButton);
            variablesVBox.getChildren().add(variablesButton);

            variablesButton.setOnAction(event2 -> {
                variablesVBox.getChildren().clear();
                variablesVBox.getChildren().add(variablesText);

                variablesText.setText("Variables");

                Region topSpacer = new Region();
                topSpacer.setMinHeight(10);
                VBox.setVgrow(topSpacer, Priority.ALWAYS);
                variablesVBox.getChildren().add(topSpacer);

                variablesVBox.getChildren().add(secondVBox);

                Region bottomSpacer = new Region();
                bottomSpacer.setMinHeight(10);
                VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
                variablesVBox.getChildren().add(bottomSpacer);

                variablesButton.setText("Click to Add");
                variablesVBox.getChildren().add(variablesButton);
            });
        });
    }

}
