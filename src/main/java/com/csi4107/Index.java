package com.csi4107;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Index {
    private Map<String, LinkedList<IndexEntry>> index;
    private int numDocs;

    public Index() {
        index = new HashMap<>();
        this.numDocs = 0;
    }

    public void index(Document doc) {
        IndexEntry entry;
        for (String uToken : doc.getUniqueTokens()) {
            entry = new IndexEntry();
            entry.setDocId(doc.getId());
            entry.setTermFreq(Collections.frequency(doc.getTokens(), uToken));
            if (this.index.get(uToken) == null) {
                this.index.put(uToken, new LinkedList<>());
            }
            this.index.get(uToken).push(entry);
        }
        numDocs++;
    }

    public void index(List<Document> docs) {
        docs.stream().forEach(doc -> this.index(doc));
    }

    public int getDocumentFreq(String term) {
        int ret = 0;
        if (this.index.get(term) != null) {
            ret = this.index.get(term).size();
        }
        return ret;
    }

    public double getIDF(String term) {
        double ret = 0.0;
        if (this.getDocumentFreq(term) != 0)
            ret = Math.log(numDocs / this.getDocumentFreq(term));
        return ret;
    }

    public Map<String, LinkedList<IndexEntry>> getIndex() {
        return index;
    }

    public List<IndexEntry> get(String term) {
        return this.index.get(term);
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

        public double getTermFreq() {
            return termFreq == 0 ? 0.0 : 1 + (Math.log(termFreq) / Math.log(2));
        }

        public void setTermFreq(int termFreq) {
            this.termFreq = termFreq;
        }

        @Override
        public String toString() {
            return "(DocId: " + this.docId + " :: TermFreq: " + this.termFreq + ")";
        }
    }
}
