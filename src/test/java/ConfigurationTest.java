import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import structures.Configuration;

import java.io.File;

import static org.junit.Assert.*;

public class ConfigurationTest {

    private static Configuration one;
    private static Configuration two;
    private static Configuration three;
    private static Configuration four;

    @BeforeClass
    public static void Setup(){

        Configuration.Setup();
        Configuration.configs.clear();

        one = new Configuration("5,5,1,5,2,5,4,5,5,4,7,3,3,5,1,1,1,3//12,39");
        two = new Configuration("9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0,0");
        three = new Configuration("");
        four = new Configuration("9,9,9,9,1,9,9,7,9,9,4,9,9,9,9,9,2,8//7,16");

    }

    @Test
    // tests the constructor to make sure a Configuration is initialised properly
    public void TestConstructor(){

        // array for testing each hole korgol amount
        int[] values = new int[]{5,5,1,5,2,5,4,5,5,4,7,3,3,5,1,1,1,3};

        // hole testing
        for(int i = 0; i < one.GetHoles().size(); i++){
            assertEquals(one.GetHoles().get(i).GetKorgols(), values[i]);
        }

        // kazan testing
        assertEquals(one.GetWhiteKazan().GetKorgols() + one.GetBlackKazan().GetKorgols(), 51);

    }

    @Test
    // tests to make sure invalid configurations are not added to the static list
    public void TestVerification(){

        // valid korgol amount and valid string structure
        assertTrue(Configuration.configs.contains(two));
        // valid korgol amount and valid string structure
        assertTrue(Configuration.configs.contains(four));
        // invalid korgol amount and valid string structure
        assertFalse(Configuration.configs.contains(one));
        // invalid korgol amount and invalid string structure
        assertFalse(Configuration.configs.contains(three));

        // final collection
        assertEquals(Configuration.configs.size(), 2);

    }

    @Test
    // tests to make sure configurations are saved and loaded correctly
    public void TestSaveLoad(){

        Configuration.SaveConfigs();
        Configuration.configs.clear();

        // make sure clear is clean
        assertEquals(Configuration.configs.size(), 0);

        Configuration.LoadConfigs();

        // test for object equality
        // tests equals() override as well
        assertEquals(Configuration.configs.size(), 2);
        assertEquals(Configuration.configs.get(0).Parse(), "9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0,0");
        assertEquals(Configuration.configs.get(1).Parse(), "9,9,9,9,1,9,9,7,9,9,4,9,9,9,9,9,2,8//7,16");

    }

    @Test
    // tests to make sure a duplicate isn't added to the list
    public void TestDuplicate(){

        // make sure list is correct
        assertEquals(Configuration.configs.size(), 2);

        Configuration duplicate = new Configuration("9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0,0");

        // list should still be the same
        assertEquals(Configuration.configs.size(), 2);

    }

    @Test
    // tests to make sure only numbers are allowed
    public void TestNumberFormat(){

        Configuration holeError = new Configuration("9,9,*,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0,0");
        Configuration kazanError = new Configuration("9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//*,0");
        assertFalse(Configuration.configs.contains(holeError));
        assertFalse(Configuration.configs.contains(kazanError));

    }

    @Test
    // tests to make sure only strings of the right length are parsed
    public void TestStringLength(){

        Configuration holeShort = new Configuration("9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0,0");
        Configuration kazanShort = new Configuration("9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9//0");
        assertFalse(Configuration.configs.contains(holeShort));
        assertFalse(Configuration.configs.contains(kazanShort));

    }

    @AfterClass
    public static void TearDown(){

        File sav = new File("saves/tgkg.sav");
        sav.delete();
        File folder = new File("saves");
        folder.delete();

    }


}
