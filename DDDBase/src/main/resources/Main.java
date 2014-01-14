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
        testController.addValue("1", "Artemiy");
        testController.addValue("2", "Kate");
        testController.addValue("3", "Misha");
        testController.addValue("4", "Natalia");
        testController.addValue("5", "Tatiana");
        testController.addValue("6", "Vitaliy");
        System.out.println(testController.getValue("1"));
        System.out.println(testController.getValue("2"));
        System.out.println(testController.getValue("3"));
        System.out.println(testController.getValue("4"));
        System.out.println(testController.getValue("5"));
        System.out.println(testController.getValue("6"));
        testController.deleteValue("4");
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
