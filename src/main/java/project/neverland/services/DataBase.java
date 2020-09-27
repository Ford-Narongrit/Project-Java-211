package project.neverland.services;

import project.neverland.models.Account;
import project.neverland.models.AccountList;
import project.neverland.models.Person;

public class DataBase {
    public AccountList getPersonData(){
        AccountList people = new AccountList();
        Account a = new Account("Admin",new Person("Narongrit","Thammapalo"),"admin");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker1",new Person("A", "Ant") ,"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker2",new Person("B", "Bot") ,"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker3",new Person("C", "Cat"),"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker4",new Person("D", "Doom"),"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker5",new Person("E","Enter"),"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident1",new Person("Sasuke","Ushiwa"),"resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident2",new Person("naruto","Uzumaki"),"resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident3",new Person("Kirigaya","Kasuto"),"resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident4",new Person("Moka","Aoba"),"resident");
        a.setPassword("1234");
        people.addAccount(a);

        return people;
    }
}
