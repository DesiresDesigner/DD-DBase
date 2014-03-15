package DDDB.desiresdesigner.twitter.com;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author desiresdesigner
 * @since 3/10/14
 */
public class DDDBBigTest {

    public static void main(String args[]) throws IOException {

        /*HttpPost post = new HttpPost("/test");
        HttpClient client = HttpClientBuilder.create().build();
        HttpHost httpHost = new HttpHost("localhost", 8080);

        String requestBody;
        HttpResponse execute;
        ResponseHandler<String> handler;
        String response;

        for(int i = 0; i < 100; ++i){
            System.out.println(System.nanoTime());
            post.setHeader("key", "key" + i);
            post.setHeader("value", "value" + i);
            post.setHeader("command", "add");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            handler.handleResponse(execute);
        }

        for(int i = 0; i < 100; ++i){
            System.out.println(System.nanoTime());
            post.setHeader("command", "clear");
            requestBody = "Adding value";
            post.setEntity(new StringEntity(requestBody));
            execute = client.execute(httpHost, post);
            handler = new BasicResponseHandler();
            handler.handleResponse(execute);
        }*/


        DDDB db = new DDDB();
        Set<String> keys = new HashSet();
        long startTime;

        db.addShard("192.168.50.174", 8100);
        //db.addShard("localhost", 8100);
        System.out.println("1 Shard");
        System.out.println("Add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            keys.add(key);
            db.addValue(key, "value" + i);
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "newvalue");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            if (!db.getValue(key).equals("newvalue")){
                throw new IOException();
            }
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("del");
        for (String key : keys){
            startTime = System.nanoTime();
            db.delValue(key);
            System.out.println(System.nanoTime() - startTime);
        }

        db.clear();
        keys.clear();
        db.addShard("192.168.50.174", 8200);
        //db.addShard("localhost", 8200);
        System.out.println("2 Shard");
        System.out.println("Add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            keys.add(key);
            db.addValue(key, "value" + i);System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "newvalue");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            if (!db.getValue(key).equals("newvalue")){
                throw new IOException();
            }System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("del");
        for (String key : keys){
            startTime = System.nanoTime();
            db.delValue(key);
            System.out.println(System.nanoTime() - startTime);
        }

        db.clear();
        keys.clear();
        db.addShard("192.168.50.187", 8100);
        System.out.println("3 Shard");
        System.out.println("Add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            keys.add(key);
            db.addValue(key, "value" + i);System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "newvalue");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            if (!db.getValue(key).equals("newvalue")){
                throw new IOException();
            }System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("del");
        for (String key : keys){
            startTime = System.nanoTime();
            db.delValue(key);
            System.out.println(System.nanoTime() - startTime);
        }

        db.clear();
        keys.clear();
        db.addShard("192.168.50.187", 8200);
        System.out.println("4 Shard");
        System.out.println("Add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            keys.add(key);
            db.addValue(key, "value" + i);System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "newvalue");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            if (!db.getValue(key).equals("newvalue")){
                throw new IOException();
            }System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("del");
        for (String key : keys){
            startTime = System.nanoTime();
            db.delValue(key);
            System.out.println(System.nanoTime() - startTime);
        }

        db.clear();
    }
}
