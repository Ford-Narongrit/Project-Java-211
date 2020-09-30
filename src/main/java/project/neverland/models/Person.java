package project.neverland.models;

import com.sun.org.apache.xpath.internal.objects.XString;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isThisPerson(Person person){
        return this.firstName.equals(person.getFirstName()) && this.lastName.equals(person.getLastName());
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
