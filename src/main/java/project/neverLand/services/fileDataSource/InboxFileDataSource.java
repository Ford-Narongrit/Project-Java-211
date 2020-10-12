package project.neverLand.services.fileDataSource;

import project.neverLand.models.*;
import project.neverLand.models.Package;

import java.io.*;

public class InboxFileDataSource extends FileDataSource {
    private InboxList inboxList;

    public InboxFileDataSource(String fileDirectoryName, String fileName) throws IOException {
        super(fileDirectoryName, fileName);
    }

    private void readData() throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            Mail mail;
            if (data.length == 11) {
                mail = new Package(new Person(data[0].trim(), data[1].trim()),
                        data[2].trim(),
                        new Person(data[3].trim(), data[4].trim()),
                        data[5].trim(),
                        data[6].trim(), Boolean.parseBoolean(data[7]),
                        data[8].trim(), data[9].trim(),
                        data[10].trim());
            } else if (data.length == 10) {
                mail = new Document(new Person(data[0].trim(), data[1].trim()),
                        data[2].trim(),
                        new Person(data[3].trim(), data[4].trim()),
                        data[5].trim(),
                        data[6].trim(), Boolean.parseBoolean(data[7]), data[8].trim(),
                        data[9].trim());
            } else {
                mail = new Mail(new Person(data[0].trim(), data[1].trim()),
                        data[2].trim(),
                        new Person(data[3].trim(), data[4].trim()),
                        data[5].trim(),
                        data[6].trim(), Boolean.parseBoolean(data[7].trim()),
                        data[8].trim());
            }
            inboxList.addInbox(mail);
        }
        bufferedReader.close();
    }

    public InboxList getInboxList() throws IOException {
        inboxList = new InboxList();
        readData();
        return inboxList;
    }

    public void setInboxList(InboxList inboxList) throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (Mail mail : inboxList.toList()) {
            String line = mail.getStructure();
            bufferedWriter.append(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}