package learning.map;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class LinkedListTest {

    private static void assertEmpty(SimpleMap<String, Integer> map) {
        Assert.assertEquals(0, map.size());
        Assert.assertFalse(map.containsValue(1));
        Assert.assertFalse(map.containsKey("a"));
        Assert.assertNull(map.remove("a"));
        Assert.assertNull(map.get("a"));
        Assert.assertNull(map.get("b"));
    }

    @Test
    public void testShiftMap() {
        SimpleMap<String, Integer> map = new LinearShiftHashMap<>();

        assertEmpty(map);
        map.clear();
        assertEmpty(map);

        Assert.assertEquals(1, map.put("a", 1).intValue());
        Assert.assertEquals(1, map.size());
        Assert.assertFalse(map.containsValue(2));
        Assert.assertTrue(map.containsValue(1));
        Assert.assertTrue(map.containsKey("a"));
        Assert.assertFalse(map.containsKey("b"));
        Assert.assertNull(map.remove("b"));
        Assert.assertEquals(1, map.get("a").intValue());
        Assert.assertNull(map.get("b"));

        Assert.assertEquals(1, map.remove("a").intValue());
        assertEmpty(map);

        List<String> sameHashStrings = Arrays.asList("1ot", "1pU", "1q6", "2Pt");

        for (int i = 0; i < sameHashStrings.size(); i++) {
            Assert.assertEquals(i, map.put(sameHashStrings.get(i), i).intValue());
        }

        Assert.assertEquals(4, map.size());

        for (int i = 0; i < sameHashStrings.size(); i++) {
            Assert.assertEquals(i, map.get(sameHashStrings.get(i)).intValue());
            Assert.assertTrue(map.containsKey(sameHashStrings.get(i)));
            Assert.assertTrue(map.containsValue(i));
        }

        // same key
        Assert.assertEquals(345, map.put("2Pt", 345).intValue());

        Assert.assertEquals(4, map.size());
        Assert.assertTrue(map.containsKey("2Pt"));
        Assert.assertTrue(map.containsValue(345));

        List<String> str = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i");

        for (int i = 0; i < str.size(); i++) {
            Assert.assertEquals(i * 10, map.put(str.get(i), i * 10).intValue());
        }

        Assert.assertEquals(13, map.size());

        for (int i = 0; i < str.size(); i++) {
            Assert.assertEquals(i * 10, map.get(str.get(i)).intValue());
            Assert.assertTrue(map.containsKey(str.get(i)));
            Assert.assertTrue(map.containsValue(i * 10));
        }

        map.clear();
        assertEmpty(map);
    }
}
