package aoc;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Builder
public class AOCTaskRunner {
    private final AOCTask aokTask;
    private final String inputDataFile;

    public AOCTask.AOCAnswer solveTask() {
        String fileContent = readInputDataFileContent(inputDataFile);
        AOCTask.AOCInputData inputData = aokTask.parseInputData(fileContent);
        log.info("Solving {}", aokTask.getTaskTitle());
        return aokTask.solve(inputData);
    }

    @SneakyThrows
    private String readInputDataFileContent(String fileName) {
        return Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI()));
    }
}
