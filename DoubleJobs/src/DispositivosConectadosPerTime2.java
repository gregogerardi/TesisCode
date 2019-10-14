import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DispositivosConectadosPerTime2 {

    public static String formatTime(long time){
        long hs=time/3600000;
        long rest = time - hs*3600000;
        long minutes= rest/60000;
        rest = rest - minutes*60000;
        long seconds = rest/1000;
        rest = rest - seconds*1000;
        long milis = rest;
        return ""+hs+":"+minutes+":"+seconds+"."+milis;
    }
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
        int amountOfDevices = 0;
        boolean simulando = false;
        //System.out.println("# hora de simulacion; cantidad total de dispositivos");
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("Device started") != -1) {
                simulando = true;
            }
            if (line.indexOf("The simulator has executed") != -1) {
                simulando = false;
            }
            if (!simulando) continue;
            String[] newline = line.split(";");
            String time = newline[0];
            if (line.indexOf("Device is into the network") != -1) {
                amountOfDevices++;
                System.out.println("" + formatTime(Long.parseLong(time)) + ";" + amountOfDevices);
            }
            if (line.indexOf("Device is out from the network") != -1) {
                amountOfDevices--;
                System.out.println("" + formatTime(Long.parseLong(time)) + ";" + amountOfDevices);
            }
        }
        scanner.close();
        System.out.println("*********************");
    }
}
