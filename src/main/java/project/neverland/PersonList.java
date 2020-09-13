package project.neverland;

import java.util.ArrayList;

public class PersonList {
    private ArrayList<Person> people;
    private Person currentperson;

    public PersonList() {
        people = new ArrayList<>();
    }
    public void addAccount(Person acc) {people.add(acc);}
    //String
    public boolean login(String username, String password){
        for(Person person: people){
            if(person.validate(username, password)){
                currentperson = person;
                return true;
            }
        }
        return false;
    }

    public Person getCurrentPerson() {
        return currentperson;
    }

    public ArrayList<Person> toList() {return people;}

}

