package learning.examples.recursion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class WordRotatorTest {

    @Test
    void testWordRotator() {
        innerWordRotatorTest("me", 4, 2);
        innerWordRotatorTest("hot", 15, 6);
        innerWordRotatorTest("host", 64, 24);

        innerWordRotatorTest("pop", 8, 3);
        innerWordRotatorTest("toss", 34, 12);
    }

    private void innerWordRotatorTest(String word, int expectedWordsCount, int expectedAnagramCount) {
        WordRotator wordRotator = new WordRotator(word);
        Set<String> allWords = wordRotator.extractAllWords();
        Set<String> anagrams = wordRotator.extractAnagrams();

        Assertions.assertEquals(expectedWordsCount, allWords.size());
        Assertions.assertEquals(expectedAnagramCount, anagrams.size());
    }
}
