package tests;

import common.Common;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.*;

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

        List<GroupData> before;
        Optional<GroupData> randomGroup = appManager.getHbm().getGroupList()
                .stream()
                .filter(group -> !group.getName().equals(""))
                .findFirst();
        if (randomGroup.isEmpty()) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
            randomGroup = appManager.getHbm().getGroupList()
                    .stream()
                    .filter(group -> !group.getName().equals(""))
                    .findFirst();
        }
        Optional<ContactData> validContact = appManager.getHbm().getContactList()
                .stream()
                .filter(e -> appManager.getHbm().getGroupsInContacts(e).size() == 0)
                .findFirst();
        if (validContact.isEmpty()) {
            before = null;
            appManager.getContact().createContact(new ContactData()
                    .withFirstname(Common.randomString(15))
                    .withLastname(Common.randomString(10))
                    .withPhoto(Common.getRandomFile("src/test/resources/img")));
            validContact = appManager.getHbm().getContactList().stream()
                    .filter(e -> appManager.getHbm().getGroupsInContacts(e).size() == 0)
                    .findFirst();
        } else {
            appManager.getContact().addContactToGroup(validContact.get(), randomGroup.get());
            before = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact.get()));
            appManager.getContact().deleteContactFromGroup(validContact.get(), randomGroup.get());

        }
     //   WebDriverWait wait = new WebDriverWait(appManager.getDriver(), Duration.ofSeconds(3));
    //    wait.until(ExpectedConditions.)
        List<GroupData> after = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact.get()));
        List<GroupData> expectedList = new ArrayList<>(before);
        List<GroupData> func = before.stream().filter(e -> !after.contains(e)).toList();
        expectedList.remove(func.get(0));
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));


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

