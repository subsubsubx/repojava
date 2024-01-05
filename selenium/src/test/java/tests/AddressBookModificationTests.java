package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class AddressBookModificationTests extends BaseTest {


    @Test
    void modifyGroupTest() {
        String s = "Modified!";
        appManager.getGroup().modifyGroup(new GroupData()
                .withName(s)
                .withHeader("Header333")
                .withFooter("Footer22"));
        Assertions.assertEquals(s, appManager.getDriver().findElement(By
                .xpath("//span[@class='group'][1]")).getText());
    }
}
