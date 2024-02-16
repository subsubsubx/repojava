package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class AppManager {

    private WebDriver driver;
    private LoginHelper session;
    private GroupHelper group;
    private ContactHelper contact;
    private Properties properties;
    private JdbcHelper jdbcHelper;
    private HibernateHelper hbm;


    public void init(String browser, Properties properties) throws MalformedURLException {
        this.properties = properties;
        if (driver == null) {
            String seleniumServer = properties.getProperty("selenium.server");
            if ("chrome".equals(browser)) {
                if (seleniumServer != null) {
                    driver = new RemoteWebDriver(new URL(seleniumServer), new ChromeOptions());
                } else {
                    driver = new ChromeDriver();
                }
            } else if ("firefox".equals(browser)) {
                if (seleniumServer != null) {
                    driver = new RemoteWebDriver(new URL(seleniumServer), new FirefoxOptions());
                } else {
                    driver = new FirefoxDriver();
                }

            } else throw new IllegalArgumentException(String.format("Browser %s is not supported", browser));
        }
        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
        driver.get(properties.getProperty("web.baseUrl"));
        driver.manage().window().maximize();
        getSession().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
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