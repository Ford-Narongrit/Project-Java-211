package project.neverland.models;

import javafx.scene.image.Image;

public class Package extends Mail{
    private String station;
    private String trackingNum;

    public Package(Person personTo, Person personFrom, double size, String station, String trackingNum) {
        super(personTo, personFrom, size);
        this.station = station;
        this.trackingNum = trackingNum;
    }

    @Override
    public String toString() {
        return "Package{" +
                "station='" + station + '\'' +
                ", trackingNum='" + trackingNum + '\'' +
                '}';
    }
}
