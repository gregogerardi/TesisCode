import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class AverageForEventsInFolder {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesWithEvents = new File(args[0]);
        File fileWithNodes = new File(args[1]);
        File outputFile = new File(args[2]);
        try {
            HashMap<String, Long> averagesPorDevice = new HashMap<>();
            for (File file : filesWithEvents.listFiles()) {
                loadAverages(file, averagesPorDevice);
            }
            String name = fileWithNodes.getName().substring(0, fileWithNodes.getName().lastIndexOf('.'));
            System.out.println("Starting average overwriting of devices in " + name);
            PrintStream console = System.out;
            FileOutputStream fos = new FileOutputStream(outputFile);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);

            overwriteNodes(fileWithNodes,averagesPorDevice);
            System.setOut(console);
            System.out.println("Finished resume of " + name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void overwriteNodes(File fileWithNodes, HashMap<String, Long> averagesPorDevice) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileWithNodes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);    //file to be scanned
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            String[] splitLine = line.split(";");
            String[] splitConnectionScoreCalculator=splitLine[5].split("-");
            splitConnectionScoreCalculator[1]=String.valueOf(averagesPorDevice.get(splitLine[0]));
            splitLine[5]=String.join("-",splitConnectionScoreCalculator);
            System.out.println(String.join(";",splitLine));
        }
        scanner.close();
    }
    private static void loadAverages(File fileWithEvents, HashMap<String, Long> averagesPorDevice) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileWithEvents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);    //file to be scanned
        List<Long> duracionEventos = new ArrayList<>();
        long connectionTime = 0;
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
                duracionEventos.add(Long.parseLong(newline[1]) - connectionTime);
            }
        }
        scanner.close();
        long total = 0;
        for (int i = 0; i < duracionEventos.size(); i++) {
            total += duracionEventos.get(i);
        }
        long promedio = total / duracionEventos.size();
        String deviceId = fileWithEvents.getName().substring(0, fileWithEvents.getName().lastIndexOf('.'));
        averagesPorDevice.put(deviceId, promedio);
    }
}
