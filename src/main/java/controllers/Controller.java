package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import structures.*;

import java.util.ArrayList;

public class Controller {

    private Board board;
    private AI ai;
    private boolean inGame = false;
    private Configuration config;
    private int turn;

    @FXML
    private GridPane black, white;
    @FXML
    private Text whiteKazan, blackKazan;

    public void initialize() {
        inGame = true;
        board = new Board();
        ai = new AI(AIType.AGG, false);
        turn = 0;
        updateBoard();
    }

    void updateBoard() {
        ArrayList<Hole> holesList = board.getHoles();
        clearBoard();
        updateKazans();

        for (int i = 0; i < holesList.size(); i++) {
            Hole currentKorgol = holesList.get(i);
            int korgolValue = currentKorgol.getKorgols();
            Seat korgolSeat = currentKorgol.getSeat();

            Text korgolTextValue = new Text();
            korgolTextValue.setText(String.valueOf(korgolValue));
            korgolTextValue.getStyleClass().add("counter");
            StackPane korgolBox = new StackPane(korgolTextValue);
            korgolBox.getStyleClass().add(korgolSeat.toString().toLowerCase());

            if (korgolSeat == Seat.WHITE) {
                final int ii = i;
                korgolBox.setOnMousePressed(event -> nextMove(holesList.get(ii)));
            }

            if (i < 9) {
                white.add(korgolBox, (i * 2) + 1, 1);
            } else {
                black.add(korgolBox, ((i % 9 * 2) + 1), 1);
            }
        }
    }

    private void clearBoard() {
        black.getChildren().clear();
        white.getChildren().clear();
    }

    private void updateKazans() {
        whiteKazan.setText(String.valueOf(board.seatToKazan(Seat.WHITE).getKorgols()));
        blackKazan.setText(String.valueOf(board.seatToKazan(Seat.BLACK).getKorgols()));
    }

    private void nextMove(Hole hole) {
        if(turn % 2 == 0){
            turn++;
            playerMove(hole);
            updateBoard();
            AIMove();
            updateBoard();
            turn++;
        }
    }

    private void playerMove(Hole hole) {
        System.out.println(board.getHoles().indexOf(hole));
        if (inGame) {
            board.move(hole, Seat.WHITE);
        }
    }

    private void AIMove() {

        Hole hole = ai.evaluate(board.getHoles());
        System.out.println("AI+ " + board.getHoles().indexOf(hole));
        board.move(hole, Seat.BLACK);

    }

    public String add(String sav) {
        Configuration config = new Configuration(sav);
        if (config.isValid()) {
            return Configuration.saveConfigs();
        } else {
            return "Invalid configuration. Note that all Korgols on the board must add up to: " + Configuration.TOTALKORGOLS;
        }
    }

    public String load() {
        return Configuration.loadConfigs();
    }

}