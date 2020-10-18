package project.neverLand.models;

public class Package extends Mail{
    private String station;
    private String trackingNum;
    private double height;

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String station, String trackingNum ,String imagePath, double width, double length, double height, String date, String workerName) {
        super(sender, senderLocation, receiver, receiverLocation, imagePath, width, length, date, workerName);
        this.height = height;
        this.station = station;
        this.trackingNum = trackingNum;
    }

    public Package(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, boolean received, String station, String trackingNum, String imagePath, String date, String workerName) {
        super(sender, senderLocation, receiver, receiverLocation, size, received, imagePath, date, workerName);
        this.station = station;
        this.trackingNum = trackingNum;
    }

    public void setHeight(double height) {
        this.height = height;
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
        else if(size == 13200){
            setSize("2C");
        }
        else{
            setSize("Unknown");
        }
        return getSize();
    }

    @Override
    public String getStructure() {
        return  getSender().getFirstName() + "," +
                getSender().getLastName() + "," +
                getSenderLocation() + "," +
                getReceiver().getFirstName() + "," +
                getReceiver().getLastName() + "," +
                getReceiverLocation() + "," +
                getSize() + "," +
                getReceiver() + "," +
                station + "," +
                trackingNum + "," +
                getImagePath() + "," +
                getDate()+ "," +
                getWorkerName();
    }
}
