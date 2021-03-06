package dataPartition.desiresdesigner.twitter.com;

import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */
public class DataPartition {
    private static SortedMap<Long, Integer> hashMapTree = new TreeMap();
    private static SortedMap<Integer, Integer> rangeRingTree = new TreeMap();
    private static int shardsAmount = 0;

    public static int simpleHash(String key, int shardsAmount) { // Simple implementation by hash%n
        //int hashCode = key.hashCode();
        long hashCode = md5HashCode(key);
        return (int)((hashCode + Math.pow(2, 31)) % shardsAmount);
    }

    public static int hashMap(String key){ //, int shardsAmount) { // Simple implementation of Redis algorithm
        /*if (DataPartition.shardsAmount == 0 || DataPartition.shardsAmount != shardsAmount){
            constructHashMap(shardsAmount);
            DataPartition.shardsAmount = shardsAmount;
        }*/
        long hashCode = md5HashCode(key);
        SortedMap<Long, Integer> tail = hashMapTree.tailMap(hashCode);
        if (tail.isEmpty()) {
            return hashMapTree.get(hashMapTree.firstKey());
        }
        return tail.get(tail.firstKey());
    }

    public static int rangeRing(String key){//, int shardsAmount){ //by rangeRing (MongoDB simple implementation)
        /*if (DataPartition.shardsAmount == 0 || DataPartition.shardsAmount != shardsAmount){
            constructRangeRing(shardsAmount);
            DataPartition.shardsAmount = shardsAmount;
        }                                             */
        int i = 0;
        while ((int)key.charAt(i) < 65 || ((int)key.charAt(i) > 90 && (int)key.charAt(i) < 97) || (int)key.charAt(i) > 122)
            ++i;
        //System.out.println(key.charAt(i));
        return rangeRingTree.get((int)key.charAt(i));
    }

    public static void setShardsAmount(int shardsAmount){
        DataPartition.shardsAmount = shardsAmount;
    }

    public static void constructRangeRing(int shardsAmount){
        System.out.println("Construct ring for " + shardsAmount);
        //int shardDataAmount = (int)(26/shardsAmount);
        for (int l = 65; l < 91; l ++){
            rangeRingTree.put(l, l%shardsAmount);
        }
        for (int l = 97; l < 123; l ++){
            rangeRingTree.put(l, l%shardsAmount);
        }
        /*for (int l = 65; l < 91; l ++){
            int node = ((l-65)/shardDataAmount);
            node = (node < shardsAmount) ? node : shardsAmount - 1;
            rangeRingTree.put(l, node);
        }
        for (int l = 97; l < 123; l ++){
            int node = ((l-97)/shardDataAmount);
            node = (node < shardsAmount) ? node : shardsAmount - 1;
            rangeRingTree.put(l, node);
        }*/
    }

    public static void constructHashMap(int shardsAmount) {
        for (int i = 0; i < shardsAmount; i ++){
            for (int n = 0; n < 300; n++) {
                long hashCOde = md5HashCode("SHARD-" + i + "-NODE-" + n);
                hashMapTree.put(hashCOde, i);
            }
        }
    }

    public static long md5HashCode(String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(key.getBytes("UTF-8"));
            byte[] bKey = md5.digest();
            return ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        } catch (Exception e){
            return 0;
        }
    }

}
