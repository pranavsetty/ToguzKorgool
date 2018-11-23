import org.junit.Test;
import structures.Configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigurationTest {

    @Test
    // this inherently tests parsing as well
    public void TestConstructor(){

        String setup = "5,5,1,5,2,5,4,5,5,4,7,3,3,5,1,1,1,3//12,39";
        Configuration config = new Configuration(setup);

        // hole and kazan
        assertEquals(config.GetHoles().size(), 18);
        assertEquals(config.GetHoles().get(10).GetKorgols(), 7);
        assertEquals(config.GetWhiteKazan().GetKorgols() + config.GetBlackKazan().GetKorgols(), 51);

        // added to static list
        assertTrue(Configuration.configs.contains(config));

    }


}
