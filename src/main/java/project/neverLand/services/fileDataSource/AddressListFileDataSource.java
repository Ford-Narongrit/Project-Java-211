package project.neverLand.services.fileDataSource;

import project.neverLand.models.*;

import java.io.*;

public class AddressListFileDataSource extends FileDataSource {
    private AddressList addressList;

    public AddressListFileDataSource(String fileDirectoryName, String fileName) throws IOException {
        super(fileDirectoryName,fileName);
    }

    private void readData() throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null){
            String[] data = line.split(",");
            Address address = new Address(data[0].trim(),data[1].trim(),data[2].trim(),data[3].trim());
            for(int i=4; i<data.length; i = i+2) {
                address.addPersonToRoom(new Person(data[i].trim(), data[i+1].trim()));
            }
            addressList.addAddress(address);
        }
        bufferedReader.close();
    }

    public AddressList getAddressList() throws IOException {
        addressList = new AddressList();
        readData();
        return addressList;
    }

    public void setAddressList(AddressList addressList) throws IOException {
        String filePath = getFilePath();
        File file = new File(filePath);
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (Address address: addressList.toList()) {
            String line =   address.getBuilding() + "," +
                            address.getFloor() + "," +
                            address.getRoomNumber() + "," +
                            address.getRoomType() +
                            address.getRoomerToString();
            bufferedWriter.append(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}

