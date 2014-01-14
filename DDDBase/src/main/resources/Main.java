import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author desiresdesigner
 * @since 1/6/14
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Controller testController = new Controller();
        testController.addValue("101", "Masha");
        System.out.println(testController.getValue("101"));
        /*try {
            RandomAccessFile invFile = new RandomAccessFile("test", "rw");
            invFile.seek(5);
            invFile.write("LOLKA".getBytes());
        } catch (FileNotFoundException notFound) {
            throw new FileNotFoundException();
        } catch (IOException io) {
            throw io;
        }*/
    }

}
