import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizarIntervals {


    public static long MIN_TIME = 0;
    public static long MAX_TIME = 3600*1000*24*2;
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
        List<Long> duracionEventos = new ArrayList<>();
        long connectionTime = 0;
        //System.out.println("# hora de simulacion; cantidad total de trabajos; cantidad total finalizada; diferencia total arribados con total finalizados; trabajos en esta hora; finalizados en esta hora; diferencia en esta hora");
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            //ENTER_NETWORK;34810488;
            //LEAVE_NETWORK;40645373;
            String[] newline = line.split(";");
            String type = newline[0];
            if (type.equals("ENTER_NETWORK")) {
               connectionTime = Integer.parseInt(newline[1]);
            }
            if (type.equals("LEAVE_NETWORK")) {
                duracionEventos.add(Long.parseLong(newline[1])-connectionTime);
            }
        }
        List<Long> tiemposDeConecciones = new ArrayList<>();
        for (int i = 0; i < duracionEventos.size(); i++) {
            long valor = ThreadLocalRandom.current().nextLong(MIN_TIME, MAX_TIME);
            tiemposDeConecciones.add(valor);
        }
        Collections.sort(tiemposDeConecciones);
        long lastEventEnding = 0;
        for (int i = 0; i < tiemposDeConecciones.size(); i++) {
            long connexion = tiemposDeConecciones.get(i);
            if (connexion <= lastEventEnding){
                connexion = lastEventEnding+1;
            }
            System.out.println("ENTER_NETWORK;" + connexion + ";");
            lastEventEnding = connexion+duracionEventos.get(i);
            System.out.println("LEAVE_NETWORK;" + lastEventEnding + ";");
        }
        scanner.close();
    }
}