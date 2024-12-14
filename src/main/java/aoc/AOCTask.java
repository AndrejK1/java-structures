package aoc;

public interface AOCTask<I extends AOCTask.AOCInputData> {

    String getTaskTitle();

    AOCAnswer solve(I inputData);

    I parseInputData(String fileContent);

    interface AOCInputData {
    }

    record AOCAnswer(long answerPart1, long answerPart2) {
    }
}
