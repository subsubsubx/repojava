package manager.hbm;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "group_list")
public class GroupDto {


    @Id
    @Column(name = "group_id")
    private int id;
    @Column(name = "group_name")
    private String name;
    @Column(name = "group_header")
    private String header;
    @Column(name = "group_footer")
    private String footer;


    @ManyToMany
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<ContactDto> contacts;


     private Date deprecated= new Date();

    public GroupDto(int id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }

    public GroupDto() {
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

}
