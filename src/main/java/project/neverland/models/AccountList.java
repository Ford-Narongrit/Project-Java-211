package project.neverland.models;

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public AccountList() {
        accounts = new ArrayList<>();
    }
    public void addAccount(Account acc) {
        accounts.add(acc);}

    public boolean login(String username, String password){
        for(Account account : accounts){
            if(account.validate(username, password)){
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameDuplicate(String username){
        for(Account account : accounts){
            if(account.validate(username))
                return true;
        }
        return false;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public ArrayList<Account> toList() {return accounts;}

}

