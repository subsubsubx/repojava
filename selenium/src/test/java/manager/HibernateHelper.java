package manager;

import manager.hbm.ContactDto;
import manager.hbm.GroupDto;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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
        return new ContactData().withId(
                        String.valueOf(e.getId()))
                .withFirstname(e.getFirstname())
                .withMiddlename(e.getMiddlename())
                .withLastname(e.getLastname())
                .withHome(e.getHome())
                .withMobile(e.getMobile())
                .withWork(e.getWork())
                .withFax(e.getFax());
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
        return new ContactDto(Integer.parseInt(id),
                contact.getFirstname(), contact.getMiddlename(), contact.getLastname(),
                contact.getHome(), contact.getMobile(), contact.getWork(), contact.getFax(), contact.getAddress(),
                contact.getEmail(), contact.getEmail2(), contact.getEmail3());
    }

    private static List<GroupData> convertGroupList(List<GroupDto> dto) {
        return dto.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static List<ContactData> convertContactList(List<ContactDto> dto) {
        return dto.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }


    public List<GroupData> getGroupList() {
        return convertGroupList(sessionFactory
                .fromSession(session -> session
                        .createQuery("from GroupDto", GroupDto.class).list()));
    }


    public long getGroupCount() {
        return sessionFactory
                .fromSession(session -> session
                        .createQuery("select count (*) from GroupDto", Long.class).getSingleResult());
    }


    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }


    public long getContactCount() {
        return sessionFactory.fromSession(session -> session
                .createQuery("select count (*) from ContactDto", Long.class).getSingleResult());
    }

    public List<ContactData> getContactList() {
        return convertContactList(sessionFactory.fromSession(session -> session
                .createQuery("from ContactDto", ContactDto.class).list()));
    }

    public List<ContactData> getContactsInGroup(GroupData group) {
        return (sessionFactory.fromSession(session -> convertContactList(session
                .get(GroupDto.class, group.getId()).getContacts())));
    }

}
