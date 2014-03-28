package tcpDDDB.desiresdesigner.twitter.com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author desiresdesigner
 * @since 3/27/14
 */
public class Client {

    static public void main(String args[]) throws IOException {
        DDDB db = new DDDB();
        long startTime;
        Set<String> keys = new HashSet();

        db.addShard("localhost", 8100);
        //db.addShard("192.168.4.92", 8000);
        System.out.println("1 Shard");
        System.out.println("add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            String value = key;
            keys.add(key);

            System.out.println(System.currentTimeMillis() + ": Sending request...");

            db.addValue(key, value);

            System.out.println(System.currentTimeMillis() + ": Have a response!");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "lol");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            db.getValue(key);
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

        db.addShard("192.168.4.93", 8000);
        System.out.println("2 Shard");
        System.out.println("add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            String value = key;
            keys.add(key);
            db.addValue(key, value);
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "lol");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            db.getValue(key);
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

        db.addShard("192.168.4.94", 8000);
        System.out.println("3 Shard");
        System.out.println("add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            String value = key;
            keys.add(key);
            db.addValue(key, value);
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "lol");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            db.getValue(key);
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


        //db.addShard("192.168.4.95", 8000);
        System.out.println("4 Shard");
        System.out.println("add");
        for (int i = 0; i < 1000000; i ++){
            startTime = System.nanoTime();
            String key = UUID.randomUUID().toString();
            String value = key;
            keys.add(key);
            db.addValue(key, value);
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("edit");
        for (String key : keys){
            startTime = System.nanoTime();
            db.editValue(key, "lol");
            System.out.println(System.nanoTime() - startTime);
        }
        System.out.println("get");
        for (String key : keys){
            startTime = System.nanoTime();
            db.getValue(key);
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
    }
}
