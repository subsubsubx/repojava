package mantis.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import mantis.manager.developermail.AddUserResp;
import mantis.manager.developermail.GetIdsResp;
import mantis.manager.developermail.GetMessageResp;
import mantis.model.DeveloperMailUserData;
import okhttp3.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.CookieManager;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeveloperMailHelper extends HelperBase {


    private static final MediaType JSON = MediaType.get("application/json");
    private OkHttpClient client;

    public DeveloperMailHelper(ApplicationManager appManager) {
        super(appManager);

        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();
    }

    public DeveloperMailUserData addUser() throws IOException {
        RequestBody requestBody = RequestBody.create("", JSON);

        Request request = new Request.Builder()
                .url(appManager.getProperty("dev.apiBaseUrl"))
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            String s = response.body().string();
            System.out.println("response: " + s);
            AddUserResp userResp = new ObjectMapper().readValue(s, AddUserResp.class);
            if (!userResp.success()) {
                throw new RuntimeException(userResp.errors().toString());
            }
            return userResp.result();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void deleteUser(DeveloperMailUserData userData) {
        Request request = new Request.Builder()
                .url(String.format("%s/%s", appManager.getProperty("dev.apiBaseUrl"), userData.getName()))
                .header("X-MailboxToken", userData.getToken())
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            String s = response.body().string();
            System.out.println(s);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



    public String receive(DeveloperMailUserData userData, Duration duration) {
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() < start + duration.toMillis()) {
            try {
                String text1 = get(userData.getName(), userData.getToken());

                GetIdsResp idsResp = new ObjectMapper().readValue(text1, GetIdsResp.class);
                if (idsResp.result().size() > 0) {
                    String text2 = get(String.format("%s/messages/%s", userData.getName(), idsResp.result().get(0)),
                            userData.getToken());
                    if (!idsResp.success()) {
                        throw new RuntimeException(idsResp.errors().toString());
                    }
                    GetMessageResp msgResp = new ObjectMapper().readValue(text2, GetMessageResp.class);
                    if (!msgResp.success()) {
                        throw new RuntimeException(msgResp.errors().toString());
                    }
                    return new String(MimeUtility.decode(
                            new ByteArrayInputStream(msgResp.result().getBytes()), "quoted-printable")
                            .readAllBytes());
                }
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("no mail");
    }

    public void registerNewUser(){

    }
    private String get(String url, String token) {
        Request request = new Request.Builder()
                .url(String.format("%s/%s", appManager.getProperty("dev.apiBaseUrl"), url))
                .header("X-MailboxToken", token)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String extractUrl(DeveloperMailUserData userData) {
        String msg = receive(userData, Duration.ofSeconds(7));
        Pattern pattern = Pattern.compile("http://\\S+");
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            return msg.substring(matcher.start(), matcher.end());
        }
        return null;
    }
}

