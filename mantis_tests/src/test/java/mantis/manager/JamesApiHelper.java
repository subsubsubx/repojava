package mantis.manager;

import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;

public class JamesApiHelper extends HelperBase {

    private OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json");

    public JamesApiHelper(ApplicationManager appManager) {
        super(appManager);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();
    }

    public void addUser(String user, String password) {
        RequestBody body = RequestBody.create(String.format("{\"password\":\"%s\"}", password), JSON);
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s@localhost", appManager.getProperty("james.apiBaseUrl"), user))
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            System.out.println(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteUser(String email) {
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s", appManager.getProperty("james.apiBaseUrl"), email))
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
