package tests;

import manager.AppManager;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected static AppManager appManager;


    @BeforeEach
    public void before() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        appManager.init(System.getProperty("browser", "chrome"));
    }

/*    @AfterEach
    public void after() {
        appManager.getDriver().quit();
    }*/

}

