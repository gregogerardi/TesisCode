
import edu.isistan.seas.Simulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class SimulationInFolder {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToRun = new File(args[0]);
        File outputFolder = new File(args[1]);
        File outputResumeFolder = new File(args[2]);
        try {
            executeForPath(filesToRun, outputFolder);
            resumeForPath(outputFolder, outputResumeFolder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void executeForPath(File filesToRun, File outputFolder) throws FileNotFoundException {
        if (filesToRun.isDirectory()) {
            for (File file : filesToRun.listFiles()) {
                executeForPath(file, outputFolder);
            }
        } else {
            String[] arg = new String[]{filesToRun.getAbsolutePath()};
            String name = filesToRun.getName().substring(0, filesToRun.getName().lastIndexOf('.'));
            System.out.println("Starting excecution of "+name);
            PrintStream console = System.out;
            File file = new File(outputFolder+"/"+name+".log");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            edu.isistan.simulator.Simulation.fullReset();
            Simulation.main(arg);
            System.setOut(console);
            System.out.println("Finished excecution of "+name);
        }
    }
    private static void resumeForPath(File filesToResume, File outputFolder) throws FileNotFoundException {
        if (filesToResume.isDirectory()) {
            for (File file : filesToResume.listFiles()) {
                resumeForPath(file, outputFolder);
            }
        } else {
            String[] arg = new String[]{filesToResume.getAbsolutePath()};
            String name = filesToResume.getName().substring(0, filesToResume.getName().lastIndexOf('.'));
            System.out.println("Starting resuming of "+name);
            PrintStream console = System.out;
            File file = new File(outputFolder+"RESUME-"+name+".log");
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            JobsFinishedPerTime.main(arg);
            System.setOut(console);
            System.out.println("Finished resume of "+name);
        }
    }
}
