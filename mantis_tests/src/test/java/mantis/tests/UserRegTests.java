package mantis.tests;

import mantis.common.Common;
import mantis.model.CreateUserData;
import mantis.model.DeveloperMailUserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

public class UserRegTests extends TestBase {


    DeveloperMailUserData userData;


/*
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


    @ParameterizedTest
    @MethodSource("mantis.tests.TestBase#stringArgsProvider")
    void userRegistrationApiTest(String username, String password) throws IOException {
        String email = String.format("%s@localhost", username);
        app.getJamesApiHelper().addUser(email, password);
        app.getRegHelper().registerNewUser(username, email);
        app.getMailHelper().receive(email, password, Duration.ofSeconds(7));
        String url = app.getMailHelper().extractUrl(email, password);
        app.getRegHelper().confirmRegistration(url, username, password);
        app.getHttpClient().login(username, password);
        Assertions.assertTrue(app.getHttpClient().isLoggedIn());
    }
*/

    @Test
    void createUser() throws IOException {
        //     userData = app.getDeveloperMail().addUser();


        String name = Common.randomString(10);
        String email = String.format("%s@localhost", name);
        String password = Common.randomString(10);

        userData = app.getJamesApiHelper().addUser(name, password);
        CreateUserData createUserData = new CreateUserData()
                .withUsername(userData.getName())
                .withRealName(userData.getName())
                .withEmail(email)
                .withPassword(password)

                .withAccessLevel("developer")
                .withProtected(false)
                .withEnabled(true);

        app.getRestApiHelper().registerNewUser(createUserData);

        app.getMailHelper().receive(email, password, Duration.ofSeconds(7));
        String url = app.getMailHelper().extractUrl(email, password);
        app.getRegHelper().confirmRegistration(url, userData.getName(), password);
        app.getHttpClient().login(userData.getName(), password);
        Assertions.assertTrue(app.getHttpClient().isLoggedIn());
    }
}
/*    @AfterEach
    void deleteMailUser() {
        app.getDeveloperMail().deleteUser(userData);
    }
}*/
