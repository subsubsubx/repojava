import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class AddressBookTest {
    private static WebDriver driver;

    @BeforeEach
    public void before() {
        if (driver == null) {
            driver = new ChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().maximize();
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@type='submit']")).click();
        }
    }

/*
    @AfterAll
    public static void after(){
        driver.quit();
    }
*/

    @Test
    void createGroups() {
        if (!isElementPresent(By.name("new"))) {
            clickElement(By.linkText("groups"));
        }
        for (int i = 1; i <= 5; i++) {
            clickElement(By.name("new"));
            clickElement(By.name("group_name")).sendKeys("qwerty" + i);
            clickElement(By.name("group_header")).sendKeys("zxcvbn" + i);
            clickElement(By.name("group_footer")).sendKeys("asdfgh" + i);
            clickElement(By.name("submit"));
            clickElement(By.linkText("group page"));
        }
    }

    @Test
    void deleteAllGroups() {
        if (!isElementPresent(By.name("new"))) {
            clickElement(By.linkText("groups"));
        }
        List<WebElement> list = driver.findElements(By.name("selected[]"));
        if (list.size() != 0) {
            for (WebElement element : list) {
                element.click();
            }
            clickElement(By.name("delete"));
            clickElement(By.linkText("group page"));
            Assertions.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.name("selected[]")));
        } else {
            createGroups();
            deleteAllGroups();
        }
    }

    public WebElement clickElement(By by) {
        WebElement ele = driver.findElement(by);
        ele.click();
        return ele;
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

