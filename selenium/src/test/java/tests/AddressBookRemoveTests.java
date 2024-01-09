package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Random;

public class AddressBookRemoveTests extends BaseTest {

    @Test
    void deleteGroupTest() {
        if (appManager.getGroup().getGroupCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getGroup().createGroup(new GroupData("", "qwe", "asd", "zxc"));
            }
        }
        ArrayList<GroupData> oldList = appManager.getGroup().getGroupList();
        Random rnd = new Random();
        int index = rnd.nextInt(oldList.size());
        appManager.getGroup().deleteGroup(oldList.get(index));
        ArrayList<GroupData> newList = appManager.getGroup().getGroupList();
        ArrayList<GroupData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }

    @Test
    void deleteAllGroupsTest() {
        if (appManager.getGroup().getGroupCount() == 0) {
            appManager.getGroup().createGroup(new GroupData());
        }
        appManager.getGroup().deleteAllGroups();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }

    @Test
    void deleteContactTest() {
        if (appManager.getContact().getContactCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getContact().createContact(new ContactData().withFirstname("Hello").withLastname("World"));
            }
        }
        ArrayList<ContactData> oldList = appManager.getContact().getContactList();
        Random rnd = new Random();
        int index = rnd.nextInt(oldList.size());
        appManager.getContact().deleteContacts(oldList.get(index));
        ArrayList<ContactData> newList = appManager.getContact().getContactList();
        ArrayList<ContactData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }

    @Test
    void deleteAllContactsTest() {
        appManager.getContact().deleteAllContacts();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }
}

