package edu.isistan.nodetranslator;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppenderForFixedScore {
    public static void main(String[] args) {
        File file = new File(args[0]);
        File file2 = new File(args[1]);
        NormalDistribution normalDistribution = new NormalDistribution(Double.parseDouble(args[2]),Double.parseDouble(args[3]));
        try (Scanner scanner = new Scanner(file); FileWriter writer=new FileWriter(file2)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                writer.write(line+(long)normalDistribution.sample()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}