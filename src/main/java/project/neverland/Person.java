package project.neverland;

import org.mindrot.jbcrypt.BCrypt;

public class Person {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    public Person(String username, String password, String firstName, String lastName, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    public Person(String username, String firstName, String lastName, String role){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
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

    public boolean validate(String username, String password) {
        return this.username.equals(username) && BCrypt.checkpw(password, this.password);
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
