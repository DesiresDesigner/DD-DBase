import dataPartition.desiresdesigner.twitter.com.DataPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */

@RunWith(JUnit4.class)
public class DataPartitionTest {

    @Test
    public void hashTest() throws IOException {
        System.out.println("Simple hash");
        int[] arr = new int[100];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        for (int i = 0; i < 100; i ++){
            arr[i] = DataPartition.simpleHash("index" + i, 3);
        }
        for (int i = 0; i < 100; i ++){
            assertEquals(arr[i], DataPartition.simpleHash("index" + i, 3));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
    }

    @Test
    public void hashRingTest() throws IOException {
        DataPartition.setShardsAmount(0);
        System.out.println("Hash ring");
        int[] arr = new int[100];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        for (int i = 0; i < 100; i ++){
            arr[i] = DataPartition.hashRing("index" + i, 3);
        }
        for (int i = 0; i < 100; i ++){
            assertEquals(arr[i], DataPartition.hashRing("index" + i, 3));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
    }


    @Test
    public void hashMapTest() throws IOException {
        DataPartition.setShardsAmount(0);
        System.out.println("Hash map");

        int[] arr = new int[100];
        int shard1 = 0;
        int shard2 = 0;
        int shard3 = 0;
        for (int i = 0; i < 100; i ++){
            arr[i] = DataPartition.hashMap("index" + i, 3);
        }
        for (int i = 0; i < 100; i ++){
            assertEquals(arr[i], DataPartition.hashMap("index" + i, 3));
            if (arr[i] == 0)
                ++shard1;
            else if (arr[i] == 1)
                ++shard2;
            else if (arr[i] == 2)
                ++shard3;
        }
        System.out.println("shard1: " + shard1);
        System.out.println("shard2: " + shard2);
        System.out.println("shard3: " + shard3);
    }
}