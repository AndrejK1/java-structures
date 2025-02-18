package learning.examples;


import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class PiBld {
    private static final int MAX_NUMBER = 24;

    public static void main(String[] args) {
        Set<String> solutionsForCombination = new PiBld().findSolutionsForCombination("1415926535");
        log.info("Solution count: {}", solutionsForCombination.size());
        log.info("Solutions:\n{}", solutionsForCombination);
    }

    public Set<String> findSolutionsForCombination(String combination) {
        Set<String> solutions = new HashSet<>();

        backtrace(solutions, combination, "", 0, 1);
        backtrace(solutions, combination, "", 0, 2);

        return solutions;
    }

    private void backtrace(Set<String> solutions, String combination, String currentSolution, int startIdx, int length) {

        if (startIdx + length > combination.length()) {
            return;
        }

        int nextNum = Integer.parseInt(combination.substring(startIdx, startIdx + length));

        if (nextNum > MAX_NUMBER || nextNum < 1) {
            return;
        }

        if (startIdx + length == combination.length()) {
            solutions.add(currentSolution + "," + nextNum);
        }

        currentSolution += (currentSolution.isEmpty() ? "" : ",") + nextNum;

        backtrace(solutions, combination, currentSolution, startIdx + length, 2);
        backtrace(solutions, combination, currentSolution, startIdx + length, 1);
    }

}
