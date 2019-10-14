import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JobsQuadJobs {


    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("missing input file");
        }
        String infile = args[0];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(infile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);    //file to be scanned
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("#") == 0 || line.length() == 0) continue;
            System.out.println(line);
            System.out.println("1" + line);
            System.out.println("2" + line);
            System.out.println("3" + line);
        }
        scanner.close();
    }
}
