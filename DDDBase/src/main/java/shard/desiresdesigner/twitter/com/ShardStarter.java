package shard.desiresdesigner.twitter.com;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author desiresdesigner
 * @since 1/26/14
 */
public class ShardStarter {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        if (args.length == 0){
            System.out.println("use port 8000");
        } else {
            try{
                port = Integer.parseInt(args[0]);
                System.out.println("use port " + port);
            } catch (NumberFormatException e) {
                System.out.println("bad port number");
                System.exit(0);
            }
        }
        createServer(port);
    }

    private static void createServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 10);
        server.createContext("/", new Shard(port + "data", port + "pointers", port + "free"));
        server.start();
    }
}
