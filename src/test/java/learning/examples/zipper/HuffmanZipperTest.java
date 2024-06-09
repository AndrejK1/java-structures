package learning.examples.zipper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class HuffmanZipperTest {
    private final HuffmanZipper huffmanZipper = new HuffmanZipper();

    @Test
    void testZipper() {
        Arrays.asList("", "e", "eeee", "test", "Some sentence hare", "Different chars:!@36-+=\\")
                .forEach(this::innerTest);
    }

    private void innerTest(String actualString) {
        log.info("Processing `{}`", actualString);
        ZipResult zipResult = huffmanZipper.zipString(actualString);
        String unzipString = huffmanZipper.unzipString(zipResult.getZip(), zipResult.getTree());
        Assertions.assertEquals(actualString, unzipString);
    }
}
