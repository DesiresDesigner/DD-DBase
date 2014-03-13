package test.desiresdesigner.twitter.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * @author desiresdesigner
 * @since 3/11/14
 */

public class Handler extends AbstractHandler{
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException
    {
        //System.out.println(request.getHeader("data"));
        //System.out.println(System.currentTimeMillis());
        baseRequest.setHandled(true);
        //response.getWriter().println("Message From Server");

        String command = request.getHeader("command");
        String key = request.getHeader("key");
        String value = request.getHeader("value");

        String responseBody = "0";

        if (command.equals("add")){
            //int res = shardManager.addValue(key, value);
            //responseBody = String.valueOf(res);
            System.out.println("add(" + key + ", " + value + ")");
        }
        else if (command.equals("del")){
            //int res = shardManager.deleteValue(key);
            //responseBody = String.valueOf(res);
            System.out.println("del(" + key + ")");
        }
        else if (command.equals("edit")){
            //int res = shardManager.editValue(key, value);
            //responseBody = String.valueOf(res);
            System.out.println("edit(" + key + ", " + value + ")");
        }
        else if (command.equals("get")){
            //responseBody = shardManager.getValue(key);
            System.out.println("get(" + key + ")");
        }
        else if (command.equals("getKeys")){
            //responseBody = shardManager.getKeys();
            System.out.println("getKeys");
        }
        else if (command.equals("clear")){
            //int res = shardManager.clearDataStorage();
            //responseBody = String.valueOf(res);
            System.out.println("clear");
        }
        else {
            responseBody = "4";
        }

        response.getWriter().println(responseBody);
    }
}

