package DDDB.desiresdesigner.twitter.com;

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
//import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
    Manager shardManager = new Manager();

    private final HttpPost post;
    private HttpClient client;
    private List <HttpHost> httpHosts;

    public DDDB(){
        post = new HttpPost("/");
        client = HttpClientBuilder.create().build();
        httpHosts = new ArrayList();
        httpHosts.add(new HttpHost("localhost", 8000));
        httpHosts.add(new HttpHost("localhost", 8100));
    }

    public int addValue(String key, String value) throws IOException {
        int hashCode = key.hashCode();
        int shardNumber = hashCode % 3 - 1;
        if (shardNumber == -1){
            return shardManager.addValue(key, value);
        }
        else {
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
    }

    public int delValue(String key) throws IOException {
        int hashCode = key.hashCode();
        int shardNumber = hashCode % 3 - 1;
        if (shardNumber == -1){
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
        }
    }

    public int editValue(String key, String value) throws IOException {
        int hashCode = key.hashCode();
        int shardNumber = hashCode % 3 - 1;
        if (shardNumber == -1){
            return shardManager.editValue(key, value);
        }
        else {
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
    }

    public String getValue(String key) throws IOException {
        int hashCode = key.hashCode();
        int shardNumber = hashCode % 3 - 1;
        if (shardNumber == -1){
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
        }
    }
}
