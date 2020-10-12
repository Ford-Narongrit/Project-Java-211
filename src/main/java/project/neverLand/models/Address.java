package project.neverLand.models;

import sun.awt.geom.AreaOp;

import java.util.ArrayList;

public class Address {
    private String building;
    private String floor;
    private String roomNumber;
    private String roomType;
    //have 2 type 1 bedroom , 2 bedroom

    private ArrayList<Person> roomers;

    public Address(String building, String floor, String roomNumber, String roomType) {
        this.building = building;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        roomers = new ArrayList<>();
    }

    public String getBuilding() {
        return building;
    }
    public String getFloor() {
        return floor;
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
        if(roomType.equals("oneBedroom") && roomers.size()==1){
            return true;
        }
        else if(roomType.equals("twoBedroom") && roomers.size()==2){
            return true;
        }
        return false;
    }

    public boolean addPersonToRoom(Person person) {
        if(!isRoomerMax()){
            roomers.add(person);
            return true;
        }
        return false;
    }

    public boolean isThisRoom(String building, String floor, String roomNumber, String roomType) {
        return this.building.equals(building) && this.floor.equals(floor) && this.roomNumber.equals(roomNumber) && this.roomType.equals(roomType);
    }
}
