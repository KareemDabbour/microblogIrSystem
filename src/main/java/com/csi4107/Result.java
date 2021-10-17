package com.csi4107;

import java.io.FileWriter;
import java.util.Map;

public class Result {
    public static void saveToFile(Map<String, Map<String, Double>> results) {
        try (FileWriter resFile = new FileWriter("./src/main/results/Results.txt")) {
            int i = 0;
            for (String queryId : results.keySet()) {
                if (results.get(queryId) == null) {
                    continue;
                }
                int rank = 0;
                i++;
                for (Map.Entry<String, Double> entry : results.get(queryId).entrySet()) {
                    String resFileString = i + "\tQ0\t" + entry.getKey() + "\t" + ++rank + "\t" + entry.getValue()
                            + "\tmyRun\n";
                    resFile.write(resFileString);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: saving results to file: " + e.getMessage());
            System.out.println("Exiting ... ");
            System.exit(1);
        }
    }
}
