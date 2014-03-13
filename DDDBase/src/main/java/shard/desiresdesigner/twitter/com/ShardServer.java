package shard.desiresdesigner.twitter.com;

import manager.desiresdesigner.twitter.com.HashMapManager;
import manager.desiresdesigner.twitter.com.Manager;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author desiresdesigner
 * @since 3/11/14
 */
public class ShardServer  extends AbstractHandler {
    HashMapManager shardManager;

    public ShardServer(){
        shardManager = new HashMapManager();

    }

    /*public ShardServer(String dataStorageName, String pointerStorageName, String freeSpaceStorageName){
        shardManager = new Manager(dataStorageName, pointerStorageName, freeSpaceStorageName);
    }*/

    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException
    {
        baseRequest.setHandled(true);

        String command = request.getHeader("command");
        String key = request.getHeader("key");
        String value = request.getHeader("value");

        String responseBody = "4";

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

        System.out.println("res: " + responseBody);
        response.getWriter().print(responseBody);
    }
}
