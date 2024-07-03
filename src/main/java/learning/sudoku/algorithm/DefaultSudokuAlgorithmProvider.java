package learning.sudoku.algorithm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultSudokuAlgorithmProvider {

    public static List<SudokuAlgorithm> getDefaultSudokuAlgorithms() {
        return Stream.of(
                        new SinglePositionCheck(),
                        new HiddenSingleAndPointingGroupsCheck(),
                        new ObviousAndHiddenGroupCheck()
                )
                .sorted(Comparator.comparing(SudokuAlgorithm::getPriority))
                .toList();
    }
}
