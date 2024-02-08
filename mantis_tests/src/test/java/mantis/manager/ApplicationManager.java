package mantis.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {


    private MailHelper mailHelper;
    private WebDriver driver;
    private String browser;
    private SessionHelper session;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;
    private RegistrationHelper regHelper;
    private Properties properties;

    public void init(String browser, Properties properties) {
        this.browser = browser;
        this.properties = properties;
    }

    public SessionHelper getSession() {
        if (session == null) {
            session = new SessionHelper(this);
        }
        return session;
    }

    public String getProperty(String val) {
        return properties.getProperty(val);
    }

    public WebDriver getDriver() {
        if (driver == null) {
            switch (browser) {
                case "chrome" -> driver = new ChromeDriver();
                case "firefox" -> driver = new FirefoxDriver();
                default -> throw new IllegalArgumentException(String.format("Browser %s is not supported", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get(properties.getProperty("web.baseUrl"));
            driver.manage().window().maximize();
        }
        return driver;
    }


    public JamesCliHelper getCliHelper() {
        if (jamesCliHelper == null) {
            jamesCliHelper = new JamesCliHelper(this);
        }
        return jamesCliHelper;
    }

    public MailHelper getMailHelper() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }
    public RegistrationHelper getRegHelper() {
        if (regHelper == null) {
            regHelper = new RegistrationHelper(this);
        }
        return regHelper;
    }

    public HttpSessionHelper getHttpClient() {
        if (httpSessionHelper == null) {
            httpSessionHelper = new HttpSessionHelper(this);
        }
        return httpSessionHelper;
    }
}

