package tests;

import common.Common;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddressBookRemoveTests extends BaseTest {

    @Test
    void deleteGroupTest() {
        if (appManager.getHbm().getGroupCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
            }
        }
        List<GroupData> oldList = appManager.getHbm().getGroupList();
        Random rnd = new Random();
        int index = rnd.nextInt(oldList.size());
        appManager.getGroup().deleteGroup(oldList.get(index));
        List<GroupData> newList = appManager.getHbm().getGroupList();
        List<GroupData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }

    @Test
    void deleteAllGroupsTest() {
        if (appManager.getHbm().getGroupCount() == 0) {
            for (int i = 0; i < 3; i++) {
                appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
            }
        }
        appManager.getGroup().deleteAllGroups();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }

    @Test
    void deleteAllContactsTest() {
        appManager.getContact().deleteAllContacts();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }

    @Test
    void removeContactFromGroup() {
        if (appManager.getHbm().getGroupCount() == 0) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
        }
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withNickname("asd")
                    .withPhoto(Common.getRandomFile("src/test/resources/img")));
        }

        long rnd = new Random().nextLong(appManager.getHbm().getContactCount());
        ContactData randomContact = appManager.getHbm().getContactList().get((int) rnd);
        GroupData randomGroup = new GroupData().withName("Hello World");
        for (int i = 0; i < appManager.getHbm().getGroupCount(); i++) {
            if (!appManager.getHbm().getGroupList().get(i).getName().equals("")) {
                randomGroup = appManager.getHbm().getGroupList().get(i);
                break;
            }
        }
        appManager.getContact().addContactToGroup(randomContact, randomGroup);

        List<ContactData> before = appManager.getHbm().getContactsInGroup(randomGroup);

        appManager.getContact().deleteContactFromGroup(randomContact, randomGroup);

        List<ContactData> after = appManager.getHbm().getContactsInGroup(randomGroup);
        List<ContactData> expectedList = new ArrayList<>(before);
        expectedList.remove(appManager.getHbm().getContactList().get((int) rnd));

        Assertions.assertEquals(expectedList, after);
    }


    @Test
    void deleteContactTest() {
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData().withFirstname("Hello").withLastname("World"));
        }
        List<ContactData> oldList = appManager.getHbm().getContactList();
        int index = new Random().nextInt(oldList.size());
        appManager.getContact().deleteContacts(oldList.get(index));
        List<ContactData> newList = appManager.getHbm().getContactList();
        List<ContactData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }


}

