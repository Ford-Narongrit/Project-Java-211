package project.neverLand.models;

public class Package extends Mail {
    private String station;
    private String trackingNum;
    private double height;

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String station, String trackingNum, String imagePath, double width, double length, double height, String date, String workerName) {
        super(sender, senderLocation, receiver, receiverLocation, imagePath, width, length, date, workerName);
        this.height = height;
        this.station = station;
        this.trackingNum = trackingNum;
    }

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, boolean received, String station, String trackingNum, String imagePath, String date, String workerName, String receivedTime) {
        super(sender, senderLocation, receiver, receiverLocation, size, received, imagePath, date, workerName, receivedTime);
        this.station = station;
        this.trackingNum = trackingNum;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String calSize() {
        double size = getWidth() * getLength() * height;
        if (Math.abs(size - 1960) < 0.001) {
            setSize("A");
        } else if (Math.abs(size - 3360) < 0.001) {
            setSize("2A");
        } else if (Math.abs(size - 3825) < 0.001) {
            setSize("B");
        } else if (Math.abs(size - 7200) < 0.001) {
            setSize("2B");
        } else if (Math.abs(size - 6600) < 0.001) {
            setSize("C");
        } else if (Math.abs(size - 13200) < 0.001){
            setSize("2C");
        } else {
            setSize("Unknown");
        }
        return getSize();
    }

    @Override
    public String getStructure() {
        return getSender().getFirstName() + "," +
                getSender().getLastName() + "," +
                getSenderLocation() + "," +
                getReceiver().getFirstName() + "," +
                getReceiver().getLastName() + "," +
                getReceiverLocation() + "," +
                getSize() + "," +
                isReceived() + "," +
                station + "," +
                trackingNum + "," +
                getImagePath() + "," +
                getDate() + "," +
                getWorkerName() + "," +
                getReceivedTime();
    }
}
