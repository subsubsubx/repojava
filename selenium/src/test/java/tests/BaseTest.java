package tests;

import manager.AppManager;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    protected static AppManager appManager;


    @BeforeEach
    public void before() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        appManager.init(System.getProperty("browser", "chrome"));
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + (int) (Math.random() * 26)));
        }
        return sb.toString();
    }

    public static List<GroupData> groupsProvider() {
        List<GroupData> res = new ArrayList<>();
        for (String name : List.of("", "some name")) {
            for (String header : List.of("", "some header")) {
                for (String footer : List.of("", "some footer")) {
                    res.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
                }
            }
        }
        for (int i = 1; i <= 3; i++) {
            res.add(new GroupData()
                    .withName(randomString(i * 10))
                    .withHeader(randomString(i * 9))
                    .withFooter(randomString(i * 8)));
        }
        return res;
    }

    public static List<GroupData> negativeGroupsProvider() {
        return new ArrayList<GroupData>(List.of(new GroupData()
                        .withName("some name'")
                        .withHeader("some header")
                        .withFooter("some footer")));
    }

    public static List<ContactData> contactsProvider() {
        List<ContactData> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            res.add(new ContactData()
                    .withFirstname(randomString(i + 10))
                    .withLastname(randomString(i + 11))
                    .withMiddlename(randomString(i + 12))
                    .withNickname(randomString(i + 13))
                    .withTitle(randomString(i + 14))
                    .withCompany(randomString(i + 15))
                    .withAddress(randomString(i + 16))
                    .withHome(randomString(i + 17))
                    .withMobile(randomString(i + 18))
                    .withWork(randomString(i + 19))
                    .withEmail(randomString(i + 20))
                    .withFax(randomString(i + 21))
                    .withEmail2(randomString(i + 22))
                    .withEmail3(randomString(i + 23))
                    .withHomepage(randomString(i + 24))
                    .withBday("11")
                    .withBmonth("January")
                    .withByear("2000")
                    .withAday("21")
                    .withAmonth("May")
                    .withAyear("1993"));
        }
        return res;
    }

/*    @AfterEach
    public void after() {
        appManager.getDriver().quit();
    }*/

}

