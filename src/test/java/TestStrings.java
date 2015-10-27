import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created by ivan.philimonov on 27.10.2015.
 */
public class TestStrings {

    private static List<String> buildStrings() {
        final long start = System.currentTimeMillis();
        System.out.println("Start_build_string_list: " + start);
        final int count10Million = 10 * 1000 * 1000;
        final List<String> strings = new ArrayList<>();
        for (int i = 0; i < count10Million; i++) {
            strings.add(RandomStringUtils.randomAlphabetic(20));
        }
        final long end = System.currentTimeMillis();
        System.out.println("End_build_string_list: " + end);
        System.out.println("Build string list duration: " + (end - start));
        return strings;
    }

    @Test
    public void testCollectors_joininng() {
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final String join = strings.stream().collect(Collectors.joining());
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End concatentaion: " + (end - start));
    }

    @Test
    public void testImplicitReduce_joininng() {
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final String join = strings.stream().reduce((s, s2) -> s + s2).get();
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End concatentaion: " + (end - start));
    }
}
