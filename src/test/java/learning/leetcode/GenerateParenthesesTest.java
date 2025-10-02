package learning.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenerateParenthesesTest {

    @Test
    void generateParenthesis() {
        checkSize(1, 1);
        checkSize(2, 2);
        checkSize(3, 5);
        checkSize(5, 42);
        checkSize(6, 132);
        checkSize(7, 429);
        checkSize(8, 1430);
        checkSize(9, 4862);
        checkSize(10, 16796);
    }

    private void checkSize(int parenthesisSize, int listSize) {
        Assertions.assertEquals(listSize, new GenerateParentheses().generateParenthesis(parenthesisSize).size());
    }
}