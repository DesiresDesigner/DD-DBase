package test.desiresdesigner.twitter.com;
import org.eclipse.jetty.server.Server;


/**
 * @author desiresdesigner
 * @since 3/11/14
 */
public class JettyServerExample {

    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new Handler());
        server.start();
        server.join();
    }
}