package learning.sudoku;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
record TestingSudokuPrinter(boolean printMove,
                            boolean printPossibleNumbers,
                            boolean printSolved) implements SudokuPrinter {
    @Override
    public void printState(SudokuSolver sudokuSolver) {
        int maxNumberLength = String.valueOf(sudokuSolver.getSudoku().getFieldWidth()).length();

        if (printMove || (printSolved && sudokuSolver.isSolved())) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < sudokuSolver.getSudoku().getFieldCellCount(); i++) {
                List<Integer> value = sudokuSolver.getPossibleNumbersByPosition(i);

                if (value.size() == 1) {
                    String strValue = value.getFirst().toString();
                    stringBuilder.append(" ".repeat(maxNumberLength - strValue.length())).append(strValue);
                } else {
                    stringBuilder.append("-".repeat(maxNumberLength));
                }

                if ((i + 1) % sudokuSolver.getSudoku().getFieldWidth() == 0) {
                    stringBuilder.append('\n');
                } else {
                    stringBuilder.append(' ');
                }
            }

            log.info("Step: {}, Solved: {}, Solved positions\n{}", sudokuSolver.getSolutionStep(), sudokuSolver.isSolved(), stringBuilder);
        }

        if (printPossibleNumbers) {
            StringBuilder stringBuilder = new StringBuilder();

            int maxStrLength = 1;

            for (int i = 0; i < sudokuSolver.getSudoku().getFieldCellCount(); i++) {
                maxStrLength = Math.max(sudokuSolver.getPossibleNumbersByPosition(i)
                                .toString()
                                .replace(" ", "")
                                .length(),
                        maxStrLength);
            }

            for (int i = 0; i < sudokuSolver.getSudoku().getFieldCellCount(); i++) {
                String value = sudokuSolver.getPossibleNumbersByPosition(i).toString().replace(" ", "");

                stringBuilder.append(value);

                stringBuilder.append(" ".repeat(Math.max(0, maxStrLength - value.length())));

                if ((i + 1) % sudokuSolver.getSudoku().getFieldWidth() == 0) {
                    stringBuilder.append('\n');
                } else {
                    stringBuilder.append(' ');
                }
            }

            log.info("Step: {}, Solved: {}, Possible Numbers\n{}", sudokuSolver.getSolutionStep(), sudokuSolver.isSolved(), stringBuilder);
        }
    }
}
