package project.neverLand.models;

import java.util.ArrayList;

public class AddressList {
    private ArrayList<Address> addressList;
    private Address currentAddress;

    public AddressList() {
        this.addressList = new ArrayList<>();
    }

    public void addAddress(Address address) {
        addressList.add(address);
    }

    public boolean linkToAddress(Person person) {
        currentAddress = null;
        for (Address address : addressList) {
            if (address.isPersonInRoom(person)) {
                currentAddress = address;
                return true;
            }
        }
        return false;
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public ArrayList<Address> toList() {
        return addressList;
    }

    public ArrayList<Address> toPersonList(String name) {
        ArrayList<Address> addresses = new ArrayList<>();
        for (Address address : addressList) {
            for (Person person : address.getRoomers()) {
                if ((person.getFirstName() + person.getLastName()).contains(name.toUpperCase())) {
                    addresses.add(address);
                }
            }
        }
        return addresses;
    }

    public String findRoomNumber(String name) {
        for (Address address : addressList) {
            for (Person person : address.getRoomers()) {
                if ((person.getFirstName() + person.getLastName()).contains(name.toUpperCase())) {
                    return address.getRoomNumber();
                }
            }
        }
        return "";
    }

    public boolean findAddress(String roomNumber) throws IllegalAccessException {
        currentAddress = null;
        for (Address address : addressList) {
            if (address.isThisRoom(roomNumber)) {
                currentAddress = address;
                return true;
            }
        }
        if (currentAddress == null) {
            throw new IllegalAccessException("Room not found.");
        }
        return false;
    }
}

