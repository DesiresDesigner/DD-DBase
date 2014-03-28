package DDDB.desiresdesigner.twitter.com;

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

/**
 * @author desiresdesigner
 * @since 2/26/14
 */
public class AddShards {

    public static void main(String[] args) throws IOException {
        final HttpPost post = new HttpPost("/test");;
        HttpClient client = HttpClientBuilder.create().build();;
        HttpHost httpHost = new HttpHost("localhost", 8000);

        String requestBody;
        HttpResponse execute;
        ResponseHandler<String> handler;
        String response;

        /*post.setHeader("address", "localhost");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        Handler = new BasicResponseHandler();
        response = Handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);*/

        post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        /*post.setHeader("address", "192.168.50.174");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        Handler = new BasicResponseHandler();
        response = Handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8100");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        Handler = new BasicResponseHandler();
        response = Handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);

        post.setHeader("address", "192.168.50.187");
        post.setHeader("port", "8200");
        post.setHeader("command", "addShard");
        requestBody = "Adding shard";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        Handler = new BasicResponseHandler();
        response = Handler.handleResponse(execute);

        System.out.println("Adding shard: " + response);/**/
    }
}
