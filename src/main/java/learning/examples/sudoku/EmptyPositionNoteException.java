package learning.examples.sudoku;

import lombok.Getter;

@Getter
public class EmptyPositionNoteException extends RuntimeException {
    private final int position;

    public EmptyPositionNoteException(int position) {
        super("No notes at position: " + position);
        this.position = position;
    }
}
