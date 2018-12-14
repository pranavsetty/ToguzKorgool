import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import structures.Kazan;

import static org.junit.Assert.*;

public class HoleTest{

    //creates 4 new Hole objects
    private static Kazan one;
    private static Kazan two;


    @Before
    public static void Setup(){
        //initialises the kazans
        one = new Kazan(0);
        two = new Kazan(80);
    }

    @Test
    //Tests whether the initialisation was successful
    public void TestConstructor(){
        assertTrue(one.getKorgols() == 0);
        assertTrue(two.getKorgols() == 80);
    }

    @Test
    //Tests the getter and mutator methods for korgols
    public void TestAddKorgols(){
        one.addKorgol();
        assertTrue(one.getKorgols() == 1);
        one.addKorgols(20);
        assertTrue(zero.getKorgols() == 21);
        two.addKorgol();
        assertTrue(two.getKorgols() == 81);
        two.clearKazan();
        assertTrue(two.getKorgols() == 0);
    }

}
