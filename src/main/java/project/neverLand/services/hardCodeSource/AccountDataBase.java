package project.neverLand.services.hardCodeSource;

import project.neverLand.models.Account;
import project.neverLand.models.AccountList;
import project.neverLand.models.Person;

public class AccountDataBase {
    public AccountList getPersonData(){
        AccountList people = new AccountList();
        Account a = new Account("Admin",new Person("Narongrit","Thammapalo"),"admin");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker3",new Person("C", "Cat"),"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker1",new Person("A", "Ant") ,"worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker2",new Person("B", "Bot") ,"worker");
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

        a = new Account("resident5",new Person("Hunter","Monter"),"resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident6",new Person("Lee","sin"), "resident");
        a.setPassword("1234");
        people.addAccount(a);

        return people;
    }
}
