package com.csi4107;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Indexer {
    private Map<String, LinkedList<IndexEntry>> index;

    public Indexer() {
        index = new HashMap<>();
    }

    public void index(String docId, List<String> tokens) {
        IndexEntry entry;
        Set<String> uniqueTokens = new HashSet<String>(tokens);
        for (String uToken : uniqueTokens) {
            entry = new IndexEntry();
            entry.setDocId(docId);
            entry.setTermFreq(Collections.frequency(tokens, uToken));
            if (this.index.get(uToken) == null) {
                this.index.put(uToken, new LinkedList<>());
            }
            this.index.get(uToken).push(entry);
        }
    }

    public int getDocumentFreq(String term) {
        int ret = 0;
        if (this.index.get(term) != null) {
            ret = this.index.get(term).size();
        }
        return ret;
    }

    public Map<String, LinkedList<IndexEntry>> getIndex() {
        return index;
    }

    public void setIndex(Map<String, LinkedList<IndexEntry>> index) {
        this.index = index;
    }

    // Class for index entry to pair DocId and Term Freq
    static class IndexEntry {
        String docId;
        int termFreq;

        public IndexEntry(String docId, int termFreq) {
            this.docId = docId;
            this.termFreq = termFreq;
        }

        public IndexEntry() {
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public int getTermFreq() {
            return termFreq;
        }

        public void setTermFreq(int termFreq) {
            this.termFreq = termFreq;
        }

        @Override
        public String toString() {
            return "DocId: " + this.docId + ", TermFreq: " + this.termFreq;
        }
    }
}
