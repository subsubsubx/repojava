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
        if (appManager.getHbm().getGroupCount() == 0) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
        }
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withNickname("asd")
                    .withPhoto(Common.getRandomFile("src/test/resources/img")));
        }
        List<ContactData> before;
        List<ContactData> after;
        Optional<GroupData> validGroup = appManager.getHbm().getGroupList()
                .stream()
                .filter(group -> !group.getName().equals("") && appManager.getHbm().getContactsInGroup(group).size() > 0)
                .findFirst();
        if (validGroup.isEmpty()) {
            ContactData rndContact = appManager.getHbm().getContactList()
                    .get(new Random().nextInt((int) appManager.getHbm().getContactCount()));
            Optional<GroupData> rndGroup = appManager.getHbm().getGroupList().stream()
                    .filter(group -> !group.getName().equals(""))
                    .findFirst();
            if (rndGroup.isEmpty()) {
                appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
                rndGroup = appManager.getHbm().getGroupList().stream()
                        .filter(group -> !group.getName().equals(""))
                        .findFirst();
            }
            appManager.getContact().addContactToGroup(rndContact, rndGroup.get());
            before = new ArrayList<>(appManager.getHbm().getContactsInGroup(rndGroup.get()));
            appManager.getContact().deleteContactFromGroup(rndContact, rndGroup.get());
            after = new ArrayList<>(appManager.getHbm().getContactsInGroup(rndGroup.get()));
        } else {
            before = new ArrayList<>(appManager.getHbm().getContactsInGroup(validGroup.get()));
            appManager.getContact().deleteContactFromGroup(
                    appManager.getHbm().getContactsInGroup(validGroup.get()).get(0)
                    , validGroup.get());
            after = new ArrayList<>(appManager.getHbm().getContactsInGroup(validGroup.get()));
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<ContactData> expectedList = new ArrayList<>(before);
        List<ContactData> func = before.stream().filter(e -> !after.contains(e)).toList();
        expectedList.remove(func.get(0));
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }

       /* Optional<ContactData> validContact = appManager.getHbm().getContactList()
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
            appManager.getContact().addContactToGroup(validContact.get(), randomGroup.get());*/

         /*   List<GroupData> before;
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
            appManager.getContact().addContactToGroup(validContact.get(), randomGroup.get());*/
/*        } else{
            before = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact.get()));
            appManager.getContact().deleteContactFromGroup(validContact.get(), randomGroup.get());
        }

        List<GroupData> after = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact.get()));
        List<GroupData> expectedList = new ArrayList<>(before);
        List<GroupData> func = before.stream().filter(e -> !after.contains(e)).toList();
        expectedList.remove(func.get(0));
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));*/


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



