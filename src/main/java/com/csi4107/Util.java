package com.csi4107;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Util {

    private static String reg = "<querytime>(.*)</querytime>\n<querytweettime>(.*)</querytweettime>\n";

    public static List<Query> getQueries() {
        return getQueries("src/main/resources/topics_MB1-49.txt");
    }

    public static List<Query> getQueries(String filePath) {

        List<String> messageList = new ArrayList<String>();
        List<Query> ret = new ArrayList<>();
        String content = "";
        try (Scanner sc = new Scanner(new File(filePath))) {
            content = sc.useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.out.println("ERROR: processing query from file: " + filePath + "\nError: " + e);
            System.out.println("Exiting  ... ");
            System.exit(1);
        }

        content = content.replaceAll("<top>\n", "").replaceAll(reg, "").replaceAll("\n", "");
        messageList.addAll(Arrays.asList(content.split("</top>")));
        messageList.stream().forEach(x -> {
            x = x.replace("<num> Number: ", "").replace("<title>", "").replace("</title>", "").trim();
            String[] a = x.split("</num>");
            ret.add(new Query(a[0].trim(), a[1].trim()));
        });
        return ret;
    }

    public static List<Document> getDocs() {
        return getDocs("src/main/resources/Trec_microblog11.txt");
    }

    public static List<Document> getDocs(String filePath) {
        List<Document> docs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean isBOF = true;
            for (String line; (line = br.readLine()) != null;) {
                if (isBOF) {
                    line = line.replace("\uFEFF", ""); // Removing BOF char
                    isBOF = false;
                }
                Document d = new Document(line);
                if (d.getId() != null && d.getTokens() != null && d.getTokens().size() > 0) {
                    docs.add(d);
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR: processing docs from file: " + filePath + "\nError: " + e);
            System.out.println("Exiting  ... ");
            System.exit(1);
        }
        return docs;
    }
}
