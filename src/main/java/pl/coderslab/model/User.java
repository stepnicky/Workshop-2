package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private Long id;
    private String email;
    private String username;
    private String password;

    public User (String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = hashPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        String hashed = hashPassword(password);
        this.password = hashed;
    }
    public String hashPassword(String password){
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
