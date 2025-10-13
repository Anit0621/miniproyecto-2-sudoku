package com.example.demosudoku.controller;

import com.example.demosudoku.model.game.Game;
import com.example.demosudoku.model.user.User;
import com.example.demosudoku.utils.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku game view (sudoku-game-view.fxml).
 * This class is responsible for initializing and managing the game board's UI.
 */
public class SudokuGameController implements Initializable {

    @FXML
    private GridPane boardGridPane;

    @FXML
    private TextArea messagesTextArea;

    private Game game;
    private User user;
    private AlertBox alertBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertBox = new AlertBox();
        game = new Game(boardGridPane);
        game.setController(this);
        game.startGame();
        setupCellInputHandling();
        setupMouseHandling();
        setupMessagesArea();
    }

    /**
     * Sets up the messages text area with initial content.
     */
    private void setupMessagesArea() {
        messagesTextArea.setText("Bienvenido al Sudoku!\n");
        messagesTextArea.appendText("Escribe números del 1 al 6.\n");
    }

    /**
     * Adds a message to the messages text area.
     *
     * @param message the message to display
     */
    public void addMessage(String message) {
        messagesTextArea.appendText(message + "\n");
        messagesTextArea.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * Sets up input handling for all cells in the Sudoku board.
     */
    private void setupCellInputHandling() {
        boardGridPane.getChildren().forEach(node -> {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                setupTextFieldFilter(textField);
            }
        });
    }

    /**
     * Sets up a filter for a TextField to only allow numbers 1-6 and single digits.
     *
     * @param textField the TextField to set up the filter for
     */
    private void setupTextFieldFilter(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();

            if (!input.matches("[1-6]")) {
                event.consume();
                alertBox.showAlert("Entrada inválida", "Solo se permiten números del 1 al 6", Alert.AlertType.WARNING);
                return;
            }

            if (textField.getText().length() >= 1) {
                event.consume();
                alertBox.showAlert("Entrada inválida", "Solo se permite un dígito por celda", Alert.AlertType.WARNING);
                return;
            }
        });

        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                textField.setText("");
                event.consume();
            }
        });
    }

    /**
     * Sets up mouse handling for cell selection.
     */
    private void setupMouseHandling() {
        boardGridPane.getChildren().forEach(node -> {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                setupMouseSelection(textField);
            }
        });
    }

    /**
     * Sets up mouse selection for a TextField.
     *
     * @param textField the TextField to set up mouse selection for
     */
    private void setupMouseSelection(TextField textField) {
        textField.setOnMouseEntered(event -> {
            if (textField.isEditable()) {
                textField.setStyle("-fx-background-color: #f0f0f0;");
            }
        });

        textField.setOnMouseExited(event -> {
            if (textField.isEditable()) {
                textField.setStyle("-fx-background-color: white;");
            }
        });

        textField.setOnMouseClicked(event -> {
            if (textField.isEditable()) {
                textField.selectAll();
            }
        });
    }

    /**
     * Sets the user for the current game session.
     *
     * @param user The user object containing player information
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            addMessage("Jugador: " + user.getNickname());
        }
    }
}