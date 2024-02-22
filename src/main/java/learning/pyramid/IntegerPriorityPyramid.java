package learning.pyramid;

public class IntegerPriorityPyramid<T> extends SimplePriorityPyramid<Integer, T> {
    public IntegerPriorityPyramid() {
        this(true);
    }

    public IntegerPriorityPyramid(boolean highPriorityBased) {
        this(16, Integer.MAX_VALUE / 2, highPriorityBased);
    }

    public IntegerPriorityPyramid(int size, int defaultPriority, boolean highPriorityBased) {
        super(size, defaultPriority, highPriorityBased);
    }
}