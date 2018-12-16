import org.junit.Before;
import org.junit.Test;
import structures.Hole;
import structures.Seat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HoleTest{

    //creates 4 new Hole objects
    private static Hole zero;
    private static Hole one;
    private static Hole two;
    private static Hole three;


    @Before
    public void Setup(){
        //initialises the holes
        zero = new Hole(0);
        one = new Hole(1);
        two = new Hole(2);
        three = new Hole(3);
    }

    @Test
    //Tests whether the initialisation was successful
    public void TestConstructor(){
        assertTrue(zero.getKorgols() == 0);
        assertTrue(one.getKorgols() == 1);
        assertTrue(three.getKorgols() == 3);
        assertFalse(zero.getKorgols() == 2);
        assertFalse(one.getKorgols() == 2);
        assertFalse(three.getKorgols() == 2);
    }

    @Test
    //Tests the getter and setter methods for korgols
    public void TestAddKorgols(){
        zero.addKorgol();
        assertTrue(zero.getKorgols() == 1);
        zero.addKorgols(3);
        assertTrue(zero.getKorgols() == 4);
        one.addKorgol();
        assertTrue(one.getKorgols() == 2);
        zero.addKorgols(8);
        assertTrue(zero.getKorgols() == 12);
    }

    @Test
    //Tests the boolean methods in the Hole class
    public void TestBooleans(){
        assertTrue(zero.isEmpty());
        assertFalse(one.isEmpty());
        assertFalse(three.isEmpty());
        assertTrue(two.isEven());
        assertFalse(three.isEven());
    }

    @Test
    //Test the methods associated with Seat in Holes
    public void TestSeats(){
        zero.setSeat(Seat.WHITE);
        assertTrue(zero.getSeat() == Seat.WHITE);
        one.setSeat(Seat.BLACK);
        assertTrue(one.getSeat() == Seat.BLACK);
    }
}
