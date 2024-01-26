package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {
    public LoginHelper(AppManager appManager) {
        super(appManager);
     //   this.appManager = appManager;
    }

    public void login(String id, String pwd) {
        setField(By.name("user"), id);
        setField(By.name("pass"), pwd);
        clickElement(By.xpath("//input[@type='submit']"));
    }
}
