import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import shard.desiresdesigner.twitter.com.Shard;

import static junit.framework.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author desiresdesigner
 * @since 2/17/14
 */
public class DDDBStarterTest {
    private final HttpPost post;
    private HttpClient client;
    private HttpHost httpHost;
    private Set<String> keys;

    public DDDBStarterTest() throws IOException {
        post = new HttpPost("/test");
        client = HttpClientBuilder.create().build();
        httpHost = new HttpHost("localhost", 8000);
        keys = new HashSet();
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
    }*/

    @Test
    public void commonTest() throws IOException {

        //Map<String, String> keys = new HashMap();

        String requestBody;
        HttpResponse execute;
        ResponseHandler<String> handler;
        String response;

        post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        for (int i = 0; i < 10; i ++){
            post.setHeader("key", "key" + i);
            post.setHeader("value", "value" + i);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        post.setHeader("command", "clear");
        requestBody = "Clear Data Storage";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        assertEquals(response, "0");

        post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        for (int i = 0; i < 10; i ++){
            post.setHeader("key", "key" + i);
            post.setHeader("value", "value" + i);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        post.setHeader("command", "clear");
        requestBody = "Clear Data Storage";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        assertEquals(response, "0");

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        for (int i = 0; i < 10; i ++){
            post.setHeader("key", "key" + i);
            post.setHeader("value", "value" + i);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        post.setHeader("command", "clear");
        requestBody = "Clear Data Storage";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        assertEquals(response, "0");

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        for (int i = 0; i < 10; i ++){
            post.setHeader("key", "key" + i);
            post.setHeader("value", "value" + i);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        post.setHeader("command", "clear");
        requestBody = "Clear Data Storage";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        assertEquals(response, "0");

        /*post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        for (int i = 0; i < 1000; i ++){
            String key = UUID.randomUUID().toString();
            String value = "val" + i;
            keys.put(key, value);
            post.setHeader("key", key);
            post.setHeader("value", value);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        Set <String> keysSet = keys.keySet();
        for (String key : keysSet){
            post.setHeader("key", key);
            post.setHeader("value", keys.get(key) + "new");
            post.setHeader("command", "edit");
            requestBody = "editing value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }

        for (String key : keysSet){
            post.setHeader("key", key);
            post.setHeader("command", "get");
            requestBody = "getting value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, keys.get(key) + "new");
        }

        for (String key : keysSet){
            post.setHeader("key", key);
            post.setHeader("command", "del");
            requestBody = "deleting value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            response = handler.handleResponse(execute);

            assertEquals(response, "0");
        }*/
    }
}
