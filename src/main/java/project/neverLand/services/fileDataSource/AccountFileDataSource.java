package project.neverLand.services.fileDataSource;

import project.neverLand.models.Account;
import project.neverLand.models.AccountList;
import project.neverLand.models.Person;
import java.io.*;

public class AccountFileDataSource extends FileDataSource {
    private AccountList accountList;

    public AccountFileDataSource(String fileDirectoryName, String fileName) throws IOException {
        super(fileDirectoryName,fileName);
    }

    private void readData() throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            String[] data = line.split(",");
            //new object
            Account account =   new Account(data[0].trim(),
                                new Person(data[1].trim(), data[2].trim())
                                ,data[3].trim()
                                ,Boolean.parseBoolean(data[4])
                                ,Integer.parseInt(data[5]));
            account.setHashPassword(data[6]);
            accountList.addAccount(account);
            //
        }
        bufferedReader.close();
    }

    public AccountList getAccountList() throws IOException {
        accountList = new AccountList();
        readData();
        return accountList;
    }

    public void setAccountList(AccountList accountList) throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(Account account: accountList.toList()){
            String line =   account.getUsername() + "," +
                            account.getPersonData().getFirstName() + "," +
                            account.getPersonData().getLastName() + "," +
                            account.getRole() + "," +
                            account.isBan() + "," +
                            account.getLoginBanCount() + "," +
                            account.getPassword();
            bufferedWriter.append(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
