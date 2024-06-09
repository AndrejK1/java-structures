package learning.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BracketsCheckerTest {

    @Test
    void testBrackets() {
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets(")"));
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets("[)"));
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets("{)}"));
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets("{34)}"));
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets("[{3(4)}]}"));
        Assertions.assertFalse(BracketsChecker.haveCorrectBrackets("(([{3(4)}]{})"));
        Assertions.assertTrue(BracketsChecker.haveCorrectBrackets("{}"));
        Assertions.assertTrue(BracketsChecker.haveCorrectBrackets("{}[]()"));
        Assertions.assertTrue(BracketsChecker.haveCorrectBrackets("({4}-[5]*3)"));
        Assertions.assertTrue(BracketsChecker.haveCorrectBrackets("([][({4})-[5]]*3)"));
        Assertions.assertTrue(BracketsChecker.haveCorrectBrackets("(1 + 34 * (34 * 3) - { -[3 + 4] })"));
    }

}
