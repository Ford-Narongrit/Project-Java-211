package project.neverland.models;

public class Address {
    private int building;
    private int floor;
    private String numberRoom;
    private String typeRoom;
    private Person person;

    public Address(int building, int floor, String numberRoom, String typeRoom, Person person) {
        this.building = building;
        this.floor = floor;
        this.numberRoom = numberRoom;
        this.typeRoom = typeRoom;
        this.person = person;
    }
}
