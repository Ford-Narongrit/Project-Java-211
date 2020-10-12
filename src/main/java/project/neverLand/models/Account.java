package project.neverLand.models;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
    private String username;
    private String password;
    private Person personData;
    private String role;
    private String imagePath;

    private String lastLogin;
    private boolean ban;
    private int loginBanCount;

    public Account(String username, Person person, String role){
        this.username = username;
        this.personData = person;
        this.role = role;
        this.ban = false;
        loginBanCount = 0;
    }

    public Account(String username, Person personData, String role, boolean ban, int loginBanCount, String lastLogin , String imagePath) {
        this.username = username;
        this.personData = personData;
        this.role = role;
        this.ban = ban;
        this.loginBanCount = loginBanCount;
        this.lastLogin = lastLogin;
        this.imagePath = imagePath;
    }

    public String getLastLogin() {
        return lastLogin;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Person getPersonData() {
        return personData;
    }
    public String getRole() {
        return role;
    }
    public int getLoginBanCount() {
        return loginBanCount;
    }
    public boolean isBan() {
        return ban;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public boolean isRole(String role) {
        return this.role.equals(role);
    }
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public void setHashPassword(String password){
        this.password = password;
    }
    public void setBan(boolean ban) {
        this.ban = ban;
    }
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personData=" + personData +
                ", role='" + role + '\'' +
                ", loginDate='" + lastLogin + '\'' +
                ", ban=" + ban +
                ", loginBanCount=" + loginBanCount +
                '}';
    }


}
