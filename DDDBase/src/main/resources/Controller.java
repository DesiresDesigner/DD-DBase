import java.io.*;
import java.util.*;

/**
 * @author desiresdesigner
 * @since 1/6/14
 */
public class Controller {
    final File dataStorage;
    final File pointerStorage;
    final File freeSpace;
    private Map <String, Long> keyPointers;

    Controller(){
        dataStorage = new File("data");
        pointerStorage = new File("pointers");
        freeSpace = new File("free");

        keyPointers = new HashMap();
        gatherKeyPointers();
    }

    /*Controller(String dataStorage, String pointerStorage){
        this.dataStorage = new File(dataStorage);
        this.pointerStorage = new File(pointerStorage);
        keyPointers = new HashMap();
    }*/

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

    public void printMapa(){
        for (String key : keyPointers.keySet()) {
            System.out.println(key + " - " + keyPointers.get(key) + ";");
        }
    }

    public boolean addValue(String key, String value) {
        if (keyPointers.containsKey(key)){
            System.out.println("This key is already exist");
            return false;
        }
        PrintWriter data;
        PrintWriter pointers;
        long dataPosition = dataStorage.length();// + 1;
        try {
            data = new PrintWriter(new FileWriter(dataStorage, true));
            pointers = new PrintWriter(new FileWriter(pointerStorage, true));
        } catch (Exception e) {
            System.out.println("Can't open data storage or pointers storage");
            return false;
        }
        try{
            data.append(value + ";");
            pointers.append(key + "-" + dataPosition + ";");
            keyPointers.put(key, dataPosition);
        } catch (Exception e){
            System.out.println("Write error");
            return false;
        }
        finally {
            data.close();
            pointers.close();
        }

        return true;
    }

    public String getValue(String key) throws NoSuchElementException, RuntimeException{
        if (!keyPointers.containsKey(key))
            throw new NoSuchElementException();
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

    public boolean deleteValue(String key) throws IOException {
        if (!keyPointers.containsKey(key))
            throw new NoSuchElementException();
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
            System.out.println("Can't save changes");
            return false;
        }
        try{
            free.append(position + "-" + size + ";");
        } catch (Exception e){
            System.out.println("Write error");
            return false;
        }
        finally {
            free.close();
            data.close();
        }
        keyPointers.remove(key);
        if (!rewriteKeys()){
            System.out.println("Write error");
            return false;
        }
        return false;
    }

    private boolean rewriteKeys(){
        PrintWriter keys;
        try {
            keys = new PrintWriter(pointerStorage); //new FileWriter(pointerStorage, true)
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
}
