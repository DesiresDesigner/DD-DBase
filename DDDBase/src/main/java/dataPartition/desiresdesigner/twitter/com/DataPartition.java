package dataPartition.desiresdesigner.twitter.com;

/**
 * @author desiresdesigner
 * @since 2/13/14
 */
public class DataPartition {

    public static int simpleHash(String key, int shardsAmount){
        int hashCode = key.hashCode();
        return (int)((hashCode + Math.pow(2, 31)) % shardsAmount - 1);
    }

}
