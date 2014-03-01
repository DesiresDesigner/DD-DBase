import dataPartition.desiresdesigner.twitter.com.DataPartition;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */

@RunWith(JUnit4.class)
public class DataPartitionTest {

    Set <String> keys;

    public DataPartitionTest(){
        System.out.println("Constructor");
        keys = new HashSet();
        getKeys();
    }

    public void getKeys(){
        File file = new File("/home/desiresdesigner/ITMO/IASP/LR5/LastNames");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    keys.add(s);
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*@Test
    public void test() {
        for (String s : keys){
            System.out.println(s);
        }
    }*/

    /*@Test
    public void simpleHashTest() throws IOException {
        System.out.println("Simple hash");
        int[] arr = new int[1000];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        int shard4 = 0;
        int shard5 = 0;
        int shard6 = 0;
        int shard7 = 0;
        int shard8 = 0;
        int shard9 = 0;
        int shard10 = 0;

        int i = 0;
        for (String s : keys){
            arr[i] = DataPartition.simpleHash(s, 10);
            ++i;
        }
        i = 0;
        for (String s : keys){
            assertEquals(arr[i], DataPartition.simpleHash(s, 10));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
            else if (arr[i] == 3)
                ++shard4;
            else if (arr[i] == 4)
                ++shard5;
            else if (arr[i] == 5)
                ++shard6;
            else if (arr[i] == 6)
                ++shard7;
            else if (arr[i] == 7)
                ++shard8;
            else if (arr[i] == 8)
                ++shard9;
            else if (arr[i] == 9)
                ++shard10;
            ++i;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
        System.out.println("shard4: " + shard4);
        System.out.println("shard5: " + shard5);
        System.out.println("shard6: " + shard6);
        System.out.println("shard7: " + shard7);
        System.out.println("shard8: " + shard8);
        System.out.println("shard9: " + shard9);
        System.out.println("shard10: " + shard10);
    }*/

    @Test
    public void hashRingTest() throws IOException {
        DataPartition.setShardsAmount(0);
        System.out.println("Hash ring");
        int[] arr = new int[1000];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        int shard4 = 0;
        int shard5 = 0;
        int shard6 = 0;
        int shard7 = 0;
        int shard8 = 0;
        int shard9 = 0;
        int shard10 = 0;

        int i = 0;
        for (String key : keys){
            arr[i] = DataPartition.hashRing(key, 10);
            ++i;
        }
        i = 0;
        for (String key : keys){
            assertEquals(arr[i], DataPartition.hashRing(key, 10));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
            else if (arr[i] == 3)
                ++shard4;
            else if (arr[i] == 4)
                ++shard5;
            else if (arr[i] == 5)
                ++shard6;
            else if (arr[i] == 6)
                ++shard7;
            else if (arr[i] == 7)
                ++shard8;
            else if (arr[i] == 8)
                ++shard9;
            else if (arr[i] == 9)
                ++shard10;
            ++i;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
        System.out.println("shard4: " + shard4);
        System.out.println("shard5: " + shard5);
        System.out.println("shard6: " + shard6);
        System.out.println("shard7: " + shard7);
        System.out.println("shard8: " + shard8);
        System.out.println("shard9: " + shard9);
        System.out.println("shard10: " + shard10);
    }


    /*@Test
    public void hashMapTest() throws IOException, NoSuchAlgorithmException {
        DataPartition.setShardsAmount(0);
        System.out.println("Hash map");
        int[] arr = new int[1000];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        int shard4 = 0;
        int shard5 = 0;
        int shard6 = 0;
        int shard7 = 0;
        int shard8 = 0;
        int shard9 = 0;
        int shard10 = 0;

        int i = 0;
        for (String s : keys){
            arr[i] = DataPartition.hashMap(s, 10);
            ++i;
        }
        i = 0;
        for (String s : keys){
            assertEquals(arr[i], DataPartition.hashMap(s, 10));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
            else if (arr[i] == 3)
                ++shard4;
            else if (arr[i] == 4)
                ++shard5;
            else if (arr[i] == 5)
                ++shard6;
            else if (arr[i] == 6)
                ++shard7;
            else if (arr[i] == 7)
                ++shard8;
            else if (arr[i] == 8)
                ++shard9;
            else if (arr[i] == 9)
                ++shard10;
            ++i;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
        System.out.println("shard4: " + shard4);
        System.out.println("shard5: " + shard5);
        System.out.println("shard6: " + shard6);
        System.out.println("shard7: " + shard7);
        System.out.println("shard8: " + shard8);
        System.out.println("shard9: " + shard9);
        System.out.println("shard10: " + shard10);
    }*/
}