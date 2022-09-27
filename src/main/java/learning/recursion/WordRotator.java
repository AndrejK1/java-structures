package learning.recursion;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WordRotator {
    private final String word;

    public WordRotator(String word) {
        this.word = word.trim().toLowerCase();
    }

    public Set<String> extractAllWords() {
        return extractAllWords(word, true);
    }

    public Set<String> extractAnagrams() {
        return extractAllWords(word, false);
    }

    private Set<String> extractAllWords(String word, boolean includeParts) {
        if (word.length() == 1) {
            return Collections.singleton(word);
        }

        Set<String> result = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            char firstChar = word.charAt(0);

            extractAllWords(word.substring(1), includeParts).forEach(w -> {
                result.add(firstChar + w);

                if (includeParts) {
                    result.add(w);
                }

            });

            word = shiftWord(word);
        }

        return result;
    }

    private String shiftWord(String word) {
        if (word.length() <= 1) {
            return word;
        }

        return word.substring(1) + word.charAt(0);
    }
}
