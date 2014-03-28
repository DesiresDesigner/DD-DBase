package tcpDDDB.desiresdesigner.twitter.com;

import dataPartition.desiresdesigner.twitter.com.DataPartition;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DesiresDesigner DataBase with 3 shards
 * @author desiresdesigner
 * @since 1/27/14
 */

/*
* 1 - key already exists
* 2 - writing error
* 3 - no such element
* 4 - not valid command
*/

public class DDDB {
    int shardAmount = 0;

    private List <SocketClient> sClients;
    private int distribution[];

    public DDDB(){
        sClients = new ArrayList();
        distribution = new int[4];
    }

    public int clear() {
        System.out.print("Distribution: ");
        for (int i = 0; i < 4; ++i){
            System.out.print(distribution[i] + ", ");
            distribution[i] = 0;
        }
        System.out.println();
        String response;
        for (SocketClient sClient : sClients){
            try{
                response = sClient.sendRequest("clr");
            } catch (IOException e){
                return 2;
            }
            if (!response.equals("0")){
                return Integer.parseInt(response);
            }
        }
        return 0;
    }

    public int addShard(String address, int port) throws IOException {
        sClients.add(new SocketClient(address, port));
        ++shardAmount;
        for (int i = 0; i < sClients.size(); i ++){
            String keysString = getKeysFromShard(i);
            if (keysString == "4")
                    return 4;
            //DataPartition.constructRangeRing(shardAmount);
            DataPartition.constructHashMap(shardAmount);
            Set <String> keysSet = parseKeys(keysString);
            for (String key : keysSet){
                int shardNumber = getShardNumberForKey(key);
                if (shardNumber != i){
                    String value = getValueFromShard(key, i);
                    delValueOnShard(key, i);
                    addValue(key, value);
                }
            }
        }
        return 0;
    }

    public int addValue(String key, String value) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return 5;
        }
        int shardNumber = getShardNumberForKey(key);

        String response = sClients.get(shardNumber).sendRequest("add" + key + ";" + value + ";");

        if (response.equals("0"))
            distribution[shardNumber] += 1;
        return Integer.parseInt(response);
    }

    public int delValue(String key) throws IOException {
        return delValueOnShard(key, getShardNumberForKey(key));
    }

    public int editValue(String key, String value) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return 5;
        }
        int shardNumber = getShardNumberForKey(key);

        String response = sClients.get(shardNumber).sendRequest("edt" + key + ";" + value + ";");

        return Integer.parseInt(response);
    }

    public String getValue(String key) throws IOException {
        return getValueFromShard(key, getShardNumberForKey(key));
    }

    private int delValueOnShard(String key, int shardNumber) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return 5;
        }

        String response = sClients.get(shardNumber).sendRequest("del" + key + ";");

        return Integer.parseInt(response);
    }

    private String getValueFromShard(String key, int shardNumber) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return "5";
        }

        String response = sClients.get(shardNumber).sendRequest("get" + key + ";");

        return response;
    }

    private Set parseKeys(String keys){
        Set <String> keysSet = new LinkedHashSet();
        String tmpRes = "";
        for (char ch : keys.toCharArray()){
            if (ch != '-'){
                tmpRes += ch;
            } else {
                keysSet.add(tmpRes);
                tmpRes = "";
            }
        }
        return keysSet;
    }

    private String getKeysFromShard(int shardNumber) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return "5";
        }
        String response = sClients.get(shardNumber).sendRequest("geK");

        return response;
    }

    private int getShardNumberForKey(String key){
        //return DataPartition.simpleHash(key, shardAmount);
        //return DataPartition.rangeRing(key);
        return DataPartition.hashMap(key);
    }
}
