import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RecuperarPorcetajeDeTrabajosCompletados {

    public static String getPorcentaje(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String result = "";
        Scanner scanner = new Scanner(fis);    //file to be scanned
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("Porcentaje de trabajos completados") != -1) {
                result = line.split(":")[1].trim();
            }
        }
        scanner.close();
        return result;
    }
}
