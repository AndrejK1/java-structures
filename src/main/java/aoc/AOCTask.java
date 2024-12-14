package aoc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AOCTask<I extends AOCTask.AOCInputData> {

    public abstract String getTaskTitle();

    public abstract AOCAnswer solve(I inputData);

    protected abstract I parseInputData(String fileContent);

    protected interface AOCInputData {
    }

    public record AOCAnswer(long answerPart1, long answerPart2) {
    }
}
