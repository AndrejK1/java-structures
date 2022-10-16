package learning.examples.recursion;

import learning.examples.recursion.WordRotator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class WordRotatorTest {

    @Test
    public void testWordRotator() {
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

        Assert.assertEquals(expectedWordsCount, allWords.size());
        Assert.assertEquals(expectedAnagramCount, anagrams.size());
    }
}
