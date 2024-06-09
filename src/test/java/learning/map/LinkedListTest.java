package learning.map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
class LinkedListTest {

    @Test
    void testShiftMap() {
        SimpleMap<String, Integer> map = new LinearShiftHashMap<>();

        assertEmpty(map);
        map.clear();
        assertEmpty(map);

        Assertions.assertEquals(1, map.put("a", 1).intValue());
        Assertions.assertEquals(1, map.size());
        Assertions.assertFalse(map.containsValue(2));
        Assertions.assertTrue(map.containsValue(1));
        Assertions.assertTrue(map.containsKey("a"));
        Assertions.assertFalse(map.containsKey("b"));
        Assertions.assertNull(map.remove("b"));
        Assertions.assertEquals(1, map.get("a").intValue());
        Assertions.assertNull(map.get("b"));

        Assertions.assertEquals(1, map.remove("a").intValue());
        assertEmpty(map);

        List<String> sameHashStrings = Arrays.asList("1ot", "1pU", "1q6", "2Pt");

        for (int i = 0; i < sameHashStrings.size(); i++) {
            Assertions.assertEquals(i, map.put(sameHashStrings.get(i), i).intValue());
        }

        Assertions.assertEquals(4, map.size());

        for (int i = 0; i < sameHashStrings.size(); i++) {
            Assertions.assertEquals(i, map.get(sameHashStrings.get(i)).intValue());
            Assertions.assertTrue(map.containsKey(sameHashStrings.get(i)));
            Assertions.assertTrue(map.containsValue(i));
        }

        // same key
        Assertions.assertEquals(345, map.put("2Pt", 345).intValue());

        Assertions.assertEquals(4, map.size());
        Assertions.assertTrue(map.containsKey("2Pt"));
        Assertions.assertTrue(map.containsValue(345));

        List<String> str = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i");

        for (int i = 0; i < str.size(); i++) {
            Assertions.assertEquals(i * 10, map.put(str.get(i), i * 10).intValue());
        }

        Assertions.assertEquals(13, map.size());

        for (int i = 0; i < str.size(); i++) {
            Assertions.assertEquals(i * 10, map.get(str.get(i)).intValue());
            Assertions.assertTrue(map.containsKey(str.get(i)));
            Assertions.assertTrue(map.containsValue(i * 10));
        }

        map.clear();
        assertEmpty(map);
    }

    private void assertEmpty(SimpleMap<String, Integer> map) {
        Assertions.assertEquals(0, map.size());
        Assertions.assertFalse(map.containsValue(1));
        Assertions.assertFalse(map.containsKey("a"));
        Assertions.assertNull(map.remove("a"));
        Assertions.assertNull(map.get("a"));
        Assertions.assertNull(map.get("b"));
    }
}
