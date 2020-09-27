package project.neverland.models;

import javafx.scene.image.Image;

public class Package extends Mail{
    private String station;
    private String trackingNum;

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, String station, String trackingNum) {
        super(sender, senderLocation, receiver, receiverLocation, size);
        this.station = station;
        this.trackingNum = trackingNum;
    }
}
