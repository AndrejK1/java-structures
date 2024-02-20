package learning.examples.spell;

import additional.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SpellChecker {
    private static final int DEFAULT_RECOMMENDATION_LIMIT = 10;
    private final List<String> vocabulary;
    private final int[][] matrix;

    public SpellChecker(List<String> vocabulary) {
        this.vocabulary = vocabulary;
        int matrixParam = calculateMatrixParam(vocabulary);
        matrix = new int[matrixParam][matrixParam];

        for (int i = 0; i < matrixParam; i++) {
            matrix[0][i] = i;
            matrix[i][0] = i;
        }
    }

    public List<Pair<Integer, String>> findSimilar(String word) {
        return findSimilar(word, DEFAULT_RECOMMENDATION_LIMIT);
    }

    public List<Pair<Integer, String>> findSimilar(String word, int size) {
        if (word.length() > matrix.length - 1) {
            return Collections.emptyList();
        }

        if (vocabulary.contains(word)) {
            return Collections.singletonList(Pair.of(0, word));
        }

        return vocabulary.stream()
                .map(vocWord -> Pair.of(findDiffLength(word, vocWord), vocWord))
                .sorted(Comparator.comparing(Pair::getKey))
                .limit(size)
                .collect(Collectors.toList());
    }

    private int findDiffLength(String word, String compareWord) {
        for (int i = 1; i < compareWord.length() + 1; i++) {
            for (int j = 1; j < word.length() + 1; j++) {
                int substitution = matrix[i - 1][j - 1];

                if (compareWord.charAt(i - 1) != word.charAt(j - 1)) {
                    substitution++;
                }

                int min = findMin(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, substitution);

                matrix[i][j] = min;
            }
        }

        return matrix[compareWord.length()][word.length()];
    }

    private static int findMin(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private static int calculateMatrixParam(List<String> words) {
        return words.stream().mapToInt(String::length).max().orElse(0) + 1;
    }
}
