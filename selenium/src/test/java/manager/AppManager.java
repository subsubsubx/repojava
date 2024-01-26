package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;


public class AppManager {

    private WebDriver driver;
    private LoginHelper session;
    private GroupHelper group;
    private ContactHelper contact;
    private Properties properties;
    private JdbcHelper jdbcHelper;
    private  HibernateHelper hbm;




    public void init(String browser, Properties properties) {
        this.properties = properties;
        if (driver == null) {
            switch (browser) {
                case "chrome" -> driver = new ChromeDriver();
                case "firefox" -> driver = new FirefoxDriver();
                default -> throw new IllegalArgumentException(String.format("Browser %s is not supported", browser));
            }
                    Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get(properties.getProperty("web.baseUrl"));
            //  driver.get("http://localhost/addressbook/");
            driver.manage().window().maximize();
            //  getSession().login("admin", "secret");
            getSession().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
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

    public HibernateHelper getHbm() {
        if (hbm == null) {
            hbm = new HibernateHelper(this);
        }
        return hbm;
    }

    public JdbcHelper getJdbcHelper() {
        if (jdbcHelper == null) {
            jdbcHelper = new JdbcHelper(this);
        }
        return jdbcHelper;
    }

    public Properties getProperties() {
        return properties;
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