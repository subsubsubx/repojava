package mantis.tests;

import org.junit.jupiter.api.Test;
import static mantis.common.Common.randomString;

public class JamesTests extends TestBase{

    @Test
    void createUserTest(){
        app.getCliHelper().addUser(String.format("%s@localhost", randomString(10)) , "password");
    }
}
