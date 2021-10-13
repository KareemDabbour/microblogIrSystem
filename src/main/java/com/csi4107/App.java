package com.csi4107;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.csi4107.Indexer.IndexEntry;

public final class App {
    private App() {
    }

    private static List<Document> docs = new ArrayList<>();

    private static void test() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Trec_microblog11.txt"))) {// ))){
            for (String line; (line = br.readLine()) != null;) {
                Document d = new Document(line);
                if (d.getId() != null && d.getTokens() != null && d.getTokens().size() > 0) {
                    docs.add(d);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        test();
        List<String> s = Tokenizer.tokenizeString(
                "McEntee: Utah Minuteman cites Giffords shooting in canceling rally:  By Peg McEntee Tribune Columnist  The Utah... http://dlvr.it/F1HJJ");
        System.out.println(docs.get(0));
        Indexer indexer = new Indexer();
        // indexer.index("1", s); // FOR TESTING
        for (Document document : docs) {
            indexer.index(document.getId(), document.getTokens());
        }

        // Print the first 10 index entries.
        int i = 0;
        for (Map.Entry<String, LinkedList<IndexEntry>> entry : indexer.getIndex().entrySet()) {
            System.out.println(entry.toString());
            if (i++ > 10)
                break;
        }
    }
}
