package learning.examples.spell;

import additional.Pair;
import learning.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SpellCheckerTest {

    @Test
    void testFindSimilar() {
        SpellChecker spellChecker = new SpellChecker(List.of("cat", "rat", "bat", "mug", "hug", "bed", "best"));

        List<Pair<Integer, String>> badSimilar = spellChecker.findSimilar("bae");

        Pair<Integer, String> bestMatches = badSimilar.get(0);
        Assertions.assertEquals(Integer.valueOf(1), bestMatches.getKey());
        Assertions.assertEquals("bat", bestMatches.getValue());
    }

    @Test
    void testFindSimilarWithinEnglishDictionary() {
        int size = 20;

        SpellChecker spellChecker = new SpellChecker(TestUtil.loadFileLines("/frequent_words.txt"));

        List<Pair<Integer, String>> bestMatches = spellChecker.findSimilar("spesial", size);

        Assertions.assertEquals(size, bestMatches.size());

        Pair<Integer, String> similarWord = bestMatches.getFirst();
        Assertions.assertEquals(Integer.valueOf(1), similarWord.getKey());
        Assertions.assertEquals("special", similarWord.getValue());
    }
}