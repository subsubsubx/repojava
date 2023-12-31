package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class AppManager {

    private WebDriver driver;
    private LoginHelper session;
    private GroupHelper group;
    private ContactHelper contact;


    public void init(String browser) {
        if (driver == null) {
            switch (browser) {
                case "chrome" -> driver = new ChromeDriver();
                case "firefox" -> driver = new FirefoxDriver();
                default -> throw new IllegalArgumentException(String.format("Browser %s is not supported", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().maximize();
            getSession().login("admin", "secret");
        }
    }

    public LoginHelper getSession() {
        if (session == null) {
            session = new LoginHelper(this);
        }
        return session;
    }

    public GroupHelper getGroup() {
        if (group == null) {
            group = new GroupHelper(this);
        }
        return group;
    }

    public ContactHelper getContact() {
        if (contact == null) {
            contact = new ContactHelper(this);
        }
        return contact;
    }

    public WebDriver getDriver() {
        return driver;
    }
}