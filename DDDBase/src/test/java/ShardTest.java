import com.sun.net.httpserver.HttpServer;
import manager.desiresdesigner.twitter.com.Manager;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import shard.desiresdesigner.twitter.com.Shard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public ShardTest() {
        post = new HttpPost("/test");
        client = HttpClientBuilder.create().build();
        httpHost = new HttpHost("localhost", 8000);
    }

    @Before
    public void before() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 10);
        server.createContext("/", new Shard());
        server.start();
    }

    @Test
    public void addingTest() throws IOException {

        post.setHeader("key", "1");
        post.setHeader("value", "Misha");
        post.setHeader("command", "add");
        String requestBody = "Adding value";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHost, post);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = handler.handleResponse(execute);

        System.out.println(response);
        assertEquals(response, "add");
    }

}
