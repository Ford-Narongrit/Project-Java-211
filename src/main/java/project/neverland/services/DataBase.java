package project.neverland.services;

import project.neverland.models.Account;
import project.neverland.models.AccountList;

public class DataBase {
    public AccountList getPersonData(){
        AccountList people = new AccountList();
        Account a = new Account("Admin","Narongrit","Thammapalo","admin");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker1","A","Ant","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker2","B","Bot","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker3","C","Cat","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker4","D","Doom","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("worker5","E","Enter","worker");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident1","Sasuke","Ushiwa","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident2","naruto","Uzumaki","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident3","Kirigaya","Kasuto","resident");
        a.setPassword("1234");
        people.addAccount(a);

        a = new Account("resident4","Moka","Aoba","resident");
        a.setPassword("1234");
        people.addAccount(a);

        return people;
    }
}
