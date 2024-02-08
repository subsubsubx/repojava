package mantis.tests;

import mantis.manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

import static mantis.common.Common.randomString;

public class TestBase {
    protected static ApplicationManager app;


    @BeforeEach
    public void before() throws IOException {
        if (app == null) {
            Properties properties = new Properties();
            properties.load(new FileReader((System.getProperty("target", "local.properties"))));
            app = new ApplicationManager();
            app.init(System.getProperty("browser", "chrome"), properties);
        }

    }

    public static Stream<Arguments> stringArgsProvider() {
        return Stream.of(Arguments.of(randomString(10), randomString(10)));
    }

}
