package project.neverLand.models;

public class Person {
    private String firstName;
    private String lastName;
    private String imagePath;

    public Person(String firstName, String lastName) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
    }

    public Person(String firstName, String lastName, String imagePath) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.imagePath = imagePath;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getImagePath() {
        return imagePath;
    }

    public boolean isThisPerson(Person person){
        return this.firstName.equals(person.getFirstName()) && this.lastName.equals(person.getLastName());
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


}
