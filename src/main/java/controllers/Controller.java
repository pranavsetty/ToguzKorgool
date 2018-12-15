package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import structures.*;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    // TODO: Link configurations to game

    // member variables
    private Board board;
    private AI ai;
    private boolean inGame = false;
    private Configuration config;
    private int turn;

    private boolean hasWon = false;

    @FXML
    private GridPane black, white;
    @FXML
    private Text whiteKazan, blackKazan;

    // setup the game with a certain configuration
    // TODO: Link parameters to UI
    // public void initialize(Configuration selectedConfig, AIType aitype, boolean dynamic)
    // @param: a configuration (can be null), an ai type and whether the ai changes behaviour mid game
    // @return: void
    public void initialize() {

        // setup member variables
        board = new Board();
        ai = new AI(AIType.WILD, true);

        // start game
        inGame = true;
        turn = 0;
        updateBoard();
    }

    // update game board and allow for play
    // @param: void
    // @return: void
    private void updateBoard() {
        // update ui
        ArrayList<Hole> holesList = board.getHoles();
        clearBoard();
        updateKazans();

        // setup holes
        for (int i = 0; i < holesList.size(); i++) {
            Hole currentKorgol = holesList.get(i);
            int korgolValue = currentKorgol.getKorgols();
            Seat korgolSeat = currentKorgol.getSeat();

            Text korgolTextValue = new Text();
            korgolTextValue.setText(String.valueOf(korgolValue));
            korgolTextValue.getStyleClass().add("counter");
            StackPane korgolBox = new StackPane(korgolTextValue);
            korgolBox.getStyleClass().add(korgolSeat.toString().toLowerCase());

            if (korgolSeat == Seat.WHITE && korgolValue != 0) {
                final int ii = i;
                korgolBox.setOnMousePressed(event -> nextMove(holesList.get(ii)));
            }

            if (i < 9) {
                white.add(korgolBox, ((8 - i) * 2) + 1, 1);
            } else {
                black.add(korgolBox, ((i % 9 * 2) + 1), 1);
            }
        }

    }

    // reset board state (ui)
    // @param: void
    // @return: void
    private void clearBoard() {
        black.getChildren().clear();
        white.getChildren().clear();
    }

    // updates display values for kazans in game
    // @param: void
    // @return: void
    private void updateKazans() {
        whiteKazan.setText(String.valueOf(board.seatToKazan(Seat.WHITE).getKorgols()));
        blackKazan.setText(String.valueOf(board.seatToKazan(Seat.BLACK).getKorgols()));
    }

    // method that begins move sequence, evaluates the players move
    // then makes the ai move and evaluates that
    // @param: Hole to move from for player
    // @return: void
    private void nextMove(Hole hole) {
        playerMove(hole);
        updateBoard();
        checkWin();
        AIMove();
        updateBoard();
        checkWin();
    }

    // moves korgols from a given valid hole from the players
    // perspective
    // @param: hole to move the korgols from on the board
    // @return: void
    private void playerMove(Hole hole) {
        if (inGame) {
            board.move(hole, Seat.WHITE);
        }
    }

    // moves korgols from a hole that the AI determines via its algorithm
    // @param: void
    // @return: void
    private void AIMove() {
        Hole hole = ai.evaluate(board.getHoles());
        if (inGame) {
            board.move(hole, Seat.BLACK);
        }
    }

    // checks if a given string can be added as a configuration, if so it adds it and returns a
    // success message, else returns a failure message, for main menu
    // @param: a string to convert to a configuration
    // @return: a status message
    public String add(String sav) {
        Configuration config = new Configuration(sav);
        if (config.isValid()) {
            return Configuration.saveConfigs();
        } else {
            return "Invalid configuration. Note that all Korgols on the board must add up to: " + Configuration.TOTALKORGOLS;
        }
    }

    private void checkWin() {
        if(board.hasWon() != null && !hasWon){
            Seat winner = board.hasWon();
            hasWon = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over!");
            alert.setHeaderText(winner.toString() + " HAS WON");
            alert.setContentText("Would you like to continue?");

            ButtonType buttonTypeOne = new ButtonType("Start again");
            ButtonType buttonTypeTwo = new ButtonType("Quit");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                hasWon = false;
                initialize();
            }
            else {
                Platform.exit();
                System.exit(0);
            }
        }
    }


    // loads a given set of saved configurations into memory
    // @param: void
    // @return: a status message
    public String load() {
        return Configuration.loadConfigs();
    }

}