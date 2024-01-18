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
        clearSetField(By.name("firstname"), contact.getFirstname());
        clearSetField(By.name("middlename"), contact.getMiddlename());
        clearSetField(By.name("lastname"), contact.getLastname());
        clearSetField(By.name("nickname"), contact.getNickname());
        if (contact.getPhoto() != null) {
            attachFile(By.name("photo"), contact.getPhoto());
        }
        clearSetField(By.name("title"), contact.getTitle());
        clearSetField(By.name("home"), contact.getHome());
        clearSetField(By.name("mobile"), contact.getMobile());
        clearSetField(By.name("work"), contact.getWork());
        clearSetField(By.name("fax"), contact.getFax());
        clearSetField(By.name("email"), contact.getEmail());
        clearSetField(By.name("email2"), contact.getEmail2());
        clearSetField(By.name("email3"), contact.getEmail3());
        clearSetField(By.name("homepage"), contact.getHomepage());
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
        openHomePage();
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



