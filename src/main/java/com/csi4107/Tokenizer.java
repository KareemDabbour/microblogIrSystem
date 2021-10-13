package com.csi4107;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private static final String STOP_WORDS_URI = "src/main/resources/StopWords.txt";
    private static final String URL_REGEX = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";
    private static List<String> stopWords;

    static {
        stopWords = new ArrayList<>();
        try {
            stopWords = Files.readAllLines(Paths.get(STOP_WORDS_URI));
            stopWords.add("");
        } catch (Exception e) {
            System.err.println("Stop Words did not load in properly: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * 
     * @param docString
     * @return A list of tokens for given document.
     */
    public static List<String> tokenizeString(String docString) {
        // Filter out URLs, Numbers and Punctuation.
        docString = preprocesString(docString);

        List<String> wordList = new ArrayList<>();

        wordList.addAll(Arrays.asList(docString.split(" ")));
        // Removing all stopwords.
        wordList.removeAll(stopWords);
        // Removing all empty tokens and tokens that are shorter than 3 chars.
        wordList.removeIf(x -> (x.trim().isEmpty() || x.length() < 3));

        return wordList;
    }

    private static String preprocesString(String docString) {
        return docString.toLowerCase() // Making all the letters lower case
                .trim() // Removing leading and trailing white spaces.
                .replaceAll(URL_REGEX, "") // Removing all URLs
                .replaceAll("/", " ") // Split lists made with '/' deliminator
                .replaceAll("\\p{Punct}", "") // Removing punctutaion.
                .replaceAll("\\d", ""); // Removing all numbers.
    }
}
