import java.io.*;
import java.util.Scanner;


public class ComparativaDeCompletitud {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToResume = new File(args[0]);
        File outputFile = new File(args[1]);
        System.out.println("Starting comparativa" );
        PrintStream console = System.out;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
        try {
            if (filesToResume.isDirectory()) {
                for (File file : filesToResume.listFiles()) {
                    if (!file.isDirectory()) {
                        compareForPath(file);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(console);
        System.out.println("Finished comparativa");
    }
    private static void compareForPath(File fileToCompare) throws FileNotFoundException {
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(fileToCompare);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(fis);    //file to be scanned
        String recibidos = "";
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.contains("Cantidad total de trabajos recibidos:")){
                recibidos=line.split("Cantidad total de trabajos recibidos:")[1].trim();
            }
            if (line.contains("Cantidad total de trabajos completados:")){
                System.out.println(fileToCompare.getName().split("-")[1]+";"+fileToCompare.getName().split("DesconexionesMasVariables")[1].substring(0, fileToCompare.getName().split("DesconexionesMasVariables")[1].lastIndexOf('.'))+";"+line.split("Cantidad total de trabajos completados:")[1].trim()+";"+recibidos);
            }

        }
        scanner.close();
    }
}
