package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.InputMismatchException;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(AppManager appManager) {
        this.appManager = appManager;
    }


    public void openContactPage() {
        clickElement(By.linkText("add new"));
    }

    public void openHomePage() {
        clickElement(By.linkText("home"));
    }


    public void createContact(int times, ContactData contact) {
        for (int i = 1; i <= times; i++) {
            openContactPage();
            fillContactInputFields(contact);
 /*
            List<WebElement> bdayList = appManager.getDriver().findElements(By
                    .xpath("//select[@name='bday']/option"));
            selectFromList(contact.getBday(), bdayList);
            List<WebElement> bmonthList = appManager.getDriver().findElements(By
                    .xpath("//select[@name='bmonth']/option"));
            selectFromList(contact.getBmonth(), bmonthList);
            clickElement(By.name("byear")).sendKeys(contact.getByear());

          List<WebElement> adayList = appManager.getDriver().findElements(By
                    .xpath("//select[@name='aday']/option"));
            selectFromList(contact.getAday(), adayList);
            List<WebElement> amonthList = appManager.getDriver().findElements(By
                    .xpath("//select[@name='amonth']/option"));
            selectFromList(contact.getAmonth(), amonthList);
            clickElement(By.name("ayear")).sendKeys(contact.getAyear());
*/
            selectFromDayMonthList("bday", contact);
            selectFromDayMonthList("bmonth", contact);
            selectYear("byear", contact);
            selectFromDayMonthList("aday", contact);
            selectFromDayMonthList("amonth", contact);
            selectYear("ayear", contact);
            List<WebElement> groupList = getList(By.name("new_group"));
            if (groupList.size() > 1) {
                selectFromList(contact.getGroup(), groupList);
            }
            submitAndReturn(By.name("submit"));
        }
    }

    private void fillContactInputFields(ContactData contact) {
        setField(By.name("firstname"), contact.getFirstname());
        setField(By.name("middlename"), contact.getMiddlename());
        setField(By.name("lastname"), contact.getLastname());
        setField(By.name("nickname"), contact.getNickname());
        setField(By.name("title"), contact.getTitle());
        setField(By.name("company"), contact.getCompany());
        setField(By.name("address"), contact.getAddress());
        setField(By.name("home"), contact.getHome());
        setField(By.name("mobile"), contact.getMobile());
        setField(By.name("work"), contact.getWork());
        setField(By.name("fax"), contact.getFax());
        setField(By.name("email"), contact.getEmail());
        setField(By.name("email2"), contact.getEmail2());
        setField(By.name("email3"), contact.getEmail3());
        setField(By.name("homepage"), contact.getHomepage());
    }


    public void deleteContacts() {
        openHomePage();
        if (getSelectorCount() == 0) {
            createContact(3, new ContactData()
                    .withFirstname("Миша")
                    .withLastname("Маваши"));
            deleteContacts();
        } else {
            clickAllElements(getList(By.name("selected[]")));
            clickElement(By.cssSelector("input[value=Delete]"));
            // do smth
        }
    }

    public void deleteContacts(int num) {
        openHomePage();
        if (getSelectorCount() == 0) {
            createContact(num, new ContactData());
            deleteContacts(num);
        } else {
            getSelectorList().get(num - 1).click();
            clickElement(By.cssSelector("input[value=Delete]"));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // do smth
        }
    }


    private void selectFromDayMonthList(String s, ContactData contact) {
        List<WebElement> list = appManager.getDriver().findElements(By
                .xpath("//select[@name='" + s + "']/option"));
        switch (s) {
            case "bday" -> selectFromList(contact.getBday(), list);
            case "bmonth" -> selectFromList(contact.getBmonth(), list);
            case "aday" -> selectFromList(contact.getAday(), list);
            case "amonth" -> selectFromList(contact.getAmonth(), list);
            default -> throw new InputMismatchException(String.format("%s not found", s));
        }
    }

    private void selectYear(String s, ContactData contact) {
        switch (s) {
            case "byear" -> setField(By.name("byear"), contact.getByear());
            case "ayear" -> setField(By.name("ayear"), contact.getAyear());
            default -> throw new InputMismatchException(String.format("%s not found", s));
        }
    }

    private void submitAndReturn(By by) {
        clickElement(by);
        clickElement(By.linkText("home page"));
    }
}



