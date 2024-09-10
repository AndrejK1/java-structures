package learning.sudoku.algorithm;

import learning.sudoku.SudokuSolver;
import learning.sudoku.SudokuUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static learning.sudoku.SudokuUtils.buildLog;
import static learning.sudoku.SudokuUtils.haveIntersection;

@Slf4j
@NoArgsConstructor
public class ObviousAndHiddenGroupCheck extends SudokuPositionGroupAlgorithm {

    @Override
    public String getName() {
        return "Obvious & Hidden Group Finder";
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.INTERMEDIATE;
    }

    @Override
    protected boolean runDeepCheckAtPositions(SudokuSolver sudokuSolver, List<Integer> positionsToCheck) {
        boolean detectedChange = false;

        Map<Integer, List<Integer>> possibleNumbersByPos = positionsToCheck.stream()
                .collect(Collectors.toMap(Function.identity(), sudokuSolver::getPossibleNumbersByPosition));

        Map<Integer, List<Integer>> unsolvedNumbersWithPositions = SudokuUtils.findAllPossiblePositionsForUnsolvedNumbers(possibleNumbersByPos);

        Map<List<Integer>, List<Integer>> numbersByPositions = unsolvedNumbersWithPositions.entrySet()
                .stream()
                .collect(HashMap<List<Integer>, List<Integer>>::new, (m, v) -> m.compute(v.getValue(), (k, numbers) -> {
                    List<Integer> nums = Optional.ofNullable(numbers).orElseGet(ArrayList::new);
                    nums.add(v.getKey());
                    return nums;
                }), Map::putAll)
                .entrySet()
                .stream()
                .filter(e -> e.getKey().size() > 1)
                .filter(e -> e.getValue().size() == e.getKey().size())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<List<Integer>, List<Integer>> hiddenGroupsByPositionsEntry : numbersByPositions.entrySet()) {
            List<Integer> positionsOfHiddenGroup = hiddenGroupsByPositionsEntry.getKey();
            List<Integer> hiddenGroup = hiddenGroupsByPositionsEntry.getValue();

            // update hidden/obvious groups positions with hidden group values
            for (Integer positionOfHiddenGroup : positionsOfHiddenGroup) {

                if (haveIntersection(sudokuSolver.getPossibleNumbersByPosition(positionOfHiddenGroup), hiddenGroup)) {
                    log.info(buildLog(sudokuSolver, "Group Override", positionOfHiddenGroup, hiddenGroup));
                }

                detectedChange = sudokuSolver.updatePosition(positionOfHiddenGroup, new ArrayList<>(hiddenGroup)) || detectedChange;
            }

            // remove hidden/obvious group values from other positions
            for (Integer pos : possibleNumbersByPos.keySet()) {
                if (!positionsOfHiddenGroup.contains(pos)) {

                    if (haveIntersection(sudokuSolver.getPossibleNumbersByPosition(pos), hiddenGroup)) {
                        log.info(buildLog(sudokuSolver, "Group Remove", pos, hiddenGroup));
                    }

                    detectedChange = sudokuSolver.removeFromPosition(pos, hiddenGroup) || detectedChange;
                }
            }
        }

        return detectedChange;
    }
}