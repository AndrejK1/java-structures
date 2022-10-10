package learning.tree.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TreeUtils {
    public static <T> MutableNode<T> binaryNodeOf(T value) {
        return binaryNodeOf(value, null, null);
    }

    public static <T> MutableNode<T> binaryNodeOf(T value, MutableNode<T> left, MutableNode<T> right) {
        return new MutableNodeImpl<>(value, left, right);
    }
}
