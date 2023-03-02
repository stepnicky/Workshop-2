package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private Long id;
    private String userName;
    private String email;
    private String password;

    public User() {

    }
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = hashedPassword(password);
    }
    public User(Long id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashedPassword(password);
    }
    public String hashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
