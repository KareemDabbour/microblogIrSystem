package com.csi4107;

import java.util.List;
import java.util.Map;

public class App {
    /**
     * 
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // Create a new index.
        System.out.println("Creating a new index");
        Index index = new Index();

        // Read the docs from default file
        System.out.println("Processing Documents from file");
        List<Document> docs = Util.getDocs();

        // Index the docs.
        System.out.println("Indexing Documents");
        index.index(docs);

        // Find out each docVector's length
        // and save it in the Querier.
        Querier.vectorizeDocs(docs);

        // Read the queries from file and
        // preproccess them for querying.
        System.out.println("Processing Queries from file");
        List<Query> queries = Util.getQueries();

        // Make the queries.
        System.out.println("Making the queries");
        Map<String, Map<String, Double>> qMap = Querier.makeQueries(queries, index);

        // Save the results to a file.
        Result.saveToFile(qMap);
        System.out.println("Saved results to file ./results/Results.txt");

        System.out.println("Done ... ");
        System.out.println("Exiting  ... ");

    }
}