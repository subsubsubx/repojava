package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddressBookAddTests extends BaseTest {

    @ParameterizedTest
    @MethodSource("groupsProvider")
    void createGroupsTest(GroupData groupData) {
        List<GroupData> before = appManager.getGroup().getGroupList();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getGroup().getGroupList();
        List<GroupData> expectedList = new ArrayList<>(before);
        Comparator<GroupData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        after.sort(comparator);
        expectedList.add(groupData.withId(after.get(after.size() - 1).getId()).withFooter("").withHeader(""));
        expectedList.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }

    @ParameterizedTest
    @MethodSource("negativeGroupsProvider")
    void createGroupNegativeTest(GroupData groupData) {

        List<GroupData> before = appManager.getGroup().getGroupList();
        appManager.getGroup().createGroup(groupData);
        List<GroupData> after = appManager.getGroup().getGroupList();
        Assertions.assertEquals(before, after);
    }

    @ParameterizedTest
    @MethodSource("contactsProvider")
    void createContactsTest(ContactData contactData) {
        List<ContactData> before = appManager.getContact().getContactList();
        appManager.getContact().createContact(contactData);
        List<ContactData> after = appManager.getContact().getContactList();
        List<ContactData> expectedList = new ArrayList<>(before);
        Comparator<ContactData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        after.sort(comparator);
        expectedList.add(contactData.withId(after.get(after.size() - 1).getId()));
        expectedList.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }
}
