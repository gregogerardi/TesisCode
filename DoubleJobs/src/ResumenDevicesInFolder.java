import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class ResumenDevicesInFolder {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToResume = new File(args[0]);
        File outputFolder = new File(args[1]);
        try {
            resumeForPath(filesToResume, outputFolder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void resumeForPath(File filesToResume, File outputFolder) throws FileNotFoundException {
        if (filesToResume.isDirectory()) {
            for (File file : filesToResume.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    File newOutFolder = new File(outputFolder + "/" + filesToResume.getName());
                    resumeForPath(file, newOutFolder);
                }
            }
        } else {
            String[] arg = new String[]{filesToResume.getAbsolutePath()};
            String name = filesToResume.getName().substring(0, filesToResume.getName().lastIndexOf('.'));
            System.out.println("Starting resuming of devices of "+name);
            PrintStream console = System.out;
            File file = new File(outputFolder+"/"+name+".log");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            DispositivosConectadosPerTime.TIME_WINDOW=1000;
            DispositivosConectadosPerTime.main(arg);
            System.setOut(console);
            System.out.println("Finished resume of "+name);
        }
    }
}
