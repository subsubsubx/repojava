package model;

import java.util.Objects;

public class GroupData {

    private String id;
    private String name;
    private String header;
    private String footer;

    public GroupData() {
        this.id = "";
        this.name = "";
        this.header = "";
        this.footer = "";
    }

    public GroupData(String id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }

    public GroupData withName(String name) {
        this.name = name;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public GroupData withId(String id) {
        this.id = id;
        return this;
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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GroupData [id - " + id + ", name - " + name + ", header - " + header + ", footer - " + footer + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return Objects.equals(id, groupData.id) && Objects.equals(name, groupData.name) && Objects.equals(header, groupData.header) && Objects.equals(footer, groupData.footer);
    }


}
