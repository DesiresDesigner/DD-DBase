package manager.desiresdesigner.twitter.com;

import java.io.*;
import java.util.*;

/**
 * @author desiresdesigner
 * @since 1/6/14
 */

/*
* 1 - key already exists
* 2 - writing error
* 3 - no such element
* 4 - not valid command
*/

public class Manager {
    private final File dataStorage;
    private final File pointerStorage;
    private final File freeSpace;
    private Map <String, Long> keyPointers;
    private Map <Long, Integer> freePointers;

    public Manager(){
        dataStorage = new File("data");
        pointerStorage = new File("pointers");
        freeSpace = new File("free");

        keyPointers = new HashMap();
        freePointers = new HashMap();
        gatherKeyPointers();
        gatherFreePointers();
    }

    public Manager(String dataStorageName, String pointerStorageName, String freeSpaceStorageName){
        dataStorage = new File(dataStorageName);
        pointerStorage = new File(pointerStorageName);
        freeSpace = new File(freeSpaceStorageName);

        keyPointers = new HashMap();
        freePointers = new HashMap();
        gatherKeyPointers();
        gatherFreePointers();
    }

    private boolean gatherKeyPointers(){
        String keys;
        try{
            keys = fileToString(pointerStorage);
        }
        catch (RuntimeException e){
            System.out.println("Can't read pointer storage, perhaps it's empty");
            return false;
        }

        String key = "";
        String position = "";
        boolean keyFLG = true;
        for (char ch : keys.toCharArray()){
            if (ch == '-'){
                keyFLG = false;
                continue;
            }
            else if (ch == ';'){
                keyFLG = true;
                keyPointers.put(key, Long.parseLong(position));
                key = "";
                position = "";
                continue;
            }
            if (keyFLG){
                key += ch;
            } else {
                position += ch;
            }
        }
        return true;
    }

    private boolean gatherFreePointers(){
        String keys;
        try{
            keys = fileToString(freeSpace);
        }
        catch (RuntimeException e){
            System.out.println("Can't read free space storage, perhaps it's empty");
            return false;
        }

        String pos = "";
        String length = "";
        boolean posFLG = true;
        for (char ch : keys.toCharArray()){
            if (ch == '-'){
                posFLG = false;
                continue;
            }
            else if (ch == ';'){
                posFLG = true;
                freePointers.put(Long.parseLong(pos), Integer.parseInt(length));
                pos = "";
                length = "";
                continue;
            }
            if (posFLG){
                pos += ch;
            } else {
                length += ch;
            }
        }
        return true;
    }

    private String fileToString(File file){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private long getPosition(int length){
        long bestPos = 0;
        int bestLeng = 0;
        for (Long pos : freePointers.keySet()){
            int leng = freePointers.get(pos);
            if (length <= leng){
                if (bestLeng == 0 || bestLeng > leng){
                    bestLeng = leng;
                    bestPos = pos;
                }
            }
        }
        if (bestPos == 0)
            bestPos = dataStorage.length();
        return bestPos;
    }

    public int addValue(String key, String value) {
        if (keyPointers.containsKey(key)){
            System.out.println("This key is already exist");
            return 1;
        }
        RandomAccessFile data;
        PrintWriter pointers;
        long dataPosition = getPosition(value.length());
        try {
            data = new RandomAccessFile(dataStorage, "rw");
            pointers = new PrintWriter(new FileWriter(pointerStorage, true));
            data.seek(dataPosition);
            value += ";";
            data.write(value.getBytes());
            pointers.append(key + "-" + dataPosition + ";");
            keyPointers.put(key, dataPosition);
            data.close();
            pointers.close();
        } catch (FileNotFoundException notFound) {
            //System.out.println("Can't open data storage or pointers storage");
            return 2;
            //return false;
        } catch (IOException io) {
            return 2;
            //System.out.println("Writing error");
            //return false;
        }
        freePointers.remove(dataPosition);
        rewriteFreeSpace();

        return 0;
        //return true;
    }

    public String getValue(String key) throws RuntimeException{
        if (!keyPointers.containsKey(key)){
            System.out.println("No such key: " + key);
            return "3";
            //throw new NoSuchElementException();
        }
        Long position = keyPointers.get(key);
        String value = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(dataStorage.getAbsoluteFile()));
            in.skip(position);
            try {
                int c;
                while((c = in.read()) != -1 && (char)c != ';'){
                    value += String.valueOf((char)c);
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return value;
    }

    public int deleteValue(String key) throws IOException {
        if (!keyPointers.containsKey(key))
            return 3;
        long position = keyPointers.get(key);
        int size = 0;
        RandomAccessFile data = new RandomAccessFile(dataStorage, "rw");
        data.seek(position);
        char c;
        while((c = (char)data.readByte()) != ';'){
            ++size;
        }
        PrintWriter free;
        try {
            free = new PrintWriter(new FileWriter(freeSpace, true));
        } catch (Exception e) {
            return 2;
            //System.out.println("Can't save changes");
            //return false;
        }
        try{
            free.append(position + "-" + size + ";");
        } catch (Exception e){
            return 2;
            //System.out.println("Write error");
            //return false;
        }
        finally {
            free.close();
            data.close();
        }
        freePointers.put(position, size);
        keyPointers.remove(key);
        if (!rewriteKeys()){
            return 2;
            //System.out.println("Write error");
            //return false;
        }
        return 0;
        //return false;
    }

    public int editValue(String key, String value) throws IOException {
        int res = deleteValue(key);
        if (res != 0){
            return res;
        }
        return addValue(key, value);
    }

    private boolean rewriteKeys(){
        PrintWriter keys;
        try {
            keys = new PrintWriter(pointerStorage);
        } catch (Exception e) {
            System.out.println("Can't open pointers Storage");
            return false;
        }
        try{
            keys.write("");
            for(String key : keyPointers.keySet()){
                keys.append(key + "-" + keyPointers.get(key) + ";");
            }
        } catch (Exception e){
            System.out.println("Write error");
            return false;
        }
        finally {
            keys.close();
        }
        return true;
    }

    private boolean rewriteFreeSpace(){
        PrintWriter free;
        try {
            free = new PrintWriter(freeSpace); //new FileWriter(pointerStorage, true)
        } catch (Exception e) {
            System.out.println("Can't open free space Storage");
            return false;
        }
        try{
            free.write("");
            for(Long pos : freePointers.keySet()){
                free.append(pos + "-" + freePointers.get(pos) + ";");
            }
        } catch (Exception e){
            System.out.println("Write error");
            return false;
        }
        finally {
            free.close();
        }
        return true;
    }

    public String getKeys(){
        String res = "";
        for (String key : keyPointers.keySet()){
            if (res == ""){
                res = key;
            } else {
                res += "-" + key;
            }
        }
        return res;
    }
}
