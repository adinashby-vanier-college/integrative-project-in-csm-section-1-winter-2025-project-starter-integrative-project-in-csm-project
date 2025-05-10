package edu.vanier.brickbybrick.allinonecalculator.logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The logical operations for the Arithmetic Calculator.
 */
public class ArithmeticCalculatorLogic {
    private static long historyID = 1L;
    private final ComputeEngine computeEngine = new ComputeEngine();

    /**
     * Calculates the result of a mathematical expression.
     * @param expression The expression to calculate
     * @return The result of the calculation
     */
    public String calculate(String expression) {
        try {
            return computeEngine.evaluate(expression);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Creates a history item for the expression and its result.
     * @param expression The expression that was calculated
     * @param result The result of the calculation
     * @return A VBox containing the history item
     * @throws IOException If there is an error creating the history item
     */
    public VBox createHistoryItem(String expression, String result) throws IOException {
        VBox historyItem = new VBox();
        historyItem.setStyle("-fx-padding: 5px; -fx-spacing: 5px;");

        // Create expression display
        TeXFormula formula = new TeXFormula(expression);
        TeXIcon icon = formula.new TeXIconBuilder()
                .setStyle(TeXConstants.STYLE_DISPLAY)
                .setSize(20)
                .build();
        icon.setInsets(new Insets(5, 5, 5, 5));

        // Create image from LaTeX
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        JLabel jl = new JLabel();
        jl.setForeground(new Color(0, 0, 0));
        icon.paintIcon(jl, g2, 0, 0);

        // Ensure history_img directory exists
        File historyDir = new File("history_img");
        if (!historyDir.exists()) {
            historyDir.mkdirs();
        }

        // Save the image
        File file = new File("history_img/history_" + historyID++ + ".png");
        ImageIO.write(image, "png", file);

        // Create JavaFX components
        ImageView img = new ImageView(new Image(file.toURI().toString()));
        img.setFitHeight(32);
        img.setPreserveRatio(true);

        Text resultText = new Text("= " + result);
        resultText.setStyle("-fx-font-size: 14px; -fx-fill: #888;");

        // Add components to VBox
        historyItem.getChildren().addAll(img, resultText);
        historyItem.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 5px; -fx-spacing: 5px;");

        return historyItem;
    }
}
