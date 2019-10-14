import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JobGeneratorDoubleInterval {


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
            String newTime = time;
            if (inputTime.equals("")){
                inputTime=time;
            }
            if(!inputTime.equals(time)){
                if (inputTime2.equals("")){
                    inputTime2=time;
                }
                if (inputTime2.equals(time)){
                    newTime=inputTime;
                }
                else{
                    inputTime=time;
                    inputTime2="";
                }
            }
            newline[2]=newTime;
            System.out.println(String.join(";",newline));
        }
        scanner.close();
    }
}
