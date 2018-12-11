import controllers.AI;
import controllers.Board;
import org.junit.BeforeClass;
import org.junit.Test;
import structures.AIType;
import structures.Configuration;

import static org.junit.Assert.*;

public class AITest {

    private static AI aggressive;
    private static AI defensive;
    private static AI wild;
    private static AI dyn;

    @BeforeClass
    public static void Setup(){

        // create AI agents
        aggressive = new AI(AIType.AGG, false);
        defensive = new AI(AIType.DEF, false);
        wild = new AI(AIType.WILD, false);
        dyn = new AI(AIType.AGG, true);

    }

    @Test
    // tests to make sure the constructor works correctly
    public void TestConstructor(){

        assertTrue(aggressive.getType() == AIType.AGG);
        assertTrue(defensive.getType() == AIType.DEF);
        assertTrue(wild.getType() == AIType.WILD);
        assertTrue(dyn.getDynamic());
        assertFalse(aggressive.getDynamic());

    }

    @Test
    // tests to make sure that each AI selects the appropriate obvious holes
    public void TestBehaviour(){

        // an aggressive AI would chose hole 7 on their side
        // as it would lead to them collecting 14 korgols
        Configuration obvious = new Configuration("9,9,13,5,9,9,9,9,9,9,9,9,9,9,12,6,9,9//0,0");

        // the largest yields from choosing each hole, thus it should pick 12 or 15 (12 due to EARLIER IS BETTER clause)
        //Hole:9 : 0
        //Hole:10 : 10
        //Hole:11 : 10
        //Hole:12 : 14
        //Hole:13 : 6
        //Hole:14 : 10
        //Hole:15 : 14
        //Hole:16 : 10
        //Hole:17 : 10
        Board board = new Board(obvious);
        assertTrue(aggressive.evaluate(board.getHoles()) == board.getHoles().get(12));

        // selecting the first hole would leave the least amount of odd holes left on the AI's side of the board
        assertTrue(defensive.evaluate(board.getHoles()) == board.getHoles().get(9));

        // test to make the wild AI makes a move
        assertNotNull(wild.evaluate(board.getHoles()));

    }


}
