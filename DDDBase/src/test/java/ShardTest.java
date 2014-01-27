import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import shard.desiresdesigner.twitter.com.Shard;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import static junit.framework.Assert.assertEquals;

/**
 * @author desiresdesigner
 * @since 1/26/14
 */

@RunWith(JUnit4.class)
public class ShardTest {
    private final HttpPost post;
    private HttpClient client;
    private HttpHost httpHost;

    public ShardTest() throws IOException {
        post = new HttpPost("/test");
        client = HttpClientBuilder.create().build();
        httpHost = new HttpHost("localhost", 8100);

    }

    /*@Test
    public void addingTest() throws IOException {

        post.setHeader("key", "123");
        post.setHeader("value", "Some_value");
        post.setHeader("command", "add");
        String requestBody = "Adding value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHost, post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);

        System.out.println(response);
        assertEquals(response, "true");
    } */

    @Test
    public void gettingValueTest() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 10);
        server.createContext("/", new Shard());
        server.start();

        post.setHeader("key", "123");
        post.setHeader("command", "get");
        String requestBody = "getting value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHost, post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);

        System.out.println(response);
        assertEquals(response, "Some_value");

        server.stop(0);
    }

    @Test
    public void invalidCommandTest() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 10);
        server.createContext("/", new Shard());
        server.start();

        post.setHeader("command", "invalidCommand");
        String requestBody = "getting error";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHost, post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);

        System.out.println(response);
        assertEquals(response, "not valid command: invalidCommand.");

        server.stop(0);
    }

}
