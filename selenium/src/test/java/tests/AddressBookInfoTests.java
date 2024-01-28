package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressBookInfoTests extends BaseTest {


    @Test
    void testContactData() {
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withFirstname("TestAll")
                    .withHome("")
                    .withAddress("Улица пушкина дом колотушкина")
                    .withMobile("88005553535")
                    .withWork("")
                    .withEmail("")
                    .withEmail2("")
                    .withEmail3("hello@world.com"));
        }
        List<ContactData> contacts = appManager.getHbm().getContactList();
        ContactData randomContact = contacts.get(new Random().nextInt(contacts.size()));

        String expectedValue = Stream.of
                        (appManager.getContact().getPhones(randomContact),
                        appManager.getContact().getEmails(randomContact),
                        appManager.getContact().getAddress(randomContact))
                .filter(Objects::nonNull)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));

        appManager.getContact().clickEditContract(randomContact);

        String actualValue = Stream.of
                        (appManager.getContact().getValueFromName("home"),
                        appManager.getContact().getValueFromName("mobile"),
                        appManager.getContact().getValueFromName("work"),
                        appManager.getContact().getValueFromName("email"),
                        appManager.getContact().getValueFromName("email2"),
                        appManager.getContact().getValueFromName("email3"),
                        appManager.getContact().getValueFromName("address"))
                .filter(Objects::nonNull)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedValue, actualValue);

        appManager.getContact().openHomePage();
    }

    @Test
    void testPhoneUi() {
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withFirstname("TestPhone")
                    .withHome("")
                    .withMobile("88005553535")
                    .withWork(""));
        }

        List<ContactData> contacts = appManager.getHbm().getContactList();
        ContactData randomContact = contacts.get(new Random().nextInt(contacts.size()));
        String expected = appManager.getContact().getPhones(randomContact);
        appManager.getContact().clickEditContract(randomContact);
        String actual = Stream.of
                        (appManager.getContact().getValueFromName("home"),
                                appManager.getContact().getValueFromName("mobile"),
                                appManager.getContact().getValueFromName("work"))
                .filter(Objects::nonNull)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));
        appManager.getContact().openHomePage();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void testEmailUi() {
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withFirstname("TestEmail")
                    .withEmail("")
                    .withEmail2("")
                    .withEmail3("hello@world.com"));
        }
        List<ContactData> contacts = appManager.getHbm().getContactList();
        ContactData randomContact = contacts.get(new Random().nextInt(contacts.size()));
        String expected = appManager.getContact().getEmails(randomContact);
        appManager.getContact().clickEditContract(randomContact);
        String actual = Stream.of
                        (appManager.getContact().getValueFromName("email"),
                                appManager.getContact().getValueFromName("email2"),
                                appManager.getContact().getValueFromName("email3"))
                .filter(Objects::nonNull)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));
        appManager.getContact().openHomePage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testAddressUi() {
        if (appManager.getHbm().getContactCount() == 0) {
            appManager.getContact().createContact(new ContactData()
                    .withFirstname("TestAddress")
                    .withAddress("Улица пушкина дом колотушкина"));
        }
        List<ContactData> contacts = appManager.getHbm().getContactList();
        ContactData randomContact = contacts.get(new Random().nextInt(contacts.size()));
        String expected = appManager.getContact().getAddress(randomContact);
        appManager.getContact().clickEditContract(randomContact);
        String actual = Stream.of
                        (appManager.getContact().getValueFromName("address"))
                .filter(Objects::nonNull)
                .filter(e -> !e.equals(""))
                .collect(Collectors.joining("\n"));
        appManager.getContact().openHomePage();
        Assertions.assertEquals(expected, actual);
    }

}
