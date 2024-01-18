package common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class Common {

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + (int) (Math.random() * 26)));
        }
        return sb.toString();
    }

    public static String getRandomFile(String path) {
        String[] files = new File(path).list();
        if (files != null) {
            return Paths.get(path, files[new Random().nextInt(files.length)]).toString();
        } else return null;
    }
}
