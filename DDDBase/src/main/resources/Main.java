/**
 * @author desiresdesigner
 * @since 1/6/14
 */
public class Main {
    public static void main(String[] args) {
        Controller testController = new Controller();
        try{
            System.out.println(testController.getValue("3"));
            System.out.println(testController.getValue("6"));
        } catch(Exception e) {
            System.out.println("Error");
        }
    }

}
