package learning.examples;

import org.junit.Assert;
import org.junit.Test;

public class BracketsCheckerTest {

    @Test
    public void testBrackets() {
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets(")"));
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets("[)"));
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets("{)}"));
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets("{34)}"));
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets("[{3(4)}]}"));
        Assert.assertFalse(BracketsChecker.haveCorrectBrackets("(([{3(4)}]{})"));
        Assert.assertTrue(BracketsChecker.haveCorrectBrackets("{}"));
        Assert.assertTrue(BracketsChecker.haveCorrectBrackets("{}[]()"));
        Assert.assertTrue(BracketsChecker.haveCorrectBrackets("({4}-[5]*3)"));
        Assert.assertTrue(BracketsChecker.haveCorrectBrackets("([][({4})-[5]]*3)"));
        Assert.assertTrue(BracketsChecker.haveCorrectBrackets("(1 + 34 * (34 * 3) - { -[3 + 4] })"));
    }

}
