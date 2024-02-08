package mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {


    public SessionHelper(ApplicationManager appManager) {
        super(appManager);
    }

    public void login(String login, String password) {
        setField(By.id("username"), login);
        clickElement(By.cssSelector("input[type='submit']"));
        setField(By.id("password"), password);
        clickElement(By.cssSelector("input[type='submit']"));
    }

    public boolean isLoggedIn() {
        return isElementPresent(By.cssSelector("span[class='user-info']"));
    }

}
