package manager;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.nio.file.Paths;
import java.util.List;

public class HelperBase {
    protected AppManager appManager;
    public HelperBase(AppManager appManager){
        this.appManager = appManager;
    }

    protected void clickAllElements(List<WebElement> list) {
        list.forEach(WebElement::click);
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

    protected void clearSetField(By by, String s) {
        appManager.getDriver().findElement(by).clear();
        appManager.getDriver().findElement(by).sendKeys(s);
    }

    protected void attachFile(By by, String s){
        appManager.getDriver().findElement(by).sendKeys(Paths.get(s).toAbsolutePath().toString());
    }


    public List<WebElement> getOptionsList() {
        return getList(By.name("selected[]"));
    }
}
