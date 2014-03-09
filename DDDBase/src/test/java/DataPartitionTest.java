import dataPartition.desiresdesigner.twitter.com.DataPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */

@RunWith(JUnit4.class)
public class DataPartitionTest {

    //Set <String> keys;

    public DataPartitionTest(){
        System.out.println("Constructor");
        //keys = new HashSet();
        //getKeys();
    }

    /*public void getKeys(){
        File file = new File("/home/desiresdesigner/ITMO/IASP/LR5/GirlNames");
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
    } */

    /*@Test
    public void test() {
        for (String s : keys){
            System.out.println(s);
        }
    }*/

    @Test
    public void simpleHashTest() throws IOException {
        System.out.println("Simple hash");
        for (int i = 68; i < 91; i += 4){
            String tmp = String.valueOf(new char[]{(char) 65, (char) 80, (char)i});
            System.out.println(tmp + ": " + tmp.hashCode()%4 + " - " + "65 80 " + i);
        }
        Map <String, Integer> keys = new HashMap();
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

        for (int i = 0; i < 100; i++){
            String key = UUID.randomUUID().toString();
            int shard_number = DataPartition.simpleHash(key, 10);
            keys.put(key, shard_number);
        }
        Set <String> keySet = keys.keySet();
        for (String key : keySet){
            int shard_number = keys.get(key);
            assertEquals(shard_number, DataPartition.simpleHash(key, 10));
            if (shard_number == 0)
                ++shard1;
            else if (shard_number == 1)
                ++shard2;
            else if (shard_number == 2)
                ++shard3;
            else if (shard_number == 3)
                ++shard4;
            else if (shard_number == 4)
                ++shard5;
            else if (shard_number == 5)
                ++shard6;
            else if (shard_number == 6)
                ++shard7;
            else if (shard_number == 7)
                ++shard8;
            else if (shard_number == 8)
                ++shard9;
            else if (shard_number == 9)
                ++shard10;
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
    public void rangeRingTest() throws IOException {
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
            arr[i] = DataPartition.rangeRing(key, 4);
            ++i;
        }
        i = 0;
        for (String key : keys){
            assertEquals(arr[i], DataPartition.rangeRing(key, 4));
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


    /*@Test
    public void hashMapTest() throws IOException {
        System.out.println("Hash map");
        Map <String, Integer> keys = new HashMap();
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

        for (int i = 0; i < 1000; i++){
            String key = UUID.randomUUID().toString();
            int shard_number = DataPartition.hashMap(key, 2);
            keys.put(key, shard_number);
        }
        Set <String> keySet = keys.keySet();
        for (String key : keySet){
            //System.out.println("Stop");
            int shard_number = keys.get(key);
            assertEquals(shard_number, DataPartition.hashMap(key, 2));
            if (shard_number == 0)
                ++shard1;
            else if (shard_number == 1)
                ++shard2;
            else if (shard_number == 2)
                ++shard3;
            else if (shard_number == 3)
                ++shard4;
            else if (shard_number == 4)
                ++shard5;
            else if (shard_number == 5)
                ++shard6;
            else if (shard_number == 6)
                ++shard7;
            else if (shard_number == 7)
                ++shard8;
            else if (shard_number == 8)
                ++shard9;
            else if (shard_number == 9)
                ++shard10;
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
    } */
}