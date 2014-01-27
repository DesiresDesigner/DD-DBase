import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import DDDB.desiresdesigner.twitter.com.DDDB;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 1/27/14
 */


@RunWith(JUnit4.class)
public class DDDBTest {
    DDDB db;

    public DDDBTest() {
        db = new DDDB();
    }

    /*@Test
    public void addTest() throws IOException {
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            int res = db.addValue(key, value);
            assertEquals(res, key.hashCode());
        }
    }*/

    @Test
    public void getTest() throws IOException {
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            String res = db.getValue(key);
            assertEquals(res, value);
        }
    }
}
