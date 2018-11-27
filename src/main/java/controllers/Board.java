package controllers;

import structures.*;

public class Board {
    private arrayList<Hole> board = new arrayList(18);
    private Kazan playerOneKazan;
    private Kazan playerTwoKazan;

    Board() {
        for (int i = 0; i < 18; i++) {
            board.add(new Hole(9));
        }
    }

    public void move(Hole targetHole) {
        int ballsToMove = targetHole.getKorgols();
        int targetHoleIndex = board.indexOf(targetHole);
        targetHole.clear()
        Hole finalHole = board.get((targetHoleIndex + ballsToMove - 1) % 18);
        for (int i = targetHoleIndex; i < (targetHoleIndex + ballsToMove); i++) {
            board.get(i % 18).addKorgol();
        }
        if (board.indexOf(targetHole) < 9 && board.indexOf(finalHole) >= 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols);
            finalHole.clear();
        }
        else if (board.indexOf(targetHole) >= 9 && board.indexOf(finalHole) < 9 && finalHole.isEven()) {
            playerOneKazan.addKorgols(finalHole.getKorgols);
            finalHole.clear();
        }
        else {}
    }



}
