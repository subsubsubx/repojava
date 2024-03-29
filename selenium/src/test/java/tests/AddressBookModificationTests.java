package tests;

import common.Common;
import io.qameta.allure.Allure;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

public class AddressBookModificationTests extends BaseTest {


    @Test
    void modifyGroupTest() {
        Allure.step("Precondition check", step -> {
            if (appManager.getHbm().getGroupsCount() == 0) {
                appManager.getHbm().createGroup(new GroupData()
                        .withName(Common.randomString(5))
                        .withHeader(Common.randomString(5))
                        .withFooter(Common.randomString(5)));
            }
        });

        List<GroupData> before = appManager.getHbm().getGroups();
        int randomIndex = new Random().nextInt(before.size());
        GroupData randomGroup = new GroupData()
                .withName("Name Modified!" + Common.randomString(10));
        appManager.getGroup().modifyGroup(before.get(randomIndex), randomGroup);
        List<GroupData> after = appManager.getHbm().getGroups();
        List<GroupData> expectedList = new ArrayList<>(before);
        expectedList.set(randomIndex, randomGroup.withId(before.get(randomIndex).getId()));

        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }

    @Test
    void modifyContactTest() throws IOException {
        Allure.step("Precondition check", step -> {
                    if (appManager.getHbm().getContactsCount() == 0) {
                        for (int i = 0; i < 2; i++) {
                            appManager.getContact().createContact(new ContactData()
                                    .withFirstname(Common.randomString(5))
                                    .withLastname(Common.randomString(5)));
                        }
                    }
                });
        List<ContactData> before = appManager.getHbm().getContacts();
        int randomIndex = new Random().nextInt(before.size());
        ContactData randomContact = contactsProvider().get(0)
                .withFirstname("FirstnameModified!" + Common.randomString(4))
                .withLastname("LastnameModified!" + Common.randomString(4));
        appManager.getContact().modifyContact(before.get(randomIndex), randomContact);
        List<ContactData> after = appManager.getHbm().getContacts();
        List<ContactData> expectedList = new ArrayList<>(before);
        expectedList.set(randomIndex, randomContact.withId(before.get(randomIndex).getId()));
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }

}
