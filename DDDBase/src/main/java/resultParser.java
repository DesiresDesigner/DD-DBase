import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author desiresdesigner
 * @since 3/19/14
 */
public class resultParser {

    public static void main(String args[]){
        File fileForParse = new File("/home/desiresdesigner/ITMO/ИсследоваиеАлгоитмовПартионирования/3 HashMap/4del");
        double sum = 0;
        double number = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileForParse.getAbsoluteFile()));
            try {
                String s;
                sum = Integer.parseInt(in.readLine());
                ++number;
                while ((s = in.readLine()) != null) {
                    long newNum = Long.parseLong(s);
                    double difference = Math.abs((sum + newNum)/(number+1) - sum/number);
                    //double difference = ((sum + newNum)/(number+1)) / (sum/number);
                    if (difference < 10000){
                    //if (difference < 2){
                        sum += newNum;
                        ++number;
                    }
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println((double)sum/number);
    }
}
