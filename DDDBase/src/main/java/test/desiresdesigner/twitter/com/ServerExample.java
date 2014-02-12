package test.desiresdesigner.twitter.com;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.InetSocketAddress;

public class ServerExample {

  public static final int OK_RESPONSE_CODE = 200;

  public static void main(String[] args) throws IOException {
    createServer();
    HttpClient client = HttpClientBuilder.create().build();
    HttpHost httpHost = new HttpHost("localhost", 8000);
    final HttpPost post = new HttpPost("/test");
    post.setHeader("hey", "value");
    String requestBody = "requestBody";
    post.setEntity(new StringEntity(requestBody));
    final HttpResponse execute = client.execute(httpHost, post);
    System.out.println(execute.getStatusLine());
  }

  private static void createServer() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 10);
    server.createContext("/test", new MyHandler());
    server.start();
  }


  static class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
      System.out.println(httpExchange.getRequestURI());
      System.out.println(httpExchange.getRequestHeaders().keySet());
      System.out.println(httpExchange.getRequestMethod());
      System.out.println(IOUtils.toString(httpExchange.getRequestBody()));

      final Writer writer = new OutputStreamWriter(httpExchange.getResponseBody());
      final String responseBody = "response!";
      httpExchange.sendResponseHeaders(OK_RESPONSE_CODE, responseBody.length());
      writer.write(responseBody);
      writer.flush();
      writer.close();
      httpExchange.getResponseBody().flush();
      httpExchange.getResponseBody().close();
      httpExchange.close();
    }
  }
}
