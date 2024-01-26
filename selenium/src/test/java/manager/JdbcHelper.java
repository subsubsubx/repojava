package manager;

import model.ContactData;
import model.GroupData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper extends HelperBase {

    public JdbcHelper(AppManager appManager) {
        super(appManager);
    }

    public List<GroupData> getGroupList() {
        List<GroupData> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection
                (
                        appManager.getProperties().getProperty("jdbcUrl"),
                        appManager.getProperties().getProperty("jdbcUser"),
                        appManager.getProperties().getProperty("jdbcPassword")
                ); Statement statement = connection.createStatement();
             ResultSet result = statement
                     .executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list")) {

            while (result.next()) {
                res.add(new GroupData()
                        .withId(result.getString("group_id"))
                        .withName(result.getString("group_name"))
                        .withHeader(result.getString("group_header"))
                        .withFooter(result.getString("group_footer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public List<ContactData> getContactList() {
        List<ContactData> res = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection
                (
                        appManager.getProperties().getProperty("jdbcUrl"),
                        appManager.getProperties().getProperty("jdbcUser"),
                        appManager.getProperties().getProperty("jdbcPassword")
                ); Statement statement = connection.createStatement();
             ResultSet result = statement
                     .executeQuery("SELECT id, firstname, middlename, lastname FROM addressbook")) {

            while (result.next()) {
                res.add(new ContactData()
                        .withId(result.getString("id"))
                        .withFirstname(result.getString("firstname"))
                        .withMiddlename(result.getString("middlename"))
                        .withLastname(result.getString("lastname")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public void check() {
        try (Connection connection = DriverManager.getConnection
                (
                        appManager.getProperties().getProperty("jdbcUrl"),
                        appManager.getProperties().getProperty("jdbcUser"),
                        appManager.getProperties().getProperty("jdbcPassword")
                ); Statement statement = connection.createStatement();
             ResultSet result = statement
                     .executeQuery("SELECT * FROM address_in_groups ag LEFT JOIN addressbook ab on ab.id = ag.id WHERE ab.id IS NULL")) {

            if (result.next()) {
                throw new IllegalStateException("db corrupt");
                //"DELETE FROM 'address_in_groups' ag LEFT JOIN addressbook on ab.id = ag.id WHERE ab.id IS NULL"
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

