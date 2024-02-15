package mantis.manager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {
    public RegistrationHelper(ApplicationManager appManager) {
        super(appManager);
    }

    public void confirmRegistration(String url, String username, String password) {
        appManager.getDriver().get(url);
        setField(By.id("realname"), username);
        setField(By.id("password"), password);
        setField(By.id("password-confirm"), password);
        clickElement(By.cssSelector("[type='submit']"));
    }

    public void registerNewUser(String username, String email) {
        //appManager.getDriver().get(String.format("%s/signup_page.php", appManager.getProperty("web.baseUrl")));
        clickElement(By.cssSelector("[class='back-to-login-link pull-left']"));
        setField(By.id("username"), username);
        setField(By.id("email-field"), email);
        clickElement(By.cssSelector("[type='submit']"));
    }


}
