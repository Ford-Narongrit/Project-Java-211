package project.neverland.models;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;

public class Address {
    private String building;
    private String floor;
    private String roomNumber;
    private String roomType;
    //have 2 type 1 bedroom , 2 bedroom

    private ArrayList<Person> roomers;
    private ArrayList<Mail> inboxList;

    public Address(String building, String floor, String numberRoom, String typeRoom) {
        this.building = building;
        this.floor = floor;
        this.roomNumber = numberRoom;
        this.roomType = typeRoom;
        roomers = new ArrayList<>();
        inboxList = new ArrayList<>();
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
    public boolean isPersonInRoom(Person person){
        for(Person roomer: roomers){
            if(roomer.isThisPerson(person)){
                return true;
            }
        }
        return false;
    }

    public void addInbox(Mail mail){
        inboxList.add(mail);
    }

    public ArrayList<Mail> getInboxList() {
        return inboxList;
    }
}
