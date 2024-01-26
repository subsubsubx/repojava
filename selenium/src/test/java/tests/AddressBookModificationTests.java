package tests;

import common.Common;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AddressBookModificationTests extends BaseTest {


    @Test
    void modifyGroupTest() {
        if (appManager.getHbm().getGroupCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getHbm().createGroup(new GroupData()
                        .withName(Common.randomString(5))
                        .withHeader(Common.randomString(5))
                        .withFooter(Common.randomString(5)));

            }
        }
        List<GroupData> before = appManager.getHbm().getGroupList();
        int randomIndex = new Random().nextInt(before.size());
        GroupData randomGroup = new GroupData()
                .withName("Name Modified!" + Common.randomString(10));
        appManager.getGroup().modifyGroup(before.get(randomIndex), randomGroup);
        List<GroupData> after = appManager.getHbm().getGroupList();
        List<GroupData> expectedList = new ArrayList<>(before);
        expectedList.set(randomIndex, randomGroup.withId(before.get(randomIndex).getId()));
        Comparator<GroupData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        expectedList.sort(comparator);
        after.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }

    @Test
    void modifyContactTest() throws IOException {
        if (appManager.getHbm().getContactCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getContact().createContact(new ContactData()
                        .withFirstname(Common.randomString(5))
                        .withLastname(Common.randomString(5)));
            }
        }
        List<ContactData> before = appManager.getHbm().getContactList();
        int randomIndex = new Random().nextInt(before.size());
        ContactData randomContact = contactsProvider().get(0)
                .withFirstname("FirstnameModified!" + Common.randomString(4))
                .withLastname("LastnameModified!" + Common.randomString(4));
        appManager.getContact().modifyContact(before.get(randomIndex), randomContact);
        List<ContactData> after = appManager.getHbm().getContactList();
        List<ContactData> expectedList = new ArrayList<>(before);
        Comparator<ContactData> comparator = Comparator.comparingInt(a -> Integer.parseInt(a.getId()));
        expectedList.set(randomIndex, randomContact.withId(before.get(randomIndex).getId()));
        expectedList.sort(comparator);
        after.sort(comparator);
        Assertions.assertEquals(expectedList, after);
    }

}
