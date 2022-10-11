package learning.map;

import additional.ADT;

public interface SimpleMap<K, V> extends ADT {
    V put(K key, V value);
    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    void clear();

    int size();

    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
}
