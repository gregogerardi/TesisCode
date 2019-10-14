import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JobsFinishedPerTime {


    public static int TIME_WINDOW = 3600000;
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("missing input file");
        }
        String infile = args[0];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(infile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);    //file to be scanned
        int timeOfLastLog = 0;
        int hourOfSimulation = 0;
        int amountOfJobs = 0;
        int amountOfJobsThisHour = 0;
        int finished = 0;
        int finishedThisHour = 0;
        boolean simulando = false;
        //System.out.println("# hora de simulacion; cantidad total de trabajos; cantidad total finalizada; diferencia total arribados con total finalizados; trabajos en esta hora; finalizados en esta hora; diferencia en esta hora");
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line.indexOf("Device started") != -1) {
                simulando = true;
            }
            if (line.indexOf("The simulator has executed") != -1) {
                simulando = false;
            }
            if (!simulando) continue;
            String[] newline = line.split(";");
            String time = newline[0];
            if (line.indexOf("Job arrived") != -1) {
                amountOfJobs++;
                amountOfJobsThisHour++;
            }
            if (line.indexOf("Result completely transferred") != -1) {
                finished++;
                finishedThisHour++;
            }
            if ((Integer.parseInt(time) - timeOfLastLog) > TIME_WINDOW) {
                hourOfSimulation++;
                if (hourOfSimulation < 25*(3600/5) && hourOfSimulation > 12*(3600/5)) {
                    System.out.println("" + hourOfSimulation + ";" + amountOfJobs + ";" + finished + ";" + (amountOfJobs - finished) +";"+ amountOfJobsThisHour + ";"+finishedThisHour +";"+  + (amountOfJobsThisHour - finishedThisHour));
                }
                timeOfLastLog += TIME_WINDOW;
                finishedThisHour = 0;
                amountOfJobsThisHour = 0;
            }
        }
        scanner.close();
        System.out.println("*********************");
        System.out.println("Cantidad total de trabajos recibidos: "+amountOfJobs);
        System.out.println("Cantidad total de trabajos completados: "+finished);
        System.out.println("Cantidad total de trabajos perdidos: "+((amountOfJobs-finished)>0?(amountOfJobs-finished):0));
        System.out.println("Porcentaje de trabajos completados: "+(((double)finished)/amountOfJobs)*100);
    }
    /*public static void main(String[] args) {
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
        int batchNumero = 0;
        String timeOfLastBatch = "";
        int amount = 0;
        int finished = 0;
        boolean simulando = false;
        while (scanner.hasNext()&&batchNumero<4200) {
            String line = scanner.nextLine().trim();
            if(line.indexOf("Device started")!=-1){
                simulando = true;
            }
            if(line.indexOf("The simulator has executed")!=-1) {
                simulando = false;
            }
            if (!simulando)continue;
            String[] newline = line.split(";");
            String time = newline[0];
            if (line.indexOf("Job arrived") != -1){
                if(!time.equals(timeOfLastBatch)){
                    timeOfLastBatch=time;
//                    amount=0;
//                    finished=0;
                    batchNumero++;
                    amount=batchNumero<4000?0:amount;
                }
                amount++;
            }
            if (line.indexOf("The device finished the job") != -1&&batchNumero>=4000){
                amount=amount==0?amount:amount-1;
                finished++;
                System.out.println(""+time+";"+amount+";"+finished);
            }
        }
        scanner.close();
    }*/
}
