
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ResumenResultadosInFolderDeep2 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        File filesToResume = new File(args[0]);
        File outputFile = new File(args[1]);
        try {
            resumeForPathDeep2(filesToResume, outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void resumeForPathDeep2(File filesToResume, File outputFile) throws FileNotFoundException {
        if (filesToResume.isDirectory()) {
            HashMap<String, String> comparadores = new HashMap<>();
            HashMap<String, String> medias = new HashMap<>();
            HashMap<String, String> incertidumbres = new HashMap<>();
            HashMap<String, String> granularidades = new HashMap<>();
            HashMap<String, String> parentFolders = new HashMap<>();
            comparadores.put("EnhMobility","ESMAS");
            comparadores.put("Mobility","SMAS");
            comparadores.put("EnhSEAS","EnhSEAS");
            comparadores.put("Multi5050","Combinado");
            comparadores.put("ESMASFJ","ESMASEJ");
            medias.put("media30desvio3","30");
            medias.put("media30desvio10","30");
            medias.put("media30desvio15","30");
            medias.put("media60desvio6","60");
            medias.put("media60desvio20","60");
            medias.put("media60desvio30","60");
            medias.put("media90desvio9","90");
            medias.put("media90desvio30","90");
            medias.put("media90desvio45","90");
            incertidumbres.put("media30desvio3","baja");
            incertidumbres.put("media30desvio10","media");
            incertidumbres.put("media30desvio15","alta");
            incertidumbres.put("media60desvio6","baja");
            incertidumbres.put("media60desvio20","media");
            incertidumbres.put("media60desvio30","alta");
            incertidumbres.put("media90desvio9","baja");
            incertidumbres.put("media90desvio30","media");
            incertidumbres.put("media90desvio45","alta");
            granularidades.put("JOBSDesconexionesMasVariablesFullJobs","1");
            granularidades.put("JOBSDesconexionesMasVariablesFullJobs5sec","150");
            granularidades.put("JOBSDesconexionesMasVariablesFullJobs20sec","600");

            PrintStream console = System.out;
            FileOutputStream fos = new FileOutputStream(outputFile);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            for (File file : Objects.requireNonNull(filesToResume.listFiles())) {
                HashMap<String, String> valores = new HashMap<>();
                if (file.isDirectory()) {
                    for (File innerDirectory : Objects.requireNonNull(file.listFiles())) {
                        if (!innerDirectory.getName().startsWith(".")) {
                            String name = innerDirectory.getName().substring(0, innerDirectory.getName().lastIndexOf('.'));
                            valores.put(name, RecuperarPorcetajeDeTrabajosCompletados.getPorcentaje(innerDirectory));
                        }
                    }
                }
                for (Map.Entry<String, String> entry : valores.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    String[] keySections = k.split("-");
                    String comparador = comparadores.get(keySections[0]);
                    String mediaDeConexion = medias.get(file.getName());
                    String incertidumbre = incertidumbres.get(file.getName());
                    String granularidad = granularidades.get(keySections[keySections.length - 1]);
                    System.out.println(
                            comparador + ";" +
                                    mediaDeConexion + ";" +
                                    incertidumbre + ";" +
                                    granularidad + ";" +
                                    v);
                }
            }
            System.setOut(console);
            System.out.println("Finished resume in " + outputFile.getName());
        }
    }
}
