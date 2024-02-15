package mantis.manager;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.UserApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.AccessLevel;
import io.swagger.client.model.User;
import io.swagger.client.model.UserAddResponse;
import mantis.model.CreateUserData;

public class RestApiHelper extends HelperBase {

    public RestApiHelper(ApplicationManager appManager) {
        super(appManager);
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth Authorization = (ApiKeyAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setApiKey(appManager.getProperty("api.key"));
    }

    public void createIssue(){

    }

    public void registerNewUser(CreateUserData data) {

        User user = new User();
        user.setUsername(data.username());
        user.setPassword(data.password());
        user.setRealName(data.realName());
        user.setEmail(data.email());

        user.setProtected(data._protected());

        AccessLevel accLevel = new AccessLevel();
        accLevel.setName(data.accessLevel());
        user.setAccessLevel(accLevel);



        user.setEnabled(data.enabled());
        UserApi apiInstance = new UserApi();

        try {
            UserAddResponse result = apiInstance.userAdd(user);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#userAdd");
            e.printStackTrace();
        }
    }
}
