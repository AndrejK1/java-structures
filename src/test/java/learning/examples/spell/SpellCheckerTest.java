package learning.examples.spell;

import additional.Pair;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;

import static learning.util.TestUtil.loadFileLines;

public class SpellCheckerTest extends TestCase {

    public void testFindSimilar() {
        SpellChecker spellChecker = new SpellChecker(List.of("cat", "rat", "bat", "mug", "hug", "bed", "best"));

        List<Pair<Integer, String>> badSimilar = spellChecker.findSimilar("bae");

        Pair<Integer, String> bestMatches = badSimilar.get(0);
        Assert.assertEquals(Integer.valueOf(1), bestMatches.getKey());
        Assert.assertEquals("bat", bestMatches.getValue());
    }

    public void testFindSimilarWithinEnglishDictionary() {
        int size = 20;

        SpellChecker spellChecker = new SpellChecker(loadFileLines("/frequent_words.txt"));

        List<Pair<Integer, String>> bestMatches = spellChecker.findSimilar("spesial", size);

        Assert.assertEquals(size, bestMatches.size());

        Pair<Integer, String> similarWord = bestMatches.get(0);
        Assert.assertEquals(Integer.valueOf(1), similarWord.getKey());
        Assert.assertEquals("special", similarWord.getValue());
    }
}