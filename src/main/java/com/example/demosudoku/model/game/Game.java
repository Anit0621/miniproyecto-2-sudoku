package com.example.demosudoku.model.game;

import com.example.demosudoku.controller.SudokuGameController;
import com.example.demosudoku.model.board.Board;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Represents the concrete implementation of the Sudoku game logic.
 * This class is responsible for setting up the game board UI and handling user input.
 */
public class Game extends GameAbstract {
    private SudokuGameController controller;

    /**
     * Constructs a new Game instance.
     *
     * @param boardGridpane The GridPane from the view where the Sudoku board will be rendered.
     */
    public Game(GridPane boardGridpane) {
        super(boardGridpane);
    }

    /**
     * Sets the controller for this game to enable message display.
     *
     * @param controller the SudokuGameController instance
     */
    public void setController(SudokuGameController controller) {
        this.controller = controller;
    }

    /**
     * Returns the Board instance associated with this game
     * @return the current board of the game
     */

    public Board getBoard(){
        return board;
    }

    /**
     * Returns the list of TextField elements that represent the cells of the board.
     * @return an ArrayList containing he board's number fields
     */

    public ArrayList<TextField> getNumberFields(){
        return numberFields;

    }



    /**
     * Starts the game by generating a board and creating UI components.
     */
    @Override
    public void startGame() {
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int number = board.getBoard().get(i).get(j);

                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);

                if (number != 0) {
                    textField.setText(String.valueOf(number));
                    textField.setEditable(false);
                    textField.setStyle("-fx-background-color: #d3d3d3;");
                } else {
                    textField.setText("");
                    textField.setEditable(true);
                }

                handleNumberField(textField, i, j);
                boardGridpane.add(textField, j, i);
                numberFields.add(textField);
            }
        }
    }

    /**
     * Attaches a key released event handler to a TextField cell.
     *
     * @param txt The TextField to which the handler will be attached.
     * @param row The row index of the cell in the board.
     * @param col The column index of the cell in the board.
     */
    private void handleNumberField(TextField txt, int row, int col) {
        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            if (input.length() > 0) {
                try {
                    int number = Integer.parseInt(input);
                    boolean isValid = board.isValid(row, col, number);

                    if (isValid) {

                        board.setCellValue(row, col, number);

                        String message = "✓ Numero " + number + " valido en la posicion.";
                        if (controller != null) {
                            controller.addMessage(message);
                        }

                        if (board.isComplete()){
                            if (controller != null){
                                controller.addMessage("¡Felicidades! Has completado el Sudoku.");
                                controller.showVictoryAlert();
                            }
                        }

                    } else {
                        String message = "✗ Numero " + number + " INVALIDO en la posicion.";
                        if (controller != null) {
                            controller.addMessage(message);
                        }
                        txt.setText("");
                    }

                } catch (NumberFormatException e) {
                    String message = "Error: '" + input + "' no es numero valido";
                    if (controller != null) {
                        controller.addMessage(message);
                    }
                    txt.setText("");
                }
            } else {
                board.setCellValue(row, col, 0);
            }
        });
    }
}