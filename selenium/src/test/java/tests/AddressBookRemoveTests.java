package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class AddressBookRemoveTests extends BaseTest {


    @Test
    void deleteGroupTest() {
        appManager.getGroup().deleteGroups(3);
    }

    @Test
    void deleteAllGroupsTest() {
        appManager.getGroup().deleteGroups();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }

    @Test
    void deleteContactTest() {
        appManager.getContact().deleteContacts(1);
    }

    @Test
    void deleteAllContactsTest() {
        appManager.getContact().deleteContacts();
        Assertions.assertThrows(NoSuchElementException.class, () -> appManager.getDriver()
                .findElement(By.name("selected[]")));
    }

}

