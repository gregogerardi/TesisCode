import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DispositivosConectadosPerTime {

    public static String formatTime(long time){
        long hs=time/3600;
        long rest = time - hs*3600;
        long minutes= rest/60;
        rest = rest - minutes*60;
        long seconds = rest;
        return ""+(hs<10?"0":"")+hs+":"+(minutes<10?"0":"")+minutes+":"+(seconds<10?"0":"")+seconds;
    }

    public static int TIME_WINDOW = 3600000;
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
        int timeOfLastLog = 0;
        int hourOfSimulation = 0;
        int amountOfDevicesConnected = 0;
        int amountOfDevices = 0;
        int amountOfConections = 0;
        int amountOfDisconections = 0;
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
                amountOfConections++;
                amountOfDevicesConnected++;
            }
            if (line.indexOf("Device is out from the network") != -1) {
                amountOfDevicesConnected--;
                amountOfDisconections++;
            }
            if ((Integer.parseInt(time) - timeOfLastLog) > TIME_WINDOW) {
                int numberOfWindowsJumped = (Integer.parseInt(time) - timeOfLastLog) / TIME_WINDOW;
                for (int i = 0; i < numberOfWindowsJumped; i++){
                    hourOfSimulation++;
                    if (hourOfSimulation < 24 * 3600) {
                        //if (true) {
                        //System.out.println("" + formatTime(hourOfSimulation) + ";" + amountOfDevicesConnected+ ";" + amountOfConections+ ";" + amountOfDisconections);
                        System.out.println("" + formatTime(hourOfSimulation) + ";" + amountOfDevicesConnected);
                    }
                }
                timeOfLastLog += TIME_WINDOW * ((Integer.parseInt(time) - timeOfLastLog) / TIME_WINDOW) ;
                amountOfConections=0;
                amountOfDisconections=0;
            }
        }
        scanner.close();
    }
}
