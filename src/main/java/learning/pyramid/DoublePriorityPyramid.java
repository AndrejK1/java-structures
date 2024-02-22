package learning.pyramid;

public class DoublePriorityPyramid<T> extends SimplePriorityPyramid<Double, T> {
    public DoublePriorityPyramid() {
        this(true);
    }

    public DoublePriorityPyramid(boolean highPriorityBased) {
        this(16, Double.MAX_VALUE / 2, highPriorityBased);
    }

    public DoublePriorityPyramid(int size, double defaultPriority, boolean highPriorityBased) {
        super(size, defaultPriority, highPriorityBased);
    }
}