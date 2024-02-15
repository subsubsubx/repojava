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

    public DeveloperMailUserData withName(String name) {
        return new DeveloperMailUserData(name, this.token);
    }

    public DeveloperMailUserData withToken(String token) {
        return new DeveloperMailUserData(this.name, token);
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
