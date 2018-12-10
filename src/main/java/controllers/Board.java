package controllers;

import structures.Configuration;
import structures.Hole;
import structures.Kazan;

import java.util.ArrayList;

public class Board {
    private ArrayList<Hole> board = new ArrayList<Hole>(18);
    private Kazan playerOneKazan;
    private Kazan playerTwoKazan;

    Board() {
        for (int i = 0; i < 18; i++) {
            board.add(new Hole(9));
        }
    }

    Board(Configuration config) {
        playerOneKazan = config.getBlackKazan();
        playerTwoKazan = config.getWhiteKazan();
        for (int i = 0; i<18; i++) {
            Hole holeToAdd = config.getHoles().get(i);
            board.add(new Hole(holeToAdd.getKorgols()));
        }
    }

    public void move(Hole targetHole) {
        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = board.indexOf(targetHole);
        targetHole.clear();
        Hole finalHole = board.get((targetHoleIndex + ballsToMove - 1) % 18);
        for (int i = targetHoleIndex; i < (targetHoleIndex + ballsToMove); i++) {
            board.get(i % 18).addKorgol();
        }
        if (board.indexOf(targetHole) < 9 && board.indexOf(finalHole) >= 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols());
            finalHole.clear();
        }
        else if (board.indexOf(targetHole) >= 9 && board.indexOf(finalHole) < 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols());
            finalHole.clear();
        }
        else {}
    }

    // a 'shadow' move method that alters a given board to give you a
    // predicted state after said move, does not alter Kazans
    public static ArrayList<Hole> move(int holeIndex, ArrayList<Hole> shadowBoard, boolean all) {
        ArrayList<Hole> result = new ArrayList<Hole>(shadowBoard);
        Hole targetHole = result.get(holeIndex);

        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = result.indexOf(targetHole);
        targetHole.clear();
        Hole finalHole = result.get((targetHoleIndex + ballsToMove - 1) % 18);
        for (int i = targetHoleIndex; i < (targetHoleIndex + ballsToMove); i++) {
            result.get(i % 18).addKorgol();
        }
        if(all){
        } else {
            result.clear();
            result.add(finalHole);
        }
        return result;
    }



}
