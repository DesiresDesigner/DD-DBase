/**
 * @author desiresdesigner
 * @since 1/15/14
 */

import manager.desiresdesigner.twitter.com.Manager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ManagerTest {
    Manager testController;

    @Before
    public void before() {
        testController = new Manager();
    }

    /*@Test
    public void addingTest(){
        for (int i = 0; i < 100; i++){
            testController.addValue(String.valueOf(i), String.valueOf(i));
        }
        for (int i = 0; i < 100; i++){
            assertEquals(testController.getValue(String.valueOf(i)), String.valueOf(i));
        }
    }

    @Test
    public void writingOnRemoveTest() throws IOException {
        for (int i = 50; i < 100; i++){
            try{
                testController.deleteValue(String.valueOf(i));
            } catch (Exception e){
                System.out.println(i);
            }
            //assertEquals(testController.getValue(String.valueOf(i)), String.valueOf(i));
        }
        for (int i = 50; i < 100; i++){
            testController.addValue(String.valueOf(i), String.valueOf(i-20));
        }
        for (int i = 0; i < 50; i++){
            assertEquals(testController.getValue(String.valueOf(i)), String.valueOf(i));
        }
        for (int i = 50; i < 100; i++){
            assertEquals(testController.getValue(String.valueOf(i)), String.valueOf(i-20));
        }
    }*/

    @Test
    public void clearTest() throws IOException {
        for (int i = 50; i < 100; i++){
            assertEquals(testController.addValue(String.valueOf(i), String.valueOf(i-20)), 0);
        }
        //testController.clearDataStorage();
        for (int i = 50; i < 100; i++){
            assertEquals(testController.addValue(String.valueOf(i), String.valueOf(i-20)), 1);
        }
    }

    /*@Test
    public void getKeysTest(){
        System.out.println(testController.getKeys());
    }*/

}
