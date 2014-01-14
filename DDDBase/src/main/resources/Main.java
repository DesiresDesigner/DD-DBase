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
        testController.addValue("1", "Kate");
        testController.addValue("2", "Michail");
        testController.addValue("3", "Artemiy");
        testController.addValue("4", "Tatiana");
        testController.addValue("5", "Vitaliy");
        testController.addValue("6", "Natasha");
        testController.deleteValue("5");
        testController.addValue("101", "Masha");
        System.out.println(testController.getValue("101"));
    }

}
