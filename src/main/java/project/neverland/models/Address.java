package project.neverland.models;

import java.util.ArrayList;

public class Address {
    private String building;
    private int floor;
    private String roomNumber;
    private String roomType;
    private ArrayList<Person> roomers;
    private InboxList inboxList;

    public Address(String building, int floor, String numberRoom, String typeRoom) {
        this.building = building;
        this.floor = floor;
        this.roomNumber = numberRoom;
        this.roomType = typeRoom;
        roomers = new ArrayList<>();
    }

    public void addPersonInRoom(Person person) {
        roomers.add(person);
    }

    public boolean isPersonInRoom(Person person) {
        for (Person roomer : roomers) {
            if (roomer.isThisPerson(person)) {
                return true;
            }
        }
        return false;
    }
}
