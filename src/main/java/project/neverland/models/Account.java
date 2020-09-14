package project.neverland.models;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private boolean ban;
    private int loginBanCount;

    public Account(String username, String password, String firstName, String lastName, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.ban = false;
        loginBanCount = 0;
    }
    public Account(String username, String firstName, String lastName, String role){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.ban = false;
        loginBanCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public boolean isBan() {
        return ban;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public void banCountAddOne(){
        loginBanCount++;
    }

    public boolean validate(String username, String password) {
        return this.username.equals(username) && BCrypt.checkpw(password, this.password);
    }

    public boolean validate(String username) {
        return this.username.equals(username);
    }

    public boolean isRole(String role) {
        return this.role.equals(role);
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
