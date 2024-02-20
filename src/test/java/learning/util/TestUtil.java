package learning.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TestUtil {

    @SneakyThrows
    public static List<String> loadFileLines(String fileName) {
        try (Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(TestUtil.class.getResource(fileName)).toURI()), Charset.defaultCharset())) {
            return lines.collect(Collectors.toList());
        }
    }
}
