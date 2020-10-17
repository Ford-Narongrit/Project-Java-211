package project.neverLand.models;

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public AccountList() {
        accounts = new ArrayList<>();
    }

    public boolean isUsernameDuplicate(String username) {
        for (Account account : accounts) {
            if (account.validate(username))
                return true;
        }
        return false;
    }
    public boolean isPersonDuplicate(Person person){
        for (Account account : accounts){
            if (account.getPersonData().isThisPerson(person)){
                return true;
            }
        }
        return false;
    }
    public void addAccount(Account acc) {
        accounts.add(acc);
    }
    public void register(String role , String firstname, String lastname, String username, String password, String confirmPassword, String imagePath, AddressList addressList) throws IllegalAccessException {
        String date = "0000/00/00--00:00:00";
        Person person = new Person(firstname, lastname);
        if (firstname.equals("") || lastname.equals("")){
            throw new IllegalAccessException("Please input firstname and lastname.");
        }
        if (isPersonDuplicate(person)){
            throw new IllegalAccessException("This person is already register.");
        }
        if(username.equals("") || password.equals("")){
            throw new IllegalAccessException("Please input username and password.");
        }
        if(isUsernameDuplicate(username)){
            throw new IllegalAccessException("This username is already in use.");
        }
        if(!password.equals(confirmPassword)){
            throw new IllegalAccessException("Confirm password not match.");
        }
        if(role.equals("resident")){
            if(!addressList.linkToAddress(person)){
                throw new IllegalAccessException("Please register the address with the staff.");
            }
        }
        Account account = new Account(username, person, role, date);
        account.setPassword(password);
        account.setImagePath(imagePath);
        addAccount(account);
    }

    public boolean login(String username, String password) throws IllegalAccessException{
        currentAccount = null;
        if(username.equals("") || password.equals("")){
            throw new IllegalAccessException("Please input username and password.");
        }
        for (Account account : accounts){
            if(account.validate(username)){
                if(account.validate(username,password)){
                    if(account.isBan()){
                        account.banCountAddOne();
                        throw new IllegalAccessException("Your account has been banned.");
                    }
                    currentAccount = account;
                    return true;
                }
                throw new IllegalAccessException("Your account or password error.");
            }
        }
        if(currentAccount == null){
            throw new IllegalAccessException("Username not yet registered.");
        }
        return false;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
    public ArrayList<Account> toList() {
        accounts.sort(Account::compareTo);
        return accounts;
    }
    public ArrayList<Account> toRoleList(String role) {
        ArrayList<Account> roleList = new ArrayList<>();
        for (Account account : accounts) {
            if (account.isRole(role)) {
                roleList.add(account);
            }
        }
        roleList.sort(Account::compareTo);
        return roleList;
    }
}

