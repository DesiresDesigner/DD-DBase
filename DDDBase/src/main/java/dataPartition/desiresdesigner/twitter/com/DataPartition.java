package dataPartition.desiresdesigner.twitter.com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */
public class DataPartition {
    private static SortedMap<Long, Integer> hashRingMap = new TreeMap();
    private static int shardsAmount = 0;

    public static int simpleHash(String key, int shardsAmount){
        int hashCode = key.hashCode();
        return (int)((hashCode + Math.pow(2, 31)) % shardsAmount - 1);
    }

    public static int hashRing(String key, int shardsAmount){
        if (DataPartition.shardsAmount == 0 || DataPartition.shardsAmount != shardsAmount){
            constructHashRing(shardsAmount);
            DataPartition.shardsAmount = shardsAmount;
        }
        SortedMap<Long, Integer> tail = hashRingMap.tailMap(md5HashCode(key));
        if (tail.isEmpty()) {
            return hashRingMap.get(hashRingMap.firstKey());
        }
        return tail.get(tail.firstKey());
    }

    private static void constructHashRing(int shardsAmount){
        for (int i = -1; i < shardsAmount - 1; i ++){
            hashRingMap.put(md5HashCode("SHARD-" + i), i);
        }
    }

    private static long md5HashCode(String str){
        MessageDigest md5 ;
        StringBuffer  hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return ((long) (messageDigest[3] & 0xFF) << 24)
                    | ((long) (messageDigest[2] & 0xFF) << 16)
                    | ((long) (messageDigest[1] & 0xFF) << 8) | (long) (messageDigest[0] & 0xFF);
        } catch (NoSuchAlgorithmException e) {
            return 0;
        }
    }

}
