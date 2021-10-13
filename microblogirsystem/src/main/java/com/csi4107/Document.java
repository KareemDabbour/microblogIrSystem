package com.csi4107;

import java.util.List;

public class Document {
    private String id;
    private List<String> tokens;

    public Document(String line) {
        String[] s = line.split("\t");
        if (s.length >= 2) {
            this.id = s[0];
            this.tokens = Tokenizer.tokenizeString(s[1]);
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

    public void setText(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "DocId: " + this.id + ", Tokens: " + this.tokens.toString();
    }
}
