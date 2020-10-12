package project.neverLand.models;

import java.util.ArrayList;

public class AddressList {
    private ArrayList<Address> addressList;
    private Address currentAddress;

    public AddressList() {
        this.addressList = new ArrayList<>();
    }

    public void addAddress(Address address){
        addressList.add(address);
    }

    public boolean linkToAddress(Person person){
        for(Address address : addressList){
            if(address.isPersonInRoom(person)){
                currentAddress = address;
                return true;
            }
        }
        return false;
    }
    public Address getCurrentAddress() {
        return currentAddress;
    }

    public ArrayList<Address> toList(){
        return addressList;
    }

    public void findAddress(String building, String floor, String roomNumber, String roomType){
        for (Address address: addressList) {
            if (address.isThisRoom(building, floor, roomNumber, roomType)) {
                currentAddress = address;
                return;
            }
        }
        Address address1 = new Address(building, floor, roomNumber, roomType);
        addressList.add(address1);
        currentAddress = address1;
        }
}

