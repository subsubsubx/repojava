package mantis.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class HelperBase {
    protected ApplicationManager appManager;

    public HelperBase(ApplicationManager appManager) {
        this.appManager = appManager;
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
        WebElement e = appManager.getDriver().findElement(by);
        e.click();
        return e;
    }

    protected void setField(By by, String s) {
        appManager.getDriver().findElement(by).sendKeys(s);

    }

    /*    protected void clickAllElements(List<WebElement> list) {
        list.forEach(WebElement::click);
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

    protected void clearSetField(By by, String s) {
        appManager.getDriver().findElement(by).clear();
        appManager.getDriver().findElement(by).sendKeys(s);
    }

    protected void attachFile(By by, String s) {
        appManager.getDriver().findElement(by).sendKeys(Paths.get(s).toAbsolutePath().toString());
    }*/
}
