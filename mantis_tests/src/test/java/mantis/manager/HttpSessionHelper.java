package mantis.manager;

import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;

public class HttpSessionHelper extends HelperBase {
  private final OkHttpClient client;

    public HttpSessionHelper(ApplicationManager appManager) {
        super(appManager);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();
    }

    public void login(String login, String password) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", login)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(String.format("%s/login.php", appManager.getProperty("web.baseUrl")))
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
    //        System.out.println(response.body().string());
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    public boolean isLoggedIn() {
        Request request = new Request.Builder()
                .url(appManager.getProperty("web.baseUrl"))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            String s = response.body().string();
            return s.contains("<span class=\"user-info\">");
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

}
