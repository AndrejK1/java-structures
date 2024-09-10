package learning.sudoku;

import additional.Pair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class SudokuHolder {
    private final List<Integer> field;
    private final int fieldWidth;
    private final int smallSquaresSize;
    private final Map<Integer, List<Integer>> rowsPositions;
    private final Map<Integer, List<Integer>> columnsPositions;
    private final Map<Integer, List<Integer>> squaresPositions;

    public SudokuHolder(List<Integer> field) {
        this.field = Collections.unmodifiableList(field);

        int calculatedFieldSize = (int) Math.sqrt(field.size());

        if (calculatedFieldSize * calculatedFieldSize != field.size()) {
            throw new IllegalArgumentException("Field is not square shape");
        }

        this.fieldWidth = calculatedFieldSize;

        int calculatedSmallSquaresSize = (int) Math.sqrt(fieldWidth);

        if (fieldWidth / calculatedSmallSquaresSize != calculatedSmallSquaresSize) {
            throw new IllegalArgumentException("Can't fit squares in the field");
        }

        this.smallSquaresSize = calculatedSmallSquaresSize;

        Map<Integer, List<Integer>> calculatedRowsPositions = new HashMap<>();
        Map<Integer, List<Integer>> calculatedColumnsPositions = new HashMap<>();
        Map<Integer, List<Integer>> calculatedSquaresPositions = new HashMap<>();

        for (int i = 0; i < field.size(); i++) {
            Pair<Integer, Integer> coords = getPositionCoordinates(i);

            calculatedRowsPositions.computeIfAbsent(coords.getKey(), k -> new ArrayList<>());
            calculatedRowsPositions.get(coords.getKey()).add(i);

            calculatedColumnsPositions.computeIfAbsent(coords.getValue(), k -> new ArrayList<>());
            calculatedColumnsPositions.get(coords.getValue()).add(i);

            int squarePos = getSquarePosition(i);
            calculatedSquaresPositions.computeIfAbsent(squarePos, k -> new ArrayList<>());
            calculatedSquaresPositions.get(squarePos).add(i);
        }

        rowsPositions = Collections.unmodifiableMap(calculatedRowsPositions);
        columnsPositions = Collections.unmodifiableMap(calculatedColumnsPositions);
        squaresPositions = Collections.unmodifiableMap(calculatedSquaresPositions);
    }

    public int getFieldCellCount() {
        return field.size();
    }

    public List<Integer> getRowPositions(int row) {
        return getRowsPositions().get(row);
    }

    public List<Integer> getColumnPositions(int column) {
        return getColumnsPositions().get(column);
    }

    public List<Integer> getSquarePositions(int square) {
        return getSquaresPositions().get(square);
    }

    public Pair<Integer, Integer> getPositionCoordinates(int pos) {
        return Pair.of(pos / fieldWidth, pos % fieldWidth);
    }

    public Pair<Integer, Integer> getSquareCoordinates(int pos) {
        Pair<Integer, Integer> coords = getPositionCoordinates(pos);
        return Pair.of((coords.getKey()) / smallSquaresSize, (coords.getValue()) / smallSquaresSize);
    }

    public int toSquarePosition(int row, int col) {
        return row * smallSquaresSize + col;
    }

    public int getSquarePosition(int cellPosition) {
        Pair<Integer, Integer> squareCoords = getSquareCoordinates(cellPosition);
        return toSquarePosition(squareCoords.getKey(), squareCoords.getValue());
    }

    public Place getPositionPlace(int position) {
        Pair<Integer, Integer> coords = getPositionCoordinates(position);
        return new Place(coords.getKey(), coords.getValue(), getSquarePosition(position));
    }

    public List<Integer> getAllPositionsInPlace(int position) {
        Place positionPlace = getPositionPlace(position);

        return Stream.of(
                        rowsPositions.get(positionPlace.row()),
                        columnsPositions.get(positionPlace.column()),
                        squaresPositions.get(positionPlace.square())
                )
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public Place isSamePlacePositions(List<Integer> positions) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        Set<Integer> sqs = new HashSet<>();

        positions.forEach(position -> {
            Pair<Integer, Integer> coords = getPositionCoordinates(position);

            rows.add(coords.getKey());
            cols.add(coords.getValue());
            sqs.add(getSquarePosition(position));
        });

        return new Place(
                rows.size() == 1 ? rows.iterator().next() : -1,
                cols.size() == 1 ? cols.iterator().next() : -1,
                sqs.size() == 1 ? sqs.iterator().next() : -1
        );
    }

    public record Place(int row, int column, int square) {
    }
}
