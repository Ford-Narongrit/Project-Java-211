package project.neverLand.models;

import sun.awt.geom.AreaOp;

import java.util.ArrayList;

public class Address {
    private String roomNumber;
    private String roomType;
    //have 2 type 1 bedroom , 2 bedroom

    private ArrayList<Person> roomers;

    public Address(String roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        roomers = new ArrayList<>();
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public String getRoomType() {
        return roomType;
    }
    public ArrayList<Person> getRoomers() {
        return roomers;
    }
    public boolean isPersonInRoom(Person person){
        for(Person roomer: roomers){
            if(roomer.isThisPerson(person)){
                return true;
            }
        }
        return false;
    }
    public String getRoomerToString() {
        String roomerString = "";
        for(Person person: roomers){
            roomerString = roomerString + "," + person.getFirstName() + "," + person.getLastName() + "," + person.getImagePath();
        }
        return roomerString;
    }

    public boolean isRoomerMax(){
        if(roomType.equals("one bedroom") && roomers.size()==1){
            return true;
        }
        else if(roomType.equals("two bedroom") && roomers.size()==2){
            return true;
        }
        return false;
    }

    public boolean addPersonToRoom(Person person) throws IllegalAccessException {
        if(!isRoomerMax()){
            roomers.add(person);
            return true;
        }
        throw new IllegalAccessException("This room is full.");
    }

    public boolean isThisRoom(String roomNumber) {
        return this.roomNumber.equals(roomNumber);
    }
}
