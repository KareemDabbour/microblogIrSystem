package com.csi4107;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Document {
    private String id;
    private List<String> tokens;
    private Set<String> uniqueTokens;

    public Document(String line) {
        String[] s = line.split("\t");
        if (s.length >= 2) {
            this.id = s[0];
            this.tokens = Tokenizer.tokenizeString(s[1]);
            this.uniqueTokens = new HashSet<>(this.tokens);
        }
    }

    public Document(String id, List<String> tokens) {
        this.id = id;
        this.tokens = tokens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public Set<String> getUniqueTokens() {
        return uniqueTokens;
    }

    public void setUniqueTokens(Set<String> uniqueTokens) {
        this.uniqueTokens = uniqueTokens;
    }

    @Override
    public String toString() {
        return "DocId: " + this.id + ", Tokens: " + this.tokens.toString();
    }
}
