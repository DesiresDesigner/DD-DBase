package tcpShard.desiresdesigner.twitter.com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author desiresdesigner
 * @since 3/27/14
 */
public class ServerStarter {

    public static void main(String [] args)
    {
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
        while (true)
        {
            try
            {
                ServerSocket soc = new ServerSocket(port);
                System.out.println("Waiting for client on port " + port + "...");
                Socket server = soc.accept();
                System.out.println("Connected: " + server.getRemoteSocketAddress());
                Thread t = new Thread(new Handler(server));
                t.start();
            }
            catch (SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                //break;
                continue;
            }
        }
    }
}
