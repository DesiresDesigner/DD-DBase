import java.io.*;

/**
 * @author desiresdesigner
 * @since 1/6/14
 */
public class Controller {
    final File dataStorage;
    final File pointerStorage;
    
    Controller(){
        dataStorage = new File("data");
        pointerStorage = new File("pointers");
    }

    Controller(String dataStorage, String pointerStorage){
        this.dataStorage = new File(dataStorage);
        this.pointerStorage = new File(pointerStorage);
    }

    
    public boolean addValue(String key, String value) {
        PrintWriter data;
        PrintWriter pointers;
        long dataPosition = dataStorage.length();
        try {
            data = new PrintWriter(new FileWriter(dataStorage, true)); //dataStorage.getAbsoluteFile()
            pointers = new PrintWriter(new FileWriter(pointerStorage, true)); //pointerStorage.getAbsoluteFile()
        } catch (Exception e) {
            System.out.println("Can't open data storage or pointers storage");
            return false;
        }
        try{
            data.append(value + ";");
            pointers.append(key + "-" + dataPosition + ";");
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
}
