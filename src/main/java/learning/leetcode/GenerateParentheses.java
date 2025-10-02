package learning.leetcode;

import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> combinations = new ArrayList<>(n);
        generateParenthesisForDepth(combinations, "", n, 0);
        return combinations;
    }

    private void generateParenthesisForDepth(List<String> parentheses, String current, int parenthesesLeft, int parenthesesOpen) {
        if (parenthesesLeft == 0) {
            parentheses.add(current + ")".repeat(Math.max(0, parenthesesOpen)));
            return;
        }

        if (parenthesesLeft > 0) {
            generateParenthesisForDepth(parentheses, current + "(", parenthesesLeft - 1, parenthesesOpen + 1);
        }

        if (parenthesesOpen > 0) {
            generateParenthesisForDepth(parentheses, current + ")", parenthesesLeft, parenthesesOpen - 1);
        }
    }
}
