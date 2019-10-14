import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class ModificarDesvioDeEventosDeConexionEnFolder {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToResume = new File(args[0]);
        File outputFolder = new File(args[1]);
        long media = Long.parseLong(args[2]);
        long desvio = Long.parseLong(args[3]);
        try {
            modificarForPath(filesToResume, outputFolder, media, desvio);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void modificarForPath(File filesToResume, File outputFolder,long media,long desvio) throws FileNotFoundException {
        if (filesToResume.isDirectory()) {
            for (File file : filesToResume.listFiles()) {
                modificarForPath(file, outputFolder, media, desvio);
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
            ModificarDesvioDeEventosDeConexion.MEDIA=media;
            ModificarDesvioDeEventosDeConexion.DESVIO=desvio;
            ModificarDesvioDeEventosDeConexion.main(arg);
            System.setOut(console);
            System.out.println("Finished resume of "+name);
        }
    }
}
