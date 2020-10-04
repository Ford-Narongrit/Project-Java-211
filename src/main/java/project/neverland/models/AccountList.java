package project.neverland.models;

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public AccountList() {
        accounts = new ArrayList<>();
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
    public boolean isUsernameDuplicate(String username){
        for(Account account : accounts){
            if(account.validate(username))
                return true;
        }
        return false;
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


    public ArrayList<Account> toList() {return accounts;}
    public ArrayList<Account> toRoleList(String role){
        ArrayList<Account> roleList = new ArrayList<>();
        for(Account account: accounts){
            if(account.isRole(role)){
                roleList.add(account);
            }
        }
        return roleList;
    }
}

