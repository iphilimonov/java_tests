import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by ivan.philimonov on 27.10.2015.
 */
public class TestStrings {

    private static List<String> buildStrings() {
        logCurrentMethod();
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
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final String join = strings.stream().collect(Collectors.joining());
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @Test
    public void testImplicitReduceWithFuture(){
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        //[IP] actually it hangs
        final Future<String> future = new FutureTask<>(() -> strings.stream().reduce((s, s2) -> s + s2).get());
        try {
            future.get(10, TimeUnit.MINUTES);
        } catch (final InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @Test
    public void testImplicitReduce_joininng() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
           final Future<String> val = CompletableFuture.supplyAsync(()-> strings.stream().reduce((s, s2) -> s + s2).get());
        try {
            val.get();
        } catch (final InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @Test
    public void testStringJoiner() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final StringJoiner joiner = new StringJoiner("");
        strings.forEach(joiner::add);
        final String result = joiner.toString();
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @Test
    public void testStaticStringClass_join() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final String join = String.join("", strings);
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Test
    public void testStringIterating() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        String join = "";
        for (int i=0; i < strings.size(); i++){
            join+=strings.get(i);
        }
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Test
    public void testStringBuilderIterating() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final StringBuilder join = new StringBuilder();
        for (int i=0; i < strings.size(); i++){
            join.append(strings.get(i));
        }
        final String result = join.toString();
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Test
    public void testStringBuilderWithSmallInitialCapacityIterating() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final StringBuilder join = new StringBuilder(10);
        for (int i=0; i < strings.size(); i++){
            join.append(strings.get(i));
        }
        final String result = join.toString();
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Test
    public void testStringBuilderWithBigInitialCapacityIterating() {
        logCurrentMethod();
        final List<String> strings = buildStrings();
        final long start = System.currentTimeMillis();
        System.out.println("Start join: " + start);
        final StringBuilder join = new StringBuilder(9 * 1000 * 1000);
        for (int i=0; i < strings.size(); i++){
            join.append(strings.get(i));
        }
        final String result = join.toString();
        final long end = System.currentTimeMillis();
        System.out.println("End join: " + end);
        System.out.println("End join: " + (end - start));
    }

    private static void logCurrentMethod(){
        System.out.println(Thread.currentThread().getStackTrace()[2].toString());
    }
}
