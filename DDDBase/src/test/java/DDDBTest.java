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

    @Test
    public void addTest() throws IOException {
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            int res = db.addValue(key, value);
            assertEquals(res, 0);
        }
    }

    @Test
    public void addSecondShardTest() throws IOException {
        db.addShard("localhost", 8000);
        for (int i = 100; i < 200; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            int res = db.addValue(key, value);
            assertEquals(res, 0);
        }
    }

    @Test
    public void getFrom2ShardsTest() throws IOException {
        db.addShard("localhost", 8000);
        for (int i = 0; i < 200; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            String res = db.getValue(key);
            assertEquals(res, value);
        }
    }

    /*@Test
    public void addThridShardTest() throws IOException {
        db.addShard("localhost", 8000);
        db.addShard("localhost", 8100);
        for (int i = 200; i < 300; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            int res = db.addValue(key, value);
            assertEquals(res, 0);
        }
    }

    @Test
    public void getFrom3ShardsTest() throws IOException {
        db.addShard("localhost", 8000);
        db.addShard("localhost", 8100);
        for (int i = 200; i < 300; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            String res = db.getValue(key);
            assertEquals(res, value);
        }
    }

    /*@Test
    public void getTest() throws IOException {
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + i;
            String res = db.getValue(key);
            assertEquals(res, value);
        }
    }*/

    /*@Test
    public void editTest() throws IOException {
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + (i + 100);
            db.editValue(key, value);
        }
        for (int i = 0; i < 100; i ++){
            String key = "Key" + i;
            String value = "_myValue" + (i + 100);
            String res = db.getValue(key);
            assertEquals(res, value);
        }
    }*/

}
