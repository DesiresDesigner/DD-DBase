package tcpShard.desiresdesigner.twitter.com;

import manager.desiresdesigner.twitter.com.HashMapManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author desiresdesigner
 * @since 3/27/14
 */
public class Handler implements Runnable{
    private Socket soc;
    private DataInputStream in;
    private DataOutputStream out;
    private HashMapManager manager;

    public Handler(Socket soc)
    {
        this.soc = soc;
        manager = new HashMapManager();

        try
        {
            this.in = new DataInputStream(this.soc.getInputStream());
            this.out = new DataOutputStream(this.soc.getOutputStream());
        } catch (IOException e)
        {
            System.out.println("Can't create data streams!");
        }
    }

    public void run(){
        while (true){
            try {

                System.out.println(System.currentTimeMillis() + ": Waiting request...");

                String command = String.valueOf(String.valueOf(new char[]{in.readChar(), in.readChar(), in.readChar()}));

                System.out.println(System.currentTimeMillis() + ": Have a request!");

                if (command.equals("add")){
                    String key = "";
                    String value = "";
                    char c = in.readChar();
                    while (c != ';'){
                        key += c;
                        c = in.readChar();
                    }
                    c = in.readChar();
                    while (c != ';'){
                        value += c;
                        c = in.readChar();
                    }
                    out.writeChars(String.valueOf(manager.addValue(key, value)) + ";");
                    System.out.println("add(" + key + ", " + value + ")");
                } else if (command.equals("edt")){
                    String key = "";
                    String value = "";
                    char c = in.readChar();
                    while (c != ';'){
                        key += c;
                        c = in.readChar();
                    }
                    c = in.readChar();
                    while (c != ';'){
                        value += c;
                        c = in.readChar();
                    }
                    out.writeChars(String.valueOf(manager.editValue(key, value)) + ";");
                    System.out.println("edit(" + key + ", " + value + ")");
                } else if (command.equals("get")){
                    String key = "";
                    char c = in.readChar();
                    while (c != ';'){
                        key += c;
                        c = in.readChar();
                    }
                    out.writeChars(String.valueOf(manager.getValue(key)) + ";");
                    System.out.println("get(" + key + ")");
                } else if (command.equals("del")){
                    String key = "";
                    char c = in.readChar();
                    while (c != ';'){
                        key += c;
                        c = in.readChar();
                    }
                    out.writeChars(String.valueOf(manager.deleteValue(key)) + ";");
                    System.out.println("del(" + key + ")");
                } else if (command.equals("geK")){
                    out.writeChars(String.valueOf(manager.getKeys()) + ";");
                    System.out.println("getKeys()");
                } else if (command.equals("clr")){
                    out.writeChars(String.valueOf(manager.clearDataStorage()) + ";");
                    System.out.println("clear()");
                } else {
                    out.writeChars("Error;");
                    System.out.println("Error");
                }
                out.flush();

                System.out.println(System.currentTimeMillis() + ": Sending response...");

            } catch (IOException e) {
                System.out.println("Closing...");
                try
                {
                    this.soc.close();
                    Thread.currentThread().interrupt();
                    return;
                } catch (IOException e1)
                {
                    System.out.println("Socket cannot be closed!");
                }

            }
        }
    }

}
