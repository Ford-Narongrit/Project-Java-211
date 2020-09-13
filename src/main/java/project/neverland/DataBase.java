package project.neverland;

public class DataBase {
    public PersonList getPersonData(){
        PersonList people = new PersonList();
        Person a = new Person("Admin","Narongrit","Thammapalo","admin");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("worker1","A","Ant","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("worker2","B","Bot","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("worker3","C","Cat","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("worker4","D","Doom","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("worker5","E","Enter","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("resident1","Sasuke","Ushiwa","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("resident2","naruto","Uzumaki","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("resident3","Kirikaya","Kasuto","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Person("resident4","Moka","Aoba","resident");
        a.setPassword("1234");
        people.addAccount(a);


        return people;
    }
}
