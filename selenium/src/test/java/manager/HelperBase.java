package manager;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HelperBase {
    protected AppManager appManager;

    protected void clickAllElements(List<WebElement> list) {
        for (WebElement element : list) {
            element.click();
        }
    }

    protected boolean isElementPresent(By by) {
        try {
            appManager.getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected WebElement clickElement(By by) {
        WebElement ele = appManager.getDriver().findElement(by);
        ele.click();
        return ele;
    }

    protected WebElement selectFromList(String s, List<WebElement> list) {
        for (WebElement element : list) {
            if (element.getText().equals(s)) {
                element.click();
                return element;
            }
        }
        Assertions.fail(String.format("Element '%s' not found", s));
        return null;
    }

    protected List<WebElement> getList(By by) {
        return appManager.getDriver().findElements(by);
    }

    protected void setField(By by, String s) {
        appManager.getDriver().findElement(by).sendKeys(s);

    }
    protected List<WebElement> getSelectorList(){
        return getList(By.name("selected[]"));
    }
    public int getSelectorCount() {
        return getSelectorList().size();
    }
}
