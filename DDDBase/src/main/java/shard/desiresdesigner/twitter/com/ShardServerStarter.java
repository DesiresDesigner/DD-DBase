package shard.desiresdesigner.twitter.com;

import org.eclipse.jetty.server.Server;
import test.desiresdesigner.twitter.com.Handler;

/**
 * @author desiresdesigner
 * @since 3/11/14
 */
public class ShardServerStarter {

    public static void main(String[] args) throws Exception {
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

        Server server = new Server(port);
        //server.setHandler(new ShardServer(port + "data", port + "pointers", port + "free"));
        server.setHandler(new ShardServer());
        server.start();
        server.join();
    }
}
