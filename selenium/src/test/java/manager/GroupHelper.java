package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase {

    public GroupHelper(AppManager appManager) {
        this.appManager = appManager;
    }

    public void createGroup(int times, GroupData groupData) {
        openGroupsPage();
        for (int i = 1; i <= times; i++) {
            clickElement(By.name("new"));
            fillDataFields(groupData);
            submitAndReturn(By.name("submit"));
        }
    }


    public void deleteGroups() {
        openGroupsPage();
        if (getSelectorCount() == 0) {
            createGroup(3, new GroupData());
            deleteGroups();
        } else {
            clickAllElements(getSelectorList());
            submitAndReturn(By.name("delete"));
        }
    }

    public void deleteGroups(int num) {
        openGroupsPage();
        if (getSelectorCount() == 0) {
            createGroup(num + 1, new GroupData());
            deleteGroups(num);
        } else {
            getSelectorList().get(num - 1).click();
            clickElement(By.name("delete"));
        }
    }


    public void modifyGroup(GroupData groupData) {
        openGroupsPage();
        if (getSelectorCount() == 0) {
            createGroup(1, new GroupData());
            modifyGroup(groupData);
        } else {
            getSelectorList().get(0).click();
            clickElement(By.name("edit"));
            clearGroupFields();
            fillDataFields(groupData);
            submitAndReturn(By.name("update"));
        }
    }


    private void openGroupsPage() {
        if (!isElementPresent(By.name("new"))) {
            clickElement(By.linkText("groups"));
        }
    }

    private void clearGroupFields() {
        clickElement(By.name("group_name")).clear();
        clickElement(By.name("group_header")).clear();
        clickElement(By.name("group_footer")).clear();
    }

    private void fillDataFields(GroupData groupData) {
        setField(By.name("group_name"), groupData.getName());
        setField(By.name("group_header"), groupData.getHeader());
        setField(By.name("group_footer"), groupData.getFooter());
    }

    private void submitAndReturn(By by) {
        clickElement(by);
        clickElement(By.linkText("group page"));
    }

}
