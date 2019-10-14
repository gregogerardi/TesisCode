import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JobsFilterPerTime {


    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("missing input file");
        }
        String infile = args[0];
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(infile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(fis);    //file to be scanned
        String inputTime = "";
        String inputTime2 = "";
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("#") == 0 || line.length() == 0) continue;
            String[] newline = line.split(";");
            String time = newline[2];
            if(Integer.parseInt(time)>(3600*1000*13)){
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
