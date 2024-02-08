package mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
public class LoginTest extends TestBase {


    @Test
    void login() {
        app.getSession().login(app.getProperty("web.username"),
                app.getProperty(("web.password")));
        Assertions.assertTrue(app.getSession().isLoggedIn());
    }

    @Test
    void loginUsingHttpClient() throws IOException {
        app.getHttpClient().login(app.getProperty("web.username"),
                app.getProperty("web.password"));
        Assertions.assertTrue(app.getHttpClient().isLoggedIn());
    }

}
