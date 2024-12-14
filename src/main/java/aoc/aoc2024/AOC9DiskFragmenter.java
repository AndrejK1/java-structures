package aoc.aoc2024;

import aoc.AOCTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AOC9DiskFragmenter implements AOCTask<AOC9DiskFragmenter.AOC9InputData> {

    @Override
    public String getTaskTitle() {
        return "Day 9: Disk Fragmenter";
    }

    @Override
    public AOCAnswer solve(AOC9InputData inputData) {
        List<Integer> discBlocks = inputData.diskMap();
        int[] diskData = new int[discBlocks.stream().mapToInt(Integer::intValue).sum()];

        int idx = 0;

        for (int discBlockIdx = 0; discBlockIdx < discBlocks.size(); discBlockIdx++) {
            Integer discBlock = discBlocks.get(discBlockIdx);

            for (int i = 0; i < discBlock; i++) {
                diskData[idx] = discBlockIdx % 2 == 0 ? discBlockIdx / 2 : -1;
                idx++;
            }
        }

        long result1 = solveFirstPart(Arrays.copyOf(diskData, diskData.length));
        long result2 = solveSecondPart(Arrays.copyOf(diskData, diskData.length), discBlocks);

        return new AOCAnswer(result1, result2);
    }

    private static long solveFirstPart(int[] diskData) {
        int startIdx = 0;
        int endIdx = diskData.length - 1;

        while (startIdx < endIdx) {
            if (diskData[startIdx] != -1) {
                startIdx++;
                continue;
            }

            if (diskData[endIdx] == -1) {
                endIdx--;
                continue;
            }

            diskData[startIdx] = diskData[endIdx];
            diskData[endIdx] = -1;
            startIdx++;
            endIdx--;
        }

        long result = 0;

        for (int i = 0; i <= endIdx; i++) {
            result += (long) diskData[i] * i;
        }

        return result;
    }

    private long solveSecondPart(int[] diskData, List<Integer> discBlocks) {
        Map<Integer, Integer> discBlockPositions = new HashMap<>();

        int dataIdx = 0;
        for (int blockIdx = 0; blockIdx < discBlocks.size(); blockIdx++) {
            discBlockPositions.put(blockIdx, dataIdx);
            dataIdx += discBlocks.get(blockIdx);
        }

        int lastDataBlockIdx = (discBlocks.size() - 1) % 2 == 0 ? discBlocks.size() - 1 : discBlocks.size() - 2;

        for (int blockIdx = lastDataBlockIdx; blockIdx > 0; blockIdx -= 2) {
            int blockSize = discBlocks.get(blockIdx);
            int blockPosition = discBlockPositions.get(blockIdx);

            int startIdxForCopy = -1;

            for (int i = 0; i <= blockPosition; i++) {
                // find start of empty block
                if (startIdxForCopy == -1 && diskData[i] == -1) {
                    startIdxForCopy = i;
                }

                if (startIdxForCopy != -1 && diskData[i] != -1) {
                    // block found
                    if (i - startIdxForCopy >= blockSize) {
                        break;
                    } else {
                        startIdxForCopy = -1;
                    }
                }
            }

            if (startIdxForCopy == -1) {
                continue;
            }

            // copy block to empty space
            for (int i = 0; i < blockSize; i++) {
                diskData[startIdxForCopy] = diskData[blockPosition];
                diskData[blockPosition] = -1;
                startIdxForCopy++;
                blockPosition++;
            }
        }

        long result = 0;

        for (int i = 0; i < diskData.length; i++) {
            if (diskData[i] != -1) {
                result += (long) diskData[i] * i;
            }
        }

        return result;
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
