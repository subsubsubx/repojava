import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

    String s = Stream.of(null, "", "qwe", "asd")
            .filter(Objects::nonNull)
            .filter(e -> !e.equals("")).collect(Collectors.joining());
        System.out.println(s);
}
    public static UUID getUUIDFromValue(String... value) {
        if (value == null) {
            return null;
        }
        byte[] bytes = Arrays.stream(value)
                .filter(Objects::nonNull)
                .collect(Collectors.joining()).getBytes();
        return UUID.nameUUIDFromBytes(bytes);
    }
}
