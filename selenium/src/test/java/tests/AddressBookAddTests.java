package tests;

import common.Common;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

public class AddressBookAddTests extends BaseTest {


    @ParameterizedTest
    @MethodSource("groupsProvider")
    void createGroupsTest(GroupData groupData) {
        List<GroupData> before = appManager.getHbm().getGroupList();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getHbm().getGroupList();
        List<GroupData> expectedList = new ArrayList<>(before);
        Comparator<GroupData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        after.sort(comparator);
        expectedList.add(groupData.withId(after.get(after.size() - 1).getId()));
        expectedList.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }


    @ParameterizedTest
    @MethodSource("negativeGroupsProvider")
    void createGroupNegativeTest(GroupData groupData) {
        List<GroupData> before = appManager.getHbm().getGroupList();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getHbm().getGroupList();
        Assertions.assertEquals(before, after);
    }

    @Test
    void createContactWithGroup() {
        ContactData contactData = new ContactData()
                .withNickname("asd")
                .withPhoto(Common.getRandomFile("src/test/resources/img"));
        if (appManager.getHbm().getGroupCount() == 0) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
        }
        GroupData group = appManager.getHbm().getGroupList().get(0);
        List<ContactData> before = appManager.getHbm().getContactsInGroup(group);
        appManager.getContact().createContact(contactData, group);
        List<ContactData> after = appManager.getHbm().getContactsInGroup(group);
        List<ContactData> expectedList = new ArrayList<>(before);
        Comparator<ContactData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        after.sort(comparator);
        expectedList.add(contactData.withId(after.get(after.size() - 1).getId()));
        expectedList.sort(comparator);
        Assertions.assertEquals(expectedList, after);

    }


    @Test
    void addContactToGroup() {
        if (appManager.getHbm().getGroupCount() == 0) {
            appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
        }
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withNickname("asd")
                    .withPhoto(Common.getRandomFile("src/test/resources/img")));
        }
        long rnd = new Random().nextLong(appManager.getHbm().getContactCount());

        ContactData randomContact = appManager.getJdbcHelper().getContactList().get((int) rnd);
        GroupData randomGroup = new GroupData().withName("Hello World");
        for (int i = 0; i < appManager.getHbm().getGroupCount(); i++) {
            if (!appManager.getHbm().getGroupList().get(i).getName().equals("")) {
                randomGroup = appManager.getHbm().getGroupList().get(i);
                break;
            }
        }
        Set<ContactData> beforeSet = new LinkedHashSet<>(appManager.getHbm().getContactsInGroup(randomGroup));
        appManager.getContact().addContactToGroup(randomContact, randomGroup);
        List<ContactData> after = appManager.getHbm().getContactsInGroup(randomGroup);
        Set<ContactData> afterSet = new LinkedHashSet<>(appManager.getHbm().getContactsInGroup(randomGroup));
        Set<ContactData> expectedSet = new LinkedHashSet<>(beforeSet);
        expectedSet.add(randomContact.withId(after.get(after.size() - 1).getId()));
        Assertions.assertEquals(expectedSet, afterSet);
    }


    @ParameterizedTest
    @MethodSource("contactsProvider")
    void createContactsTest(ContactData contactData) {
        List<ContactData> before = appManager.getHbm().getContactList();
        appManager.getContact().createContact(contactData);
        List<ContactData> after = appManager.getHbm().getContactList();
        List<ContactData> expectedList = new ArrayList<>(before);
        Comparator<ContactData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        after.sort(comparator);
        expectedList.add(contactData.withId(after.get(after.size() - 1).getId()));
        expectedList.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }
}
