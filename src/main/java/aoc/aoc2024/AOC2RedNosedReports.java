package aoc.aoc2024;

import aoc.AOCTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AOC2RedNosedReports extends AOCTask<AOC2RedNosedReports.AOC2InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 2: Red-Nosed Reports";
    }

    @Override
    public AOCAnswer solve(AOC2InputData inputData) {
        List<List<Integer>> reports = inputData.reports();
        int safeWithoutErrors = 0;
        int safeReports = 0;

        for (List<Integer> report : reports) {

            boolean correctReport = isReportCorrect(report);

            if (!correctReport) {
                correctReport = checkForOneErrorReport(report);
            } else {
                safeWithoutErrors++;
            }

            if (correctReport) {
                safeReports++;
            }
        }

        return new AOCAnswer(safeWithoutErrors, safeReports);
    }

    private static boolean checkForOneErrorReport(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            List<Integer> reportCopy = new ArrayList<>(report);
            reportCopy.remove(i);

            boolean reportCopyCorrect = isReportCorrect(reportCopy);

            if (reportCopyCorrect) {
                return true;
            }
        }

        return false;
    }

    private static boolean isReportCorrect(List<Integer> report) {
        int firstDiff = report.get(1) - report.get(0);

        if (firstDiff == 0) {
            return false;
        }

        for (int i = 0; i < report.size() - 1; i++) {
            int currentDiff = report.get(i + 1) - report.get(i);
            int absDiff = Math.abs(currentDiff);

            if (currentDiff * firstDiff < 0 || absDiff > 3 || absDiff < 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public AOC2InputData parseInputData(String fileContent) {
        return new AOC2InputData(
                Arrays.stream(fileContent.split("\n"))
                        .map(String::trim)
                        .map(report -> Arrays.stream(report.split(" ")).map(Integer::valueOf).toList())
                        .toList()
        );
    }

    public record AOC2InputData(List<List<Integer>> reports) implements AOCInputData {
    }

}
