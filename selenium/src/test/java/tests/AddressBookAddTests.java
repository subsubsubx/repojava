package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressBookAddTests extends BaseTest {

    @Test
    void createEmptyGroupTest() {
        appManager.getGroup().createGroup(1, new GroupData());
        Assertions.assertTrue(appManager.getGroup().getSelectorCount() >= 1);
    }

    @Test
    void createGroupWithParamsTest() {
        appManager.getGroup().createGroup(1, new GroupData()
                .withName("NAME!!!")
                .withHeader("QWAS WEX EXORT")
                .withFooter("FOOTER!!!!111"));
        Assertions.assertTrue(appManager.getGroup().getSelectorCount() >= 1);
    }

    @Test
    void createGroupWithConstructorTest() {
        appManager.getGroup().createGroup(1, new GroupData("qwe", "asd", "zxc"));
        Assertions.assertTrue(appManager.getGroup().getSelectorCount() >= 1);
    }


    @Test
    void createEmptyContactTest() {
        appManager.getContact().createContact(1, new ContactData());
        Assertions.assertTrue(appManager.getContact().getSelectorCount() >= 1);
    }

    @Test
    void createContactWithParamsTest() {
        appManager.getContact().createContact(1, new ContactData()
                .withFirstname("Firstname")
                .withLastname("Lastname")
                .withMiddlename("Middlename")
                .withNickname("Nickname")
                .withTitle("Title")
                .withCompany("Company")
                .withAddress("Address")
                .withHome("8800")
                .withMobile("555")
                .withWork("3535")
                .withFax("99999999")
                .withEmail("e@mail.com")
                .withEmail2("e@mail2.com")
                .withEmail3("e@mail3.com")
                .withHomepage("homepage.co")
                .withBday("10")
                .withBmonth("May")
                .withByear("1990")
                .withAday("19")
                .withAmonth("June")
                .withAyear("1999"));
        Assertions.assertTrue(appManager.getContact().getSelectorCount() >= 1);
    }

}
