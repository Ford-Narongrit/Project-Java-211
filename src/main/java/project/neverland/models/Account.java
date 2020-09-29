package project.neverland.models;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
    private String username;
    private String password;
    private Person personData;
    private String role;
    private boolean ban;
    private int loginBanCount;
    private Address addressInCondo;

    public Account(String username, String password, Person person, String role) {
        this.username = username;
        this.password = password;
        this.personData = person;
        this.role = role;
        this.ban = false;
        loginBanCount = 0;
    }
    public Account(String username,Person person, String role){
        this.username = username;
        this.personData = person;
        this.role = role;
        this.ban = false;
        loginBanCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public Person getPersonData() {
        return personData;
    }

    public boolean isBan() {
        return ban;
    }

    public void setPassword(String password) {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
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


}
