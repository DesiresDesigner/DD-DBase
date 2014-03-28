package tcpDDDB.desiresdesigner.twitter.com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author desiresdesigner
 * @since 3/27/14
 */
public class SocketClient {

    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    public SocketClient(String address, int port){
        try
        {
            client = new Socket(address, port);
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
        } catch (IOException e)
        {
            System.out.println("Can't connect to server");
            System.exit(1);
        }
    }

    public String sendRequest(String request) throws IOException {
        out.writeChars(request);
        out.flush();
        String response = "";
        char c = in.readChar();
        while (c != ';'){
            response += c;
            c = in.readChar();
        }
        return response;
    }
}
