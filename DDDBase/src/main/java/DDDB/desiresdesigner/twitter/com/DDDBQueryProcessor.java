package DDDB.desiresdesigner.twitter.com;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author desiresdesigner
 * @since 2/23/14
 */
public class DDDBQueryProcessor implements Runnable {
    DDDB db;
    HttpExchange httpExchange;

    public DDDBQueryProcessor (DDDB db, HttpExchange httpExchange) {
        this.db = db;
        this.httpExchange = httpExchange;
    }

    @Override
    public void run() {
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
            int res = 0;
            try {
                res = db.addValue(key, value);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            responseBody = String.valueOf(res);
            System.out.println("add(" + key + ", " + value + ")");
        }
        else if (command.equals("del")){
            int res = 0;
            try {
                res = db.delValue(key);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            responseBody = String.valueOf(res);
            System.out.println("del(" + key + ")");
        }
        else if (command.equals("edit")){
            int res = 0;
            try {
                res = db.editValue(key, value);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            responseBody = String.valueOf(res);
            System.out.println("edit(" + key + ", " + value + ")");
        }
        else if (command.equals("get")){
            try {
                responseBody = db.getValue(key);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            System.out.println("get(" + key + ")");
        }
        else if (command.equals("addShard")){
            try {
                responseBody = String.valueOf(db.addShard(address, Integer.parseInt(port)));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            System.out.println("addShard(" + address + ", " + port + ")");
        }
        else {
            responseBody = "4";
            System.out.println("unknown command");
        }

        final Writer writer = new OutputStreamWriter(httpExchange.getResponseBody());
        try {
            httpExchange.sendResponseHeaders(200, responseBody.length());
            writer.write(responseBody);
            writer.flush();
            writer.close();
            httpExchange.getResponseBody().flush();
            httpExchange.getResponseBody().close();
            httpExchange.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        }
        System.out.println("Processing is complete");
    }
}
