package mantis.model;

public class DeveloperMailUserData {


    private String name;
    private String token;

    public DeveloperMailUserData(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public DeveloperMailUserData() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
