package manager.desiresdesigner.twitter.com;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author desiresdesigner
 * @since 3/13/14
 */
public class HashMapManager {
    int amount = 0;
    private Map<String, String> dataMap;

    public HashMapManager(){
        dataMap = new HashMap();
    }

    public int clearDataStorage(){
        dataMap.clear();
        amount = 0;
        return 0;
    }

    public int addValue(String key, String value) {

        if (dataMap.containsKey(key)){
            System.out.println("This key is already exist");
            return 1;
        }
        dataMap.put(key, value);

        ++amount;
        System.out.println("Amount: " + amount);
        return 0;
    }

    public String getValue(String key) throws RuntimeException{
        if (!dataMap.containsKey(key)){
            System.out.println("No such key: " + key);
            return "3";
        }
        return dataMap.get(key);
    }

    public int deleteValue(String key) throws IOException {
        if (!dataMap.containsKey(key))
            return 3;
        dataMap.remove(key);
        --amount;
        System.out.println("Amount: " + amount);
        return 0;
    }

    public int editValue(String key, String value) throws IOException {
        if (!dataMap.containsKey(key))
            return 3;
        dataMap.put(key, value);
        return 0;
    }

    public String getKeys(){
        String res = "";
        for (String key : dataMap.keySet()){
            if (res == ""){
                res = key;
            } else {
                res += "-" + key;
            }
        }
        return res;
    }
}
