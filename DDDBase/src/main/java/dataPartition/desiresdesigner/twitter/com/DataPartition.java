package dataPartition.desiresdesigner.twitter.com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */
public class DataPartition {
    private static SortedMap<Long, Integer> hashMapTree = new TreeMap();
    private static SortedMap<Integer, Integer> hashRingTree = new TreeMap();
    private static int shardsAmount = 0;

    public static int simpleHash(String key, int shardsAmount){ // Simple implementation by hash%n
        int hashCode = key.hashCode();
        return (int)((hashCode + Math.pow(2, 31)) % shardsAmount);
    }

    public static int hashMap(String key, int shardsAmount){ // Simple implementation of Redis algorithm
        if (DataPartition.shardsAmount == 0 || DataPartition.shardsAmount != shardsAmount){
            constructHashMap(shardsAmount);
            DataPartition.shardsAmount = shardsAmount;
        }
        SortedMap<Long, Integer> tail = hashMapTree.tailMap(md5HashCode(key));
        if (tail.isEmpty()) {
            return hashMapTree.get(hashMapTree.firstKey());
        }
        return tail.get(tail.firstKey());
    }

    public static int hashRing(String key, int shardsAmount){ //by range (MongoDB simple implementation)
        if (DataPartition.shardsAmount == 0 || DataPartition.shardsAmount != shardsAmount){
            constructHashRing(shardsAmount);
            DataPartition.shardsAmount = shardsAmount;
        }
        SortedMap<Integer, Integer> tail = hashRingTree.tailMap(key.hashCode());
        if (tail.isEmpty()) {
            return hashRingTree.get(hashRingTree.firstKey());
        }
        return tail.get(tail.firstKey());
    }

    public static void setShardsAmount(int shardsAmount){
        DataPartition.shardsAmount = shardsAmount;
    }

    private static void constructHashRing(int shardsAmount){
        int shardDataAmount = (int)(Math.pow(2, 62)/shardsAmount);
        for (int i = 0; i < shardsAmount; i ++){
            int node = (int)Math.pow(-2, 31) + shardDataAmount*(i + 1);
            hashRingTree.put(node, i);
        }
    }

    private static void constructHashMap(int shardsAmount){
        for (int i = 0; i < shardsAmount; i ++){
            hashMapTree.put(md5HashCode("SHARD-" + i), i);
        }
    }

    private static long md5HashCode(String str){
        MessageDigest md5 ;
        StringBuffer hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return ((long) (messageDigest[3] & 0xFF) << 24)
                    | ((long) (messageDigest[2] & 0xFF) << 16)
                    | ((long) (messageDigest[1] & 0xFF) << 8) | (long) (messageDigest[0] & 0xFF);
        } catch (NoSuchAlgorithmException e) {
            return 0;
        }
    }

}
