package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Common;
import manager.AppManager;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.stream.Stream.generate;

public class BaseTest {

    protected static AppManager appManager;


    @BeforeEach
    public void before() throws IOException {
        if (appManager == null) {
            Properties properties = new Properties();
            properties.load(new FileReader((System.getProperty("target", "local.properties"))));
            appManager = new AppManager();
            appManager.init(System.getProperty("browser", "chrome"), properties);
        }

    }

    public static List<GroupData> groupsProvider() throws IOException {
        List<GroupData> res = new ArrayList<>();
        for (String name : List.of("", "some name")) {
            for (String header : List.of("", "some header")) {
                for (String footer : List.of("", "some footer")) {
                    res.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
                }
            }
        }
        StringBuilder json = new StringBuilder();
        try (FileReader fr = new FileReader("tmp/groups.json");
             BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        List<GroupData> mapperList = mapper.readValue(json.toString(), new TypeReference<List<GroupData>>() {
        });
        res.addAll(mapperList);
/*        File json = new File("src/test/resources/tmp/contacts.json");
        if (json.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            List<GroupData> mapperList = mapper.readValue(new File("src/test/resources/tmp/groups.json"), new TypeReference<List<GroupData>>() {
            });

 */
        res.addAll(mapperList);
        return res;
    }


    public static Stream<GroupData> negativeGroupsProvider() {
        return Stream.generate(() -> new GroupData()
                .withName("some name'")
                .withHeader("some header")
                .withFooter("some footer")).limit(1);

    }

    public static List<ContactData> contactsProvider() throws IOException {
        Stream<ContactData> stream = generate(() -> new ContactData()
                .withFirstname(Common.randomString(10))
                .withLastname(Common.randomString(11))
                .withMiddlename(Common.randomString(12))
                .withNickname(Common.randomString(13))
                .withTitle(Common.randomString(14))
                .withCompany(Common.randomString(15))
                .withAddress(Common.randomString(16))
                .withHome(Common.randomString(17))
                .withMobile(Common.randomString(18))
                .withWork(Common.randomString(19))
                .withEmail(Common.randomString(20))
                .withFax(Common.randomString(21))
                .withEmail2(Common.randomString(22))
                .withEmail3(Common.randomString(23))
                .withHomepage(Common.randomString(24))
                .withBday("11")
                .withBmonth("January")
                .withByear("2000")
                .withAday("21")
                .withAmonth("May")
                .withAyear("1993")).limit(2);
        List <ContactData> res = new ArrayList<>(stream.toList());

        StringBuilder json = new StringBuilder();
        try (FileReader fr = new FileReader("tmp/contacts.json");
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        List<ContactData> mapperList = mapper.readValue(json.toString(),
                new TypeReference<List<ContactData>>() {
                });
    /*         String json = Files.readString(Paths.get("src/test/resources/tmp/contacts.json"));
          File json = new File("src/test/resources/tmp/contacts.json");
        if (json.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            List<ContactData> mapperList = mapper
                    .readValue(json, new TypeReference<List<ContactData>>() {
                    });
                    */
        res.addAll(mapperList);
        return res;
    }

    @AfterEach
    public void after() {
        appManager.getJdbcHelper().check();
        //  appManager.getDriver().quit();
    }

}

