import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

/**          c1
 * @author desiresdesigner
 * @since 12/27/13
 */

@RunWith(JUnit4.class)
public class HelloWorldsTest {

    @Test
    public void stubTest(){
        assertEquals("Hello, Worlds!", "Hello, Worlds!");
    }
}
