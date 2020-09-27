package project.neverland.models;

import java.util.ArrayList;

public class Address {
    private int building;
    private int floor;
    private String roomNumber;
    private String roomType;
    private ArrayList<Person> roomers;
    private InboxList inboxList;

    public Address(int building, int floor, String numberRoom, String typeRoom) {
        this.building = building;
        this.floor = floor;
        this.roomNumber = numberRoom;
        this.roomType = typeRoom;
    }
}
