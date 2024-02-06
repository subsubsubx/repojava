package tests;

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
        if (appManager.getHbm().getGroupsCount() == 0) {
            for (int i = 0; i < 2; i++) {
                appManager.getHbm().createGroup(new GroupData("", "qwe", "asd", "zxc"));
            }
        }
        List<GroupData> oldList = appManager.getHbm().getGroups();
        Random rnd = new Random();
        int index = rnd.nextInt(oldList.size());
        appManager.getGroup().deleteGroup(oldList.get(index));
        List<GroupData> newList = appManager.getHbm().getGroups();
        List<GroupData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }

    @Test
    void deleteAllGroupsTest() {
        if (appManager.getHbm().getGroupsCount() == 0) {
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

        List<ContactData> before;
        List<ContactData> after;

        // получить группу, в которой есть контакт
        Optional<GroupData> validGroup = getValidGroups().stream()
                .filter(group -> appManager.getHbm().getContactsInGroup(group).size() > 0)
                .findFirst();
        //если такая группа нашлась, удалить контакт
        if (validGroup.isPresent()) {
            before = new ArrayList<>(appManager.getHbm().getContactsInGroup(validGroup.get()));
            appManager.getContact().deleteContactFromGroup(
                    appManager.getHbm().getContactsInGroup(validGroup.get()).get(new Random()
                            .nextInt(appManager.getHbm().getContactsInGroup(validGroup.get()).size())), validGroup.get());
            after = new ArrayList<>(appManager.getHbm().getContactsInGroup(validGroup.get()));
        } else {
            // если такой группы нет - добавить рандомный контакт в рандомную группу, и затем удалить
            GroupData randomGroup = getValidGroups().get(new Random().nextInt(getValidGroups().size()));
            ContactData randomContact = getValidContactsWithoutGroups().get(new Random()
                    .nextInt(getValidContactsWithoutGroups().size()));

            appManager.getContact().addContactToGroup(randomContact, randomGroup);
            before = new ArrayList<>(appManager.getHbm().getContactsInGroup(randomGroup));

            appManager.getContact().deleteContactFromGroup(randomContact, randomGroup);
            after = new ArrayList<>(appManager.getHbm().getContactsInGroup(randomGroup));
        }
        try {
            Thread.sleep(1500);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<ContactData> expectedList = new ArrayList<>(before);
        List<ContactData> func = before.stream()
                .filter(e -> !after.contains(e))
                .toList();
        try {
            expectedList.remove(func.get(0));
        } catch (Exception e) {
            Assertions.fail("corrupted data");
        }
        Assertions.assertEquals(Set.copyOf(expectedList), Set.copyOf(after));
    }


    @Test
    void deleteContactTest() {
        if (appManager.getHbm().getContactsCount() == 0) {
            appManager.getContact().createContact(new ContactData().withFirstname("Hello").withLastname("World"));
        }
        List<ContactData> oldList = appManager.getHbm().getContacts();
        int index = new Random().nextInt(oldList.size());
        appManager.getContact().deleteContacts(oldList.get(index));
        List<ContactData> newList = appManager.getHbm().getContacts();
        List<ContactData> expectedList = new ArrayList<>(oldList);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newList);
    }
}



