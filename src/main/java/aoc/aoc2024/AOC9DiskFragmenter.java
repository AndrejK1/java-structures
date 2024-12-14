package aoc.aoc2024;

import aoc.AOCTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AOC9DiskFragmenter implements AOCTask<AOC9DiskFragmenter.AOC9InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 9: Disk Fragmenter";
    }

    @Override
    public AOCAnswer solve(AOC9InputData inputData) {
        List<Integer> discBlocks = inputData.diskMap();
        int[] compressed = new int[discBlocks.stream().mapToInt(Integer::intValue).sum()];

        int idx = 0;

        for (int discBlockIdx = 0; discBlockIdx < discBlocks.size(); discBlockIdx++) {
            Integer discBlock = discBlocks.get(discBlockIdx);

            for (int i = 0; i < discBlock; i++) {
                compressed[idx] = discBlockIdx % 2 == 0 ? discBlockIdx / 2 : -1;
                idx++;
            }
        }

        int startIdx = 0;
        int endIdx = compressed.length - 1;

        while (startIdx < endIdx) {
            if (compressed[startIdx] != -1) {
                startIdx++;
                continue;
            }

            if (compressed[endIdx] == -1) {
                endIdx--;
                continue;
            }

            compressed[startIdx] = compressed[endIdx];
            compressed[endIdx] = -1;
            startIdx++;
            endIdx--;
        }

        long result1 = 0;

        for (int i = 0; i <= endIdx; i++) {
            result1 += (long) compressed[i] * i;
        }

        return new AOCAnswer(result1, 0);
    }

    @Override
    public AOC9InputData parseInputData(String fileContent) {
        List<Integer> diskMap = new ArrayList<>();

        for (int i = 0; i < fileContent.length(); i++) {
            diskMap.add(Character.getNumericValue(fileContent.charAt(i)));
        }

        return new AOC9InputData(diskMap);
    }

    public record AOC9InputData(List<Integer> diskMap) implements AOCInputData {
    }
}
