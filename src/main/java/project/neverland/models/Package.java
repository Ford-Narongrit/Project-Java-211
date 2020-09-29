package project.neverland.models;

import javafx.scene.image.Image;

public class Package extends Mail{
    private String station;
    private String trackingNum;
    private double height;

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, String station, String trackingNum) {
        super(sender, senderLocation, receiver, receiverLocation, size);
        this.station = station;
        this.trackingNum = trackingNum;
    }

    @Override
    public String calSize() {
        double size =getWidth()*getLength()*height;
        if(size == 1960){
            setSize("A");
        }
        else if(size == 3360){
            setSize("2A");
        }
        else if(size == 3825){
            setSize("B");
        }
        else if(size == 7650){
            setSize("2B");
        }
        else if(size == 6600){
            setSize("C");
        }
        else if(size == 10780){
            setSize("2C");
        }
        return getSize();
    }
}
