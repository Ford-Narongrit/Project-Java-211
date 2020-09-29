package project.neverland.models;

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

    public boolean isPersonInAddress(Person person){
        for(Address address : addressList){
            if(address.isPersonInRoom(person)) {
                return true;
            }
        }
        return false;
    }

}
