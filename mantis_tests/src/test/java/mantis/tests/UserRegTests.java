package mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.time.Duration;

public class UserRegTests extends TestBase {


    @ParameterizedTest
    @MethodSource("mantis.tests.TestBase#stringArgsProvider")
    void userRegistrationTest(String username, String password) throws IOException {
        String email = String.format("%s@localhost", username);
        app.getCliHelper().addUser(email, password);
        app.getRegHelper().registerNewUser(username, email);
        app.getMailHelper().receive(email, password, Duration.ofSeconds(7));
        String url = app.getMailHelper().extractUrl(email, password);
        app.getRegHelper().confirmRegistration(url, username, password);
        app.getHttpClient().login(username, password);
        Assertions.assertTrue(app.getHttpClient().isLoggedIn());
    }
}
