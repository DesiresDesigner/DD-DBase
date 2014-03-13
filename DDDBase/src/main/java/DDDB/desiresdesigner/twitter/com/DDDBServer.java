package DDDB.desiresdesigner.twitter.com;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.desiresdesigner.twitter.com.Manager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author desiresdesigner
 * @since 2/17/14
 */
public class DDDBServer implements HttpHandler {
    DDDB db;

    public DDDBServer(){
        db = new DDDB();

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        long startTime = System.nanoTime();
        //System.out.println("New request. Time: " + System.nanoTime());
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
        String address = "";
        if (httpExchange.getRequestHeaders().containsKey("address")){
            address = httpExchange.getRequestHeaders().get("address").get(0);

        }
        String port = "";
        if (httpExchange.getRequestHeaders().containsKey("port")){
            port = httpExchange.getRequestHeaders().get("port").get(0);

        }
        if (command.equals("add")){
            int res = db.addValue(key, value);
            responseBody = String.valueOf(res);
            System.out.println("add(" + key + ", " + value + ")");
        }
        else if (command.equals("del")){
            int res = db.delValue(key);
            responseBody = String.valueOf(res);
            System.out.println("del(" + key + ")");
        }
        else if (command.equals("edit")){
            int res = db.editValue(key, value);
            responseBody = String.valueOf(res);
            System.out.println("edit(" + key + ", " + value + ")");
        }
        else if (command.equals("get")){
            responseBody = db.getValue(key);
            System.out.println("get(" + key + ")");
        }
        else if (command.equals("addShard")){
            responseBody = String.valueOf(db.addShard(address, Integer.parseInt(port)));
            System.out.println("addShard(" + address + ", " + port + ")");
        }
        else if (command.equals("clear")){
            int res = db.clear();
            responseBody = String.valueOf(res);
        }
        else {
            responseBody = "4";
            System.out.println("unknown command");
        }

        final Writer writer = new OutputStreamWriter(httpExchange.getResponseBody());
        httpExchange.sendResponseHeaders(200, responseBody.length());
        writer.write(responseBody);
        writer.flush();
        writer.close();
        httpExchange.getResponseBody().flush();
        httpExchange.getResponseBody().close();
        httpExchange.close();

        System.out.println("Processing is complete. Work time: " + (System.nanoTime() - startTime));
    }
}
