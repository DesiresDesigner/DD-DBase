package shard.desiresdesigner.twitter.com;

import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author desiresdesigner
 * @since 1/26/14
 */
public class ShardStarter {
    public static void main(String[] args) throws IOException {
        createServer();
        /*HttpClient client = HttpClientBuilder.create().build();
        HttpHost httpHost = new HttpHost("localhost", 8000);
        final HttpPost post = new HttpPost("/test");
        post.setHeader("hey", "value");
        String requestBody = "requestBody";
        post.setEntity(new StringEntity(requestBody));
        final HttpResponse execute = client.execute(httpHost, post);
        System.out.println(execute.getStatusLine());*/
    }

    private static void createServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 10);
        server.createContext("/", new Shard());
        server.start();
    }
}
