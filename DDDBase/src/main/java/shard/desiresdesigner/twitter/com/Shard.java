package shard.desiresdesigner.twitter.com;

/**
 * @author desiresdesigner
 * @since 1/21/14
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
import manager.desiresdesigner.twitter.com.Manager;
/*import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;*/

import java.io.*;
//import java.net.InetSocketAddress;

import java.io.IOException;

/*
* 1 - key already exists
* 2 - writing error
* 3 - no such element
* 4 - not valid command
*/

public class Shard implements HttpHandler {
    Manager shardManager;

    public Shard(){
        shardManager = new Manager();

    }

    public Shard(String dataStorageName, String pointerStorageName, String freeSpaceStorageName){
        shardManager = new Manager(dataStorageName, pointerStorageName, freeSpaceStorageName);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String responseBody;

        String command = httpExchange.getRequestHeaders().get("command").get(0);
        String key = "";
        if (httpExchange.getRequestHeaders().containsKey("key")){
            key = httpExchange.getRequestHeaders().get("key").get(0);
        }
        String value = "";
        if (httpExchange.getRequestHeaders().containsKey("value")){
            value = httpExchange.getRequestHeaders().get("value").get(0);
        }
        if (command.equals("add")){
            int res = shardManager.addValue(key, value);
            responseBody = String.valueOf(res);
            System.out.println("add(" + key + ", " + value + ")");
        }
        else if (command.equals("del")){
            int res = shardManager.deleteValue(key);
            responseBody = String.valueOf(res);
            System.out.println("del(" + key + ")");
        }
        else if (command.equals("edit")){
            int res = shardManager.editValue(key, value);
            responseBody = String.valueOf(res);
            System.out.println("edit(" + key + ", " + value + ")");
        }
        else if (command.equals("get")){
            responseBody = shardManager.getValue(key);
            System.out.println("get(" + key + ")");
        }
        else if (command.equals("getKeys")){
            responseBody = shardManager.getKeys();
            System.out.println("getKeys");
        }
        else if (command.equals("clear")){
            int res = shardManager.clearDataStorage();
            responseBody = String.valueOf(res);
            System.out.println("clear");
        }
        else {
            responseBody = "4";
        }

        final Writer writer = new OutputStreamWriter(httpExchange.getResponseBody());
        httpExchange.sendResponseHeaders(200, responseBody.length());
        writer.write(responseBody);
        writer.flush();
        writer.close();
        httpExchange.getResponseBody().flush();
        httpExchange.getResponseBody().close();
        httpExchange.close();
    }
}

