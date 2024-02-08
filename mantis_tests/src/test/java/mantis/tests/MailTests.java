package mantis.tests;

import jakarta.mail.MessagingException;
import mantis.model.MailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class MailTests extends TestBase {
    @Test
    void receiveEmail() throws MessagingException {
        List<MailMessage> messages = app.getMailHelper().receive(
                "user1@localhost",
                "password",
                Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    @Test
    void drainInbox() {
        app.getMailHelper().drain("user1@localhost", "password");
    }
}
