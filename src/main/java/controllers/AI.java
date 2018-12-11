package controllers;

import structures.*;
import java.util.*;

public class AI {

    // the weightings to decide how much weight a given factor has in the AI's choice of hole
    public static double ODDWEIGHT = 1.0;
    public static double TUZWEIGHT = 3.0;
    public static double MOODCHANGEWEIGHT = 0.3;

    // the type of AI it is, defines its behaviour: aggressive, defensive or wild
    private  AIType type;
    // whether it is playing on white or black
    private Seat seat = Seat.BLACK;
    // whether they can change their playstyle mid game
    private boolean dynamic;

    // evaluates the board and picks a 'decent' hole
    // @param: the current state of the board
    // @return: an ideal Hole to move korgols from
    public Hole evaluate(ArrayList<Hole> board){

        if(dynamic) {
            // can randomly change the AI's mood if dyanmic
            moodChange();
        }

        // defensive AI tries first to reduce the number of holes on their side of the board
        // that can provide positive benefits to the player
        // first looking to elimate any nearTuz Holes and then any Holes with odd Korgols

        switch(type) {
            case DEF:
                // find an ideal set of defensive choices using a hashmap that records the number of unideal
                // results from using each hole, an undeal result here is defined as
                // a result the opposing player can take advantage of
                ArrayList<HoleWeight> def = new ArrayList<>();
                System.out.println(board.size());
                for (Hole hole : board) {
                    if (hole.getSeat() == seat) {
                        def.add(new HoleWeight(hole, threats(hole, new ArrayList<>(board))));
                    }
                }
                for(HoleWeight we : def){
                    System.out.println("Hole:" + board.indexOf(we.getHole()) + " : " + we.getWeight());
                }
                HoleWeight idealDef = null;
                // iterates assuming that moving a later Hole is always better than moving an earlier hole
                Iterator itd = def.iterator();
                while (itd.hasNext()) {
                    HoleWeight hw = (HoleWeight)itd.next();
                    if (idealDef == null || hw.getWeight() <= idealDef.getWeight()) {
                        idealDef = hw;
                    }
                }
                return idealDef.getHole();
            case WILD:
                // completely random
                Random r = new Random();
                return board.get(r.nextInt(board.size()));
            case AGG:
                // find an ideal set of offensive choices using a hashmap that records the number of ideal
                // results from using each hole, an ideal result here is defined as
                // a result that yields the most possible korgols
                ArrayList<HoleWeight> agg = new ArrayList<>();
                System.out.println(board.size());
                for (Hole hole : board) {
                    if (hole.getSeat() == seat) {
                        agg.add(new HoleWeight(hole, opportunities(hole, new ArrayList<>(board))));
                    }
                }
                for(HoleWeight we : agg){
                    System.out.println("Hole:" + board.indexOf(we.getHole()) + " : " + we.getWeight());
                }
                HoleWeight idealAgg = null;
                // iterates assuming that moving an earlier Hole is always better than moving a later hole
                Iterator ita = agg.iterator();
                while (ita.hasNext()) {
                    HoleWeight hw = (HoleWeight)ita.next();
                    if (idealAgg == null || hw.getWeight() > idealAgg.getWeight()) {
                        idealAgg = hw;
                    }
                }
                System.out.println(idealAgg.getHole().getKorgols());
                System.out.println(board.indexOf(idealAgg.getHole()));
                return idealAgg.getHole();
        }

        // AI not setup correctly
        return null;

    }

    public AI(AIType type, boolean dynamic){

        this.type = type;
        this.seat = seat;
        this.dynamic = dynamic;

    }

    private void moodChange(){

        Random r = new Random();
        double choice = r.nextDouble();
        if(choice <= MOODCHANGEWEIGHT){
            type = AIType.getRandom();
        }

    }


    private int threats(Hole target, ArrayList<Hole> board){

        ArrayList<Hole> futureBoard = Board.move(board.indexOf(target), board, true);
        int threatCount = 0;
        for(Hole hole : futureBoard){
            if(hole.getSeat() == seat){
                if(!hole.isEven()){
                    threatCount += 1 * ODDWEIGHT;
                } else if(hole.getKorgols() == 2){
                    threatCount += 1 * TUZWEIGHT;
                }
            }
        }
        return threatCount;

    }

    private int opportunities(Hole target, ArrayList<Hole> board){

        ArrayList<Hole> futureLastHole = Board.move(board.indexOf(target), new ArrayList<>(board), false);
        int opp = 0;
        if(futureLastHole.get(0).isEven() && futureLastHole.get(0).getSeat() != seat){
            opp = futureLastHole.get(0).getKorgols();
        }
        //System.out.println(opp);
        return opp;

    }

    // getters
    public AIType getType(){return type;}
    public boolean getDynamic(){return dynamic;}

}
