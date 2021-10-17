package com.csi4107;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.csi4107.Index.IndexEntry;

public class Querier {
    private static Map<String, Double> docVectLengths = new HashMap<>();

    public static Map<String, Double> makeQuery(Query query, Index index) {

        Map<String, Double> docRankMap = new HashMap<>();
        double queryVecLen = 0.0;
        for (String term : query.getUniqueTokens()) {
            double queryTfIdf = index.getIDF(term)
                    * (Collections.frequency(query.getQueryTokens(), term) / query.getMaxFreq());
            queryVecLen += Math.pow(queryTfIdf, 2);
            for (IndexEntry entry : index.get(term)) {
                double toBeAdded = (queryTfIdf) * (entry.getTermFreq()); // query weight * doc weight for dot product
                if (docRankMap.containsKey(entry.getDocId())) {
                    toBeAdded += docRankMap.get(entry.getDocId());
                }
                docRankMap.put(entry.getDocId(), toBeAdded);
            }
        }
        queryVecLen = Math.sqrt(queryVecLen);
        normalize(docRankMap, queryVecLen);
        return sortByValue(docRankMap, 1000);
    }

    public static Map<String, Map<String, Double>> makeQueries(List<Query> queries, Index index) {
        Map<String, Map<String, Double>> qMap = new LinkedHashMap<>();
        for (Query query : queries) {
            qMap.put(query.getQueryId(), Querier.makeQuery(query, index));
        }
        return qMap;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int limit) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }.reversed()// make it decending order
        );
        // Return a map with the correct order largest to smallest
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            if (limit-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void vectorizeDocs(List<Document> docs) {
        for (Document doc : docs) {
            double len = 0.0;
            for (String term : doc.getUniqueTokens()) {
                int termFreq = Collections.frequency(doc.getTokens(), term);
                double wTF = 1 + (Math.log(termFreq) / Math.log(2));

                len += Math.pow(wTF, 2);
            }
            docVectLengths.put(doc.getId(), Math.sqrt(len));
        }

    }

    private static void normalize(Map<String, Double> docRankMap, double queryVecLen) {
        for (Map.Entry<String, Double> entry : docRankMap.entrySet()) {
            docRankMap.put(entry.getKey(), entry.getValue() / (queryVecLen * docVectLengths.get(entry.getKey())));
        }
    }
}
