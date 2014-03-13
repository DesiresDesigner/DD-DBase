import manager.desiresdesigner.twitter.com.HashMapManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 3/13/14
 */

@RunWith(JUnit4.class)
public class HashMapManagerTest {
    HashMapManager manager = new HashMapManager();

    @Test
    public void operationsTest() throws IOException {
        for (int i = 0; i < 100; ++i){
            assertEquals(manager.addValue(i + "key", i + "value"), 0);
        }

        for (int i = 0; i < 100; ++i){
            assertEquals(manager.editValue(i + "key", (i + 100) + "value"), 0);
        }

        for (int i = 0; i < 100; ++i){
            assertEquals(manager.getValue(i + "key"), (i + 100) + "value");
        }

        for (int i = 0; i < 100; ++i){
            assertEquals(manager.deleteValue(i + "key"), 0);
        }
    }




}
