package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(AppManager appManager) {
        this.appManager = appManager;
    }


    public void openAddNewContact() {
        clickElement(By.linkText("add new"));
    }

    public void openHomePage() {
        clickElement(By.linkText("home"));
    }


    public void createContact(ContactData contact) {
        openAddNewContact();
        fillContactInputFields(contact);
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

    private void fillContactInputFields(ContactData contact) {
        clickElement(By.name("firstname")).clear();
        setField(By.name("firstname"), contact.getFirstname());
        clickElement(By.name("middlename")).clear();
        setField(By.name("middlename"), contact.getMiddlename());
        clickElement(By.name("lastname")).clear();
        setField(By.name("lastname"), contact.getLastname());
        clickElement(By.name("nickname")).clear();
        setField(By.name("nickname"), contact.getNickname());
        clickElement(By.name("title")).clear();
        setField(By.name("title"), contact.getTitle());
        clickElement(By.name("company")).clear();
        setField(By.name("company"), contact.getCompany());
        clickElement(By.name("address")).clear();
        setField(By.name("address"), contact.getAddress());
        clickElement(By.name("home")).clear();
        setField(By.name("home"), contact.getHome());
        clickElement(By.name("mobile")).clear();
        setField(By.name("mobile"), contact.getMobile());
        clickElement(By.name("work")).clear();
        setField(By.name("work"), contact.getWork());
        clickElement(By.name("fax")).clear();
        setField(By.name("fax"), contact.getFax());
        clickElement(By.name("email")).clear();
        setField(By.name("email"), contact.getEmail());
        clickElement(By.name("email2")).clear();
        setField(By.name("email2"), contact.getEmail2());
        clickElement(By.name("email3")).clear();
        setField(By.name("email3"), contact.getEmail3());
        clickElement(By.name("homepage")).clear();
        setField(By.name("homepage"), contact.getHomepage());
    }


    public void deleteAllContacts() {
        if (getContactCount() == 0) {
            createContact(new ContactData()
                    .withFirstname("Test")
                    .withLastname("Qwe"));
            deleteAllContacts();
        } else {
            clickAllElements(getOptionsList());
            clickElement(By.cssSelector("input[value=Delete]"));
        }
    }

    public void deleteContacts(ContactData contactData) {
        openHomePage();
        selectCheckboxContact(contactData);
        clickElement(By.cssSelector("input[value=Delete]"));
        openHomePage();
    }


    public void selectCheckboxContact(ContactData contactData) {
        clickElement(By.cssSelector(String.format("input[value='%s']", contactData.getId())));
    }

    public void modifyContact(ContactData contactData, ContactData modify) {
        openHomePage();
        clickEditContract(contactData);
        fillContactInputFields(modify);
        submitAndReturn(By.name("update"));
    }

    private void clickEditContract(ContactData contactData) {
        clickElement(By.xpath("//a[contains(@href, 'edit.php?id=" + contactData.getId() + "')]"));

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

    public int getContactCount() {
        openHomePage();
        return getOptionsList().size();
    }

    public ArrayList<ContactData> getContactList() {
        ArrayList<ContactData> res = new ArrayList<>();
        List<WebElement> list = getList(By.xpath("//tr[@name='entry']"));
        for (WebElement element : list) {
       //     String[] text = element.getText().split(" ", 3);
            String name = element.findElement(By.xpath("./td[3]")).getText();
            String surname = element.findElement(By.xpath("./td[2]")).getText();
            WebElement checkbox = element.findElement(By.name("selected[]"));
            String id = checkbox.getAttribute("value");
            res.add(new ContactData().withId(id).withFirstname(name).withLastname(surname));
         //   res.add(new ContactData().withId(id).withFirstname(text[1]).withLastname(text[0]));
        }
        return res;
    }
}



