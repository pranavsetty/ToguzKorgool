package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AI {

    // the weightings to decide how much weight a given factor has in the AI's choice of hole
    public static double ODDWEIGHT = 1.0;
    public static double TUZWEIGHT = 3.0;
    public static double MOODCHANGEWEIGHT = 0.3;

    // the type of AI it is, defines its behaviour: aggressive, defensive or wild
    private  AIType type;
    // assuming seat of AI is always black
    private Seat seat = Seat.BLACK;
    // whether they can change their playstyle mid game
    private boolean dynamic;
    // AI random
    private Random r;

    // constructor
    // @param: type of AI and whether or not it can change mid game
    // @return: AI
    public AI(AIType type, boolean dynamic){

        this.type = type;
        this.seat = seat;
        this.dynamic = dynamic;
        r = new Random();

    }

    // evaluates the board and picks a 'decent' hole
    // @param: the current state of the board
    // @return: an ideal Hole to move korgols from
    public Hole evaluate(ArrayList<Hole> board){

        if(dynamic) {
            // can randomly change the AI's mood if dyanmic
            moodChange();
        }

        switch(type) {

            case DEF:

                // find an ideal set of defensive choices using a list that records the number of unideal
                // results from using each hole, an undeal result here is defined as
                // a result the opposing player can take advantage of
                ArrayList<HoleWeight> def = new ArrayList<>();

                // get all possible threats from each of the AI's holes
                for (Hole hole : board) {
                    if (hole.getSeat() == seat && !hole.isEmpty()) {
                        def.add(new HoleWeight(hole, threats(hole, board)));
                    }
                }

                // iterates assuming that moving a later Hole is always better than moving an earlier hole
                HoleWeight idealDef = null;
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
                return board.get(r.nextInt(board.size()));

            case AGG:

                // find an ideal set of offensive choices using a list that records the number of ideal
                // results from using each hole, an ideal result here is defined as
                // a result that yields the most possible korgols
                ArrayList<HoleWeight> agg = new ArrayList<>();

                // get all possible opportunities from each of the AI's holes
                for (Hole hole : board) {
                    if (hole.getSeat() == seat && !hole.isEmpty()) {
                        agg.add(new HoleWeight(hole, opportunities(hole, board)));
                    }
                }

                // iterates assuming that moving an earlier Hole is always better than moving a later hole
                HoleWeight idealAgg = null;
                Iterator ita = agg.iterator();
                while (ita.hasNext()) {
                    HoleWeight hw = (HoleWeight)ita.next();
                    if (idealAgg == null || hw.getWeight() > idealAgg.getWeight()) {
                        idealAgg = hw;
                    }
                }
                return idealAgg.getHole();

        }

        // AI not setup correctly
        return null;

    }

    // method to change the type of a given AI to another random type (can be the same)
    // @param: void
    // @return: void
    private void moodChange(){

        double choice = r.nextDouble();
        if(choice <= MOODCHANGEWEIGHT){
            type = AIType.getRandom();
        }

    }

    // method to find the number of threats (number of odd holes on an AI's side of the board)
    // @param: a potential target hole to move from and state of the holes
    // @return: the number of threats for that given choice
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

    // method to find the largest opportunity (the choice of hole that would yield the greatest pickup)
    // @param: a potential target hole to move from and state of the holes
    // @return: the potential pickup from selecting said hole
    private int opportunities(Hole target, ArrayList<Hole> board){

        ArrayList<Hole> futureLastHole = Board.move(board.indexOf(target), board, false);
        int opp = 0;
        if(futureLastHole.get(0).isEven() && futureLastHole.get(0).getSeat() != seat){
            opp = futureLastHole.get(0).getKorgols();
        }
        return opp;

    }

    // getters
    public AIType getType(){return type;}
    public boolean getDynamic(){return dynamic;}

}
