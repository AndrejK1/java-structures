package learning.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public class LinearShiftHashMap<K, V> implements SimpleMap<K, V> {
    private final double loadFactor;
    private final double sizeMultiplier;
    private int size;
    private int arraySize;
    private int mod;
    private Entry<K, V>[] elements;

    public LinearShiftHashMap() {
        this(16, 0.75, 1.5);
    }

    public LinearShiftHashMap(int arraySize, double loadFactor, double sizeMultiplier) {
        if (arraySize < 1) {
            throw new IllegalArgumentException("Map size must be > 0");
        }

        if (loadFactor <= 0.5 || loadFactor >= 0.9) {
            throw new IllegalArgumentException("load factor must be 0.5 < lf < 0.9");
        }

        if (sizeMultiplier <= 1) {
            throw new IllegalArgumentException("size multiplier must be > 1");
        }

        this.arraySize = arraySize;
        this.loadFactor = loadFactor;
        this.sizeMultiplier = sizeMultiplier;
        this.mod = calcMod(arraySize);
        elements = new Entry[arraySize];
    }

    @Override
    public String showStructure() {
        return null;
    }

    @Override
    public V put(K key, V value) {
        adjustCapacity();
        return putEntry(new Entry<>(key, value));
    }

    private V putEntry(Entry<K, V> entry) {
        K key = entry.key;
        int index = hashFunc(key);
        int hashIncrement = hashIncrement(key);
        Entry<K, V> current = elements[index];

        while (current != null && !current.deleted) {

            if (current.key.equals(key)) {
                current.value = entry.value;
                return current.value;
            }

            index += hashIncrement;
            index %= arraySize;

            current = elements[index];
        }

        elements[index] = entry;
        size++;
        return entry.value;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = innerSearch(key);
        return entry != null ? entry.value : null;
    }

    @Override
    public V remove(K key) {
        Entry<K, V> entry = innerSearch(key);

        if (entry != null) {
            entry.deleted = true;
            size--;
            return entry.value;
        }

        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return innerSearch(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < arraySize; i++) {
            if (elements[i] != null && !elements[i].deleted && elements[i].value.equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    private int calcMod(int size) {
        int i = 1;

        if (size <= 3) {
            return i;
        }

        while (size % i == 0 && i < size || !isPrime(i)) {
            i += 2;
        }

        return i;
    }

    public boolean isPrime(int num) {
        if (num > 2 && num % 2 == 0) {
            return false;
        }
        int top = (int) Math.sqrt(num) + 1;
        for (int i = 3; i < top; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int hashFunc(Object o) {
        return o.hashCode() % arraySize;
    }

    private int hashIncrement(Object o) {
        return mod - (o.hashCode() % mod);
    }

    private void adjustCapacity() {
        if (size + 1 <= arraySize * loadFactor) {
            return;
        }

        Entry<K, V>[] currentElements = elements;

        arraySize = (int) Math.ceil(arraySize * sizeMultiplier);
        elements = new Entry[arraySize];
        mod = calcMod(arraySize);
        size = 0;

        for (Entry<K, V> currentElement : currentElements) {
            if (currentElement != null && !currentElement.deleted) {
                putEntry(currentElement);
            }
        }
    }

    private Entry<K, V> innerSearch(K key) {
        int startIndex = hashFunc(key);
        int hashIncrement = hashIncrement(key);

        int i = startIndex;
        Entry<K, V> current;

        do {
            current = elements[i];

            if (current == null) {
                return null;
            }

            if (current.key.equals(key)) {
                return current.deleted ? null : current;
            }

            i += hashIncrement;
            i %= arraySize;
        } while (i != startIndex);

        return null;
    }

    @Getter
    @AllArgsConstructor
    private static class Entry<K, V> implements SimpleMap.Entry<K, V> {
        private K key;
        private V value;
        private boolean deleted;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }
}
