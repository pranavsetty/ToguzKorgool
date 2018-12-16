package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import structures.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    // TODO: Link configurations to game

    // member variables
    private Board board;
    private AI ai;
    private boolean inGame = false;
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
        if(Settings.config == null){
            board = new Board();
        } else {
            board = new Board(Settings.config);
        }

        if(Settings.aitype == null){
            ai = new AI(AIType.WILD, true);
        } else {
            ai = new AI(Settings.aitype, true);
        }

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
        if(checkWin()){
            return;
        }
        AIMove();
        updateBoard();
        if(checkWin()){
            return;
        }
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

    private boolean checkWin() {
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
                inGame = false;
                hasWon = false;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/init.fxml"));
                try {
                    Parent root = loader.load();
                    Scene menu = new Scene(root, 800, 600);
                    Stage window = (Stage)black.getScene().getWindow();
                    window.setScene(menu);
                } catch (IOException e){

                }
                return true;
            }
            else {
                Platform.exit();
                System.exit(0);
                return true;
            }
        } else {

            boolean drawWhite = true;
            boolean drawBlack = true;
            for(Hole h : board.getHoles()){

                if(h.getSeat() == Seat.WHITE && h.getKorgols() != 0){
                    drawWhite = false;
                }

                if(h.getSeat() == Seat.BLACK && h.getKorgols() != 0){
                    drawBlack = false;
                }

            }

            if(drawBlack || drawWhite){

                hasWon = true;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game Over!");
                alert.setHeaderText("DRAW!");
                alert.setContentText("Would you like to continue?");

                ButtonType buttonTypeOne = new ButtonType("Start again");
                ButtonType buttonTypeTwo = new ButtonType("Quit");

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    inGame = false;
                    hasWon = false;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/init.fxml"));
                    try {
                        Parent root = loader.load();
                        Scene menu = new Scene(root, 800, 600);
                        Stage window = (Stage)black.getScene().getWindow();
                        window.setScene(menu);
                    } catch (IOException e){

                    }
                    return true;
                }
                else {
                    Platform.exit();
                    System.exit(0);
                    return true;
                }

            }

        }
        return false;
    }

}