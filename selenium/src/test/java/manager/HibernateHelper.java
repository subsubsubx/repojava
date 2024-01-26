package manager;

import manager.hbm.ContactDto;
import manager.hbm.GroupDto;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(AppManager appManager) {
        super(appManager);
        sessionFactory =
                new Configuration()
                        .addAnnotatedClass(ContactDto.class)
                        .addAnnotatedClass(GroupDto.class)
                        .setProperty(AvailableSettings.URL, appManager.getProperties().getProperty("jdbcUrl") + "?zeroDateTimeBehavior=convertToNull")
                        .setProperty(AvailableSettings.USER, appManager.getProperties().getProperty("jdbcUser"))
                        .setProperty(AvailableSettings.PASS, appManager.getProperties().getProperty("jdbcPassword"))
                        .buildSessionFactory();
    }





    private static GroupData convert(GroupDto e) {
        return new GroupData(String.valueOf(e.getId()), e.getName(), e.getHeader(), e.getFooter());
    }

    private static ContactData convert(ContactDto e) {
        return new ContactData().withId(String.valueOf(e.getId())).withFirstname(e.getFirstname()).withMiddlename(e.getMiddlename()).withLastname(e.getLastname());
    }

    private static GroupDto convert(GroupData group) {
        String id = group.getId();
        if (id.equals("")) {
            id = "0";
        }
        return new GroupDto(Integer.parseInt(id), group.getName(), group.getHeader(), group.getFooter());
    }

    private static ContactDto convert(ContactData contact) {
        String id = contact.getId();
        if (id.equals("")) {
            id = "0";
        }
        return new ContactDto(Integer.parseInt(id), contact.getFirstname(), contact.getMiddlename(), contact.getLastname());
    }

    private static List<GroupData> convertGroupList(List<GroupDto> dto) {
        List<GroupData> res = new ArrayList<>();
        for (GroupDto e : dto) {
            res.add(convert(e));
        }
        return res;
    }

    private static List<ContactData> convertContactList(List<ContactDto> dto) {
        List<ContactData> res = new ArrayList<>();
        for (ContactDto e : dto) {
            res.add(convert(e));
        }
        return res;
    }



    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupDto", GroupDto.class).list();
        }));
    }


    public long getGroupCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupDto", Long.class).getSingleResult();
        });
    }


    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }


    public long getContactCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactDto", Long.class).getSingleResult();
        });
    }

    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactDto", ContactDto.class).list();
        }));
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
       return (sessionFactory.fromSession(session -> {
         return convertContactList(session.get(GroupDto.class, group.getId()).getContacts());
        }));
    }

}
