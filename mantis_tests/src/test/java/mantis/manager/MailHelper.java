package mantis.manager;

import jakarta.mail.*;
import mantis.model.MailMessage;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailHelper extends HelperBase {
    public MailHelper(ApplicationManager appManager) {
        super(appManager);
    }


    public void drain(String username, String password) {
        try {
            Folder inbox = getInbox(username, password);
            inbox.open(Folder.READ_WRITE);
            Arrays.stream(inbox.getMessages()).forEach(message -> {
                try {
                    message.setFlag(Flags.Flag.DELETED, true);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
            inbox.close();
            inbox.getStore().close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MailMessage> receive(String username, String password, Duration duration) {

        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() < start + duration.toMillis()) {
            try {
                Folder inbox = getInbox(username, password);
                inbox.open(Folder.READ_ONLY);
                Message[] messages = inbox.getMessages();
                List<MailMessage> res = Arrays.stream(messages)
                        .map(message -> {
                            try {
                                return new MailMessage()
                                        .withFrom(message.getFrom()[0].toString())
                                        .withContent((String) message.getContent());
                            } catch (MessagingException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                inbox.close();
                inbox.getStore().close();
                if (res.size() > 0) {
                    return res;
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no mail");
    }

    private Folder getInbox(String username, String password) {
        try {
            Session session = Session.getInstance(new Properties());
            Store store = session.getStore("pop3");
            store.connect("localhost", username, password);
            Folder inbox = store.getFolder("INBOX");
            return inbox;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractUrl(String username, String password) {
        List<MailMessage> messages = receive(username, password, Duration.ofSeconds(8));
        String content = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return content.substring(matcher.start(), matcher.end());
        }
        return null;
    }


}
