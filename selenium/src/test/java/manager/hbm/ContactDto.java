package manager.hbm;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactDto {

    @Id
    private int id;
    private String firstname;

    private String middlename;

    private String lastname;
    private String home;
    private String mobile;
    private String work;
    private String fax;

    private String address;
    private String email;
    private String email2;
    private String email3;

    public ContactDto() {
    }

    public ContactDto(int id, String firstname, String middlename, String lastname,
                      String home, String mobile, String work, String fax, String address,
                      String email, String email2, String email3) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.home = home;
        this.mobile = mobile;
        this.work = work;
        this.fax = fax;
        this.address = address;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
    }

    public String getHome() {
        return home;
    }

    public String getMobile() {
        return mobile;
    }

    public String getWork() {
        return work;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

}
