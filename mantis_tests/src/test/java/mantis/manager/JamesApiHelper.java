package mantis.manager;

import mantis.model.DeveloperMailUserData;
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

    public DeveloperMailUserData addUser(String user, String password) {
        RequestBody body = RequestBody.create(String.format("{\"password\":\"%s\"}", password), JSON);
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s@localhost", appManager.getProperty("james.apiBaseUrl"), user))
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code: " + response);
            System.out.println(response.body().string());
            return new  DeveloperMailUserData().withName(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
