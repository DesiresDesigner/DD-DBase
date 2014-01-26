package shard.desiresdesigner.twitter.com;

/**
 * @author desiresdesigner
 * @since 1/21/14
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.desiresdesigner.twitter.com.Manager;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.InetSocketAddress;

import java.io.IOException;


public class Shard implements HttpHandler {
    Manager shardManager;


    public Shard(){
        shardManager = new Manager();

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String responseBody = "";

        String command = httpExchange.getRequestHeaders().get("command").get(0);
        String key = httpExchange.getRequestHeaders().get("key").get(0);
        String value = httpExchange.getRequestHeaders().get("value").get(0);
        //if (command == "add"){
        if (command.equals("add")){
            responseBody = "add";
        }
        else if (command.equals("del")){
            responseBody = "del";
        }
        else if (command.equals("edit")){
            responseBody = "edit";
        }
        else if (command.equals("get")){
            responseBody = "get";
        }
        else {
            responseBody = "not valid command:" + command + ".";
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

