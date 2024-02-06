package tests;

import common.Common;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AddressBookAddTests extends BaseTest {


    @ParameterizedTest
    @MethodSource("groupsProvider")
    void createGroupsTest(GroupData groupData) {
        List<GroupData> before = appManager.getHbm().getGroups();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getHbm().getGroups();
        List<GroupData> expectedList = new ArrayList<>(before);
        List<GroupData> func = after.stream().filter(e -> !before.contains(e)).toList();
        expectedList.add(func.get(0));

        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }


    @ParameterizedTest
    @MethodSource("negativeGroupsProvider")
    void createGroupNegativeTest(GroupData groupData) {
        List<GroupData> before = appManager.getHbm().getGroups();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getHbm().getGroups();
        Assertions.assertEquals(before, after);
    }

    @Test
    void createContactWithGroup() {
        ContactData contactData = new ContactData()
                .withNickname("asd")
                .withPhoto(Common.getRandomFile("src/test/resources/img"));
        if (appManager.getHbm().getGroupsCount() == 0) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
        }
        GroupData group = appManager.getHbm().getGroups().get(0);
        List<ContactData> before = appManager.getHbm().getContactsInGroup(group);
        appManager.getContact().createContact(contactData, group);
        List<ContactData> after = appManager.getHbm().getContactsInGroup(group);
        List<ContactData> expectedList = new ArrayList<>(before);

        expectedList.add(contactData.withId(after.get(after.size() - 1).getId()));

        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }


/*    @Test
    void addContactToGroup() {
        GroupData randomGroup = appManager.getHbm().getGroupList()
                .stream()
                .filter(group -> !group.getName().equals(""))
                .findFirst()
                .orElseGet(() -> {
                    appManager.getHbm().createGroup(new GroupData("",
                            randomString(3),
                            randomString(4),
                            randomString(5)));
                    return appManager.getHbm().getGroupList()
                            .stream()
                            .filter(group -> !group.getName().equals(""))
                            .findFirst()
                            .orElseThrow();
                });

        ContactData validContact = appManager.getHbm().getContactList()
                .stream()
                .filter(e -> appManager.getHbm().getGroupsInContacts(e).isEmpty())
                .findFirst()
                .orElseGet(() -> {
                    ContactData newContact = new ContactData()
                            .withFirstname(randomString(15))
                            .withLastname(randomString(10))
                            .withPhoto(Common.getRandomFile("src/test/resources/img"));
                    appManager.getContact().createContact(newContact);
                    return appManager.getHbm().getContactList()
                            .stream()
                            .filter(e -> appManager.getHbm().getGroupsInContacts(e).isEmpty())
                            .findFirst()
                            .orElseThrow();
                });
        List<GroupData> before = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact));
        appManager.getContact().addContactToGroup(validContact, randomGroup);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<GroupData> after = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact));
        List<GroupData> expectedList = after.stream().filter(e -> !before.contains(e)).toList();
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }*/


    @Test
    void addContactToGroup() {

        GroupData validGroup = getValidGroups().get(new Random()
                .nextInt(getValidGroups().size()));

        ContactData validContact = getValidContactsWithoutGroups().get(new Random()
                .nextInt(getValidContactsWithoutGroups().size()));

        List<GroupData> before = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact));
        appManager.getContact().addContactToGroup(validContact, validGroup);

        try {
            Thread.sleep(1500);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<GroupData> after = new ArrayList<>(appManager.getHbm().getGroupsInContacts(validContact));
        List<GroupData> expectedList = new ArrayList<>(before);
        List<GroupData> func = after.stream()
                .filter(e -> !before.contains(e))
                .toList();

        try {
            expectedList.add(func.get(0));
        } catch (Exception e) {
            Assertions.fail("corrupted data");
        }
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }


    @ParameterizedTest
    @MethodSource("contactsProvider")
    void createContactsTest(ContactData contactData) {
        List<ContactData> before = appManager.getHbm().getContacts();
        appManager.getContact().createContact(contactData);
        List<ContactData> after = appManager.getHbm().getContacts();
        List<ContactData> expectedList = new ArrayList<>(before);
        expectedList.add(contactData.withId(after.get(after.size() - 1).getId()));
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }
}
