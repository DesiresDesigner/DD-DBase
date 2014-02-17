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

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author desiresdesigner
 * @since 2/17/14
 */
public class DDDBStarterTest {
    private final HttpPost post;
    private HttpClient client;
    private HttpHost httpHost;

    public DDDBStarterTest() throws IOException {
        post = new HttpPost("/test");
        client = HttpClientBuilder.create().build();
        httpHost = new HttpHost("localhost", 8000);
    }

    @Test
    public void commonTest() throws IOException {
        //HttpServer server = HttpServer.create(new InetSocketAddress(8000), 10);
        //server.createContext("/", new Shard());
        //server.start();

        post.setHeader("key", "123");
        post.setHeader("value", "Some_value");
        post.setHeader("command", "add");
        String requestBody = "Adding value";
        post.setEntity(new StringEntity(requestBody));
        HttpResponse execute = client.execute(httpHost, post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);

        System.out.println("Adding: " + response);
        assertEquals(response, "0");

        post.setHeader("key", "123");
        post.setHeader("value", "Some_new_value");
        post.setHeader("command", "edit");
        requestBody = "editing value";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Editing: " + response);
        assertEquals(response, "0");

        post.setHeader("key", "123");
        post.setHeader("command", "get");
        requestBody = "getting value";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Getting: " + response);
        assertEquals(response, "Some_new_value");

        post.setHeader("key", "123");
        post.setHeader("command", "del");
        requestBody = "deleting value";
        post.setEntity(new StringEntity(requestBody));
        execute = client.execute(httpHost, post);
        handler = new BasicResponseHandler();
        response = handler.handleResponse(execute);

        System.out.println("Deleting: " + response);
        assertEquals(response, "0");

        //server.stop(0);
    }
}
