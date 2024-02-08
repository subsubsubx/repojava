package mantis.common;

import org.junit.jupiter.params.provider.Arguments;

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



}
