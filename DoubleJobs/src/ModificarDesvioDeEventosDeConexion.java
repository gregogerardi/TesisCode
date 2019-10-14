import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ModificarDesvioDeEventosDeConexion {


    public static long MEDIA = 30;
    public static long DESVIO = 30;
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
        NormalDistribution normalDistributionToDisconnection = new NormalDistribution(MEDIA,DESVIO);

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
                long delta = (long)normalDistributionToDisconnection.sample();
                if (delta < 0){
                    delta = 0;
                }
                if (delta > MEDIA * 2){
                    delta = MEDIA * 2;
                }
                long newDisconnectionTime = lastConnection + delta;
                System.out.println("LEAVE_NETWORK;" + newDisconnectionTime + ";");
                cerrado=true;
            }
        }
        if (!cerrado){
            long newDisconnectionTime = lastConnection + (long)normalDistributionToDisconnection.sample();
            System.out.println("LEAVE_NETWORK;" + newDisconnectionTime + ";");
        }
        scanner.close();
    }
}