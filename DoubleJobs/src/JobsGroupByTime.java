import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JobsGroupByTime {


    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("missing input file");
        }
        String infile = args[0];
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(infile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(fis);    //file to be scanned
        long inputAcum = 0;
        long outputAcum = 0;
        long mipsAcum = 0;
        long mipstotales = 0;
        long amountOfSamples = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("#") == 0 || line.length()==0 || line.startsWith("promedioJobsMips")) continue;
            String[] newline = line.split(";");
            long input =Long.parseLong(newline[3]);
            long mips =Long.parseLong(newline[1]);
            long output =Long.parseLong(newline[4]);
            long now =Long.parseLong(newline[2]);
            inputAcum+=input;
            outputAcum+=output;
            mipsAcum+=mips;
            if ((outputAcum/4)>600){
                System.out.println(""+newline[0]+";"+mipsAcum+";"+now+";"+inputAcum+";"+outputAcum);
                mipstotales+=mipsAcum;
                amountOfSamples++;
                mipsAcum=inputAcum=outputAcum=0;
            }
        }
        long promedioMips = mipstotales/amountOfSamples;
        System.out.println("#promedio: "+promedioMips);
        scanner.close();
    }
}
