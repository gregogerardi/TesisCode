import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReduceIntervals {


    public static double PERCENTAGE = 0.66;
    public static long DEFAULT_WINDOWS = 3600*1500 ;
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
        int lastConnection = 0;
        boolean cerrado = false;
        //System.out.println("# hora de simulacion; cantidad total de trabajos; cantidad total finalizada; diferencia total arribados con total finalizados; trabajos en esta hora; finalizados en esta hora; diferencia en esta hora");
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            //ENTER_NETWORK;34810488;
            //LEAVE_NETWORK;40645373;
            String[] newline = line.split(";");
            String type = newline[0];
            if (type.equals("ENTER_NETWORK")) {
                lastConnection = Integer.parseInt(newline[1]);
                System.out.println(line);
                cerrado=false;
            }
            if (type.equals("LEAVE_NETWORK")) {
                String value = String.valueOf((lastConnection + Math.round((Integer.parseInt(newline[1]) - lastConnection) * PERCENTAGE)));
                System.out.println("LEAVE_NETWORK;" + value + ";");
                cerrado=true;
            }
        }
        if (!cerrado){
            String value = String.valueOf((lastConnection + DEFAULT_WINDOWS));
            System.out.println("LEAVE_NETWORK;" + value + ";");
        }
        scanner.close();
    }
}