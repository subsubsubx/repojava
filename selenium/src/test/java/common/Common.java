package common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Common {

    public static String randomString(int length) {
        Supplier<Integer> supplier = () -> new Random().nextInt(26);
       return Stream.generate(supplier)
                .limit(length)
                .map(i -> 'a' + i)
                .map(Character::toString).collect(Collectors.joining());
    }
/*
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        Supplier<Integer> supplier = () -> new Random().nextInt(26);
        Stream.generate(supplier)
                .limit(length)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .forEach(sb::append);
        return sb.toString();
    }*/

    public static String getRandomFile(String path) {
        String[] files = new File(path).list();
        if (files != null) {
            return Paths.get(path, files[new Random().nextInt(files.length)]).toString();
        } else return null;
    }
}
