import java.io.*;


public class RunSimulationsInFolder {
    static int count = 0;
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToUseAsArgument = new File(args[0]);
        File outputFolder = new File(args[1]);
        try {
            runForPath(filesToUseAsArgument, outputFolder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void runForPath(File filesToUseAsArgument, File outputFolder) throws IOException, InterruptedException {
        if (filesToUseAsArgument.isDirectory()) {
            for (File file : filesToUseAsArgument.listFiles()) {
                if (!file.getName().startsWith(".")){
                    File newOutFolder = new File(outputFolder + "/" + filesToUseAsArgument.getName());
                    runForPath(file, newOutFolder);
                }
            }
        } else {
            String[] arg = new String[]{filesToUseAsArgument.getAbsolutePath()};
            String name = filesToUseAsArgument.getName().substring(0, filesToUseAsArgument.getName().lastIndexOf('.'));
            System.out.println("Starting running scenario: " + name);
//            PrintStream console = System.out;
            File file = new File(outputFolder + "/" + name + ".log");
//            FileOutputStream fos = new FileOutputStream(file);
//            PrintStream ps = new PrintStream(fos);
//            System.setOut(ps);
            Process instance = new ProcessBuilder().directory(new File("/Users/gregorio/Documents/TesisCode/MobileGridSimulation"))
                    .command("java",
                            "-jar",
                            "-Xmx8G",
                            "../out/artifacts/MobileGridSimulation_jar/MobileGridSimulation.jar",
                            filesToUseAsArgument.toString()
                    ).redirectOutput(file)
                    .start();
            int exitCode = instance.waitFor();
//            System.setOut(console);
            System.out.println("Finished running scenario: " + name+"with exit code: "+exitCode +". Scenario number: "+ count++);
        }
    }
}
