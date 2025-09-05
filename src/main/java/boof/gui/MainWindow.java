package boof.gui;

import boof.Boof;
import boof.ui.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Boof boof;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image boofImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Boof instance */
    public void setBoof(Boof d) {
        boof = d;
        // Show welcome message if dialogContainer is already initialized
        if (dialogContainer != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getBoofDialog(boof.getWelcomeMessage(), boofImage));
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Boof's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = boof.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBoofDialog(response, boofImage));
        userInput.clear();
        if (input.trim().equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }

    }
}
