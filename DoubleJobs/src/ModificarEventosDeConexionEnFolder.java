import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class ModificarEventosDeConexionEnFolder {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToResume = new File(args[0]);
        File outputFolder = new File(args[1]);
        double percetage = Double.parseDouble(args[2]);
        try {
            modificarForPath(filesToResume, outputFolder, percetage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void modificarForPath(File filesToResume, File outputFolder, double percetage) throws FileNotFoundException {
        if (filesToResume.isDirectory()) {
            for (File file : filesToResume.listFiles()) {
                modificarForPath(file, outputFolder, percetage);
            }
        } else {
            String[] arg = new String[]{filesToResume.getAbsolutePath()};
            String name = filesToResume.getName().substring(0, filesToResume.getName().lastIndexOf('.'));
            System.out.println("Starting modificacion de eventos of "+name);
            PrintStream console = System.out;
            File file = new File(outputFolder+"/"+name+".cnf");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            ReduceIntervals.PERCENTAGE=percetage;
            ReduceIntervals.DEFAULT_WINDOWS=Math.round(3600*1500*percetage);
            ReduceIntervals.main(arg);
            System.setOut(console);
            System.out.println("Finished resume of "+name);
        }
    }
}
