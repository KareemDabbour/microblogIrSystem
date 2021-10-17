package com.csi4107;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Query {
    private String queryId;
    private List<String> queryTokens;
    private Set<String> uniqueTokens;
    private int maxFreq;

    public int getMaxFreq() {
        return maxFreq;
    }

    public void setMaxFreq(int maxFreq) {
        this.maxFreq = maxFreq;
    }

    public Query(String queryId, String query) {
        this.queryId = queryId;
        if (query != null && !query.isEmpty()) {
            this.queryTokens = Tokenizer.tokenizeString(query);
            this.maxFreq = 0;
            this.uniqueTokens = new HashSet<String>(this.queryTokens);
            for (String uniqueToken : this.uniqueTokens) {
                int d = Collections.frequency(this.queryTokens, uniqueToken);
                if (d > this.maxFreq) {
                    this.maxFreq = d;
                }
            }
        }
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public Set<String> getUniqueTokens() {
        return this.uniqueTokens;
    }

    public void setUniqueTokens(Set<String> uniqueTokens) {
        this.uniqueTokens = uniqueTokens;
    }

    public List<String> getQueryTokens() {
        return queryTokens;
    }

    public void setQueryTokens(List<String> queryTokens) {
        this.queryTokens = queryTokens;
    }

    @Override
    public String toString() {
        return "(Query Num: " + this.queryId + " Query Tokens: " + this.queryTokens + ")";
    }
}