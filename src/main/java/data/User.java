package data;

public class User {

    private String name;
    private String gender;
    private String email;
    private String status;
    private String id;

    public User() {
        super();
    }

    public User(String name, String gender, String email, String status) {
        this.gender = gender;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public User(String id, String name, String gender, String email, String status) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
