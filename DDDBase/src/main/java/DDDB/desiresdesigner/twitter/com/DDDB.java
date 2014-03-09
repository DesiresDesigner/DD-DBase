package DDDB.desiresdesigner.twitter.com;

import dataPartition.desiresdesigner.twitter.com.DataPartition;
import manager.desiresdesigner.twitter.com.Manager;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    private final HttpPost post;
    private HttpClient client;
    private List <HttpHost> httpHosts;

    public DDDB(){
        post = new HttpPost("/");
        client = HttpClientBuilder.create().build();
        httpHosts = new ArrayList();
    }

    public int clear() {
        HttpHost v = httpHosts.get(0);
        String response;
        for (HttpHost host : httpHosts){
            try{
                post.setHeader("command", "clear");
                String requestBody = "clear";
                post.setEntity(new StringEntity(requestBody));
                final HttpResponse execute = client.execute(host, post);
                ResponseHandler<String> handler = new BasicResponseHandler();
                response = handler.handleResponse(execute);
            } catch (IOException e){
                return 2;
            }
            if (response != "0"){
                return Integer.parseInt(response);
            }
        }
        return 0;
    }

    public int addShard(String address, int port) throws IOException {
        httpHosts.add(new HttpHost(address, port));
        ++shardAmount;
        for (int i = 0; i < httpHosts.size(); i ++){
            String keysString = getKeysFromShard(i);
            if (keysString == "4")
                    return 4;
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
        post.setHeader("key", key);
        post.setHeader("value", value);
        post.setHeader("command", "add");
        String requestBody = "adding value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);
        return Integer.parseInt(response);
    }

    public int delValue(String key) throws IOException {
        return delValueOnShard(key, getShardNumberForKey(key));
        /*if (shardNumber == -1){
            return shardManager.deleteValue(key);
        }
        else {
            post.setHeader("key", key);
            post.setHeader("command", "del");
            String requestBody = "deleting value";
            post.setEntity(new StringEntity(requestBody));
            final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String response = handler.handleResponse(execute);
            return Integer.parseInt(response);
        }*/
    }

    public int editValue(String key, String value) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return 5;
        }
        int shardNumber = getShardNumberForKey(key);
        post.setHeader("key", key);
        post.setHeader("value", value);
        post.setHeader("command", "edit");
        String requestBody = "editing value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);
        return Integer.parseInt(response);
    }

    public String getValue(String key) throws IOException { //
        return getValueFromShard(key, getShardNumberForKey(key));
        /*if (shardNumber == -1){
            return shardManager.getValue(key);
        }
        else {
            post.setHeader("key", key);
            post.setHeader("command", "get");
            String requestBody = "getting value";
            post.setEntity(new StringEntity(requestBody));
            final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String response = handler.handleResponse(execute);
            return response;
        }*/
    }

    private int delValueOnShard(String key, int shardNumber) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return 5;
        }
        post.setHeader("key", key);
        post.setHeader("command", "del");
        String requestBody = "deleting value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);
        return Integer.parseInt(response);
    }

    private String getValueFromShard(String key, int shardNumber) throws IOException {
        if (shardAmount == 0){
            System.out.println("No shards");
            return "5";
        }
        post.setHeader("key", key);
        post.setHeader("command", "get");
        String requestBody = "getting value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);
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
        post.setHeader("command", "getKeys");
        String requestBody = "getting keys";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHosts.get(shardNumber), post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        return handler.handleResponse(execute);
    }

    private int getShardNumberForKey(String key){
        return DataPartition.simpleHash(key, shardAmount);
        //return DataPartition.rangeRing(key, shardAmount);
        //return DataPartition.hashMap(key, shardAmount);
    }
}
