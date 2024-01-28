package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactHelper extends HelperBase {

    public ContactHelper(AppManager appManager) {
        super(appManager);
        //   this.appManager = appManager;
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
        submitAndReturn(By.name("submit"));
    }

    public void createContact(ContactData contact, GroupData groupData) {
        openAddNewContact();
        fillContactInputFields(contact);
        selectFromDayMonthList("bday", contact);
        selectFromDayMonthList("bmonth", contact);
        selectYear("byear", contact);
        selectFromDayMonthList("aday", contact);
        selectFromDayMonthList("amonth", contact);
        selectYear("ayear", contact);
        selectGroup(groupData);
        submitAndReturn(By.name("submit"));
    }

    private void selectGroup(GroupData groupData) {
        new Select(appManager.getDriver().findElement(By.name("new_group"))).selectByValue(groupData.getId());
    }

    public void addContactToGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectCheckboxContact(contact);
        new Select(appManager.getDriver().findElement(By.name("to_group"))).selectByValue(group.getId());
        clickElement(By.name("add"));
        clickElement(By.partialLinkText("group page"));
    }

    public void deleteContactFromGroup(ContactData contact, GroupData groupData) {
        openHomePage();
        new Select(appManager.getDriver().findElement(By.name("group"))).selectByValue(groupData.getId());
        selectCheckboxContact(contact);
        clickElement(By.cssSelector("input[name=remove]"));
        clickElement(By.partialLinkText("group page"));
        new Select(appManager.getDriver().findElement(By.name("group"))).selectByVisibleText("[all]");
    }

    private void fillContactInputFields(ContactData contact) {
        clearSetField(By.name("firstname"), contact.getFirstname());
        clearSetField(By.name("middlename"), contact.getMiddlename());
        clearSetField(By.name("lastname"), contact.getLastname());
        clearSetField(By.name("nickname"), contact.getNickname());
        clearSetField(By.name("company"), contact.getCompany());
        if (contact.getPhoto() != null) {
            attachFile(By.name("photo"), contact.getPhoto());
        }
        clearSetField(By.name("title"), contact.getTitle());
        clearSetField(By.name("address"), contact.getAddress());
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

    public void clickEditContract(ContactData contactData) {
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
            String name = element.findElement(By.xpath("./td[3]")).getText();
            String surname = element.findElement(By.xpath("./td[2]")).getText();
            WebElement checkbox = element.findElement(By.name("selected[]"));
            String id = checkbox.getAttribute("value");
            res.add(new ContactData().withId(id).withFirstname(name).withLastname(surname));
        }
        return res;
    }


    public String getPhones(ContactData contact) {
        return appManager.getDriver().findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[6]", contact.getId()))).getText();
    }

    public String getEmails(ContactData contact) {
        List<WebElement> elements = appManager.getDriver().findElements(By.xpath(
                String.format("//input[@id='%s']/../../td[5]//a", contact.getId())));
        return elements.stream()
                .filter(Objects::nonNull)
                .map(WebElement::getText)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public String getValueFromName(String name) {
        return appManager.getDriver().findElement(By.name(name)).getAttribute("value");
    }

    public String getAddress(ContactData contact) {
        return appManager.getDriver()
                .findElement(By.xpath(String.format("//input[@id='%s']/../../td[4]", contact.getId()))).getText();
    }
}



