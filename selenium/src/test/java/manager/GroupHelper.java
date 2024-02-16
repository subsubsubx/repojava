package manager;

import io.qameta.allure.Step;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GroupHelper extends HelperBase {

    public GroupHelper(AppManager appManager) {
        super(appManager);
        //   this.appManager = appManager;
    }


    @Step(value = "Создание группы")
    public void createGroup(GroupData groupData) {
        openGroupsPage();
        clickElement(By.name("new"));
        fillDataFields(groupData);
        submitAndReturn(By.name("submit"));
    }



    @Step(value = "Удаление всех групп")
    public void deleteAllGroups() {
        openGroupsPage();
        clickAllElements(getOptionsList());
        submitAndReturn(By.name("delete"));
    }



    @Step(value = "Удаление группы")
    public void deleteGroup(GroupData groupData) {
        openGroupsPage();
        selectGroup(groupData);
        submitAndReturn(By.name("delete"));
    }


    @Step(value = "Модификация группы")
    public void modifyGroup(GroupData oldGroup, GroupData newGroup) {
        openGroupsPage();
        selectGroup(oldGroup);
        clickElement(By.name("edit"));
        clearGroupFields();
        fillDataFields(newGroup);
        submitAndReturn(By.name("update"));
    }




    public void openGroupsPage() {
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


    @Step(value = "Подтверждение и возврат")
    private void submitAndReturn(By by) {
        clickElement(by);
        clickElement(By.linkText("group page"));
    }

    public int getGroupCount() {
        openGroupsPage();
        return getOptionsList().size();
    }


    @Step(value = "Получение списка групп")
    public List<GroupData> getGroupList() {
        openGroupsPage();
        return getList(By
                .cssSelector("span.group"))
                .stream()
                .map(e -> {
                    String s = e.getText();
                    WebElement checkbox = e.findElement(By.name("selected[]"));
                    String id = checkbox.getAttribute("value");
                    return new GroupData().withId(id).withName(s);
                })
                .collect(Collectors.toList());
    }

    public void selectGroup(GroupData groupData) {
        clickElement(By.cssSelector(String.format("input[value='%s']", groupData.getId())));
    }
}
