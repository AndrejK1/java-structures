package learning.examples;

import learning.stack.LinkedStack;
import learning.stack.Stack;

import java.util.HashMap;
import java.util.Map;

public class BracketsChecker {
    private static final BracketsChecker BRACKETS_CHECKER = new BracketsChecker();

    public static boolean haveCorrectBrackets(String input) {
        return BRACKETS_CHECKER.innerHaveCorrectBrackets(input);
    }

    private final Stack<Character> chars;
    private final Map<Character, Character> brackets;

    private BracketsChecker() {
        chars = new LinkedStack<>();
        brackets = new HashMap<>(3);
        brackets.put('{', '}');
        brackets.put('(', ')');
        brackets.put('[', ']');
    }

    private boolean innerHaveCorrectBrackets(String input) {
        chars.clear();

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);

            if (brackets.containsKey(character)) {
                chars.push(character);
                continue;
            }

            if (brackets.containsValue(character) &&
                    (chars.isEmpty() || !brackets.get(chars.pop()).equals(character))
            ) {
                return false;
            }
        }

        return chars.isEmpty();
    }
}
