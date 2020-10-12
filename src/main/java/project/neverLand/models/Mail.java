package project.neverLand.models;

public class Mail {
    private Person sender;
    private String senderLocation;
    private Person receiver;
    private String receiverLocation;

    private String size;
    private double width;
    private double length;
    private boolean received;
    private String imagePath;

    public Mail(Person sender, String senderLocation, Person receiver, String receiverLocation, String imagePath, double width, double length) {
        this.sender = sender;
        this.senderLocation = senderLocation;
        this.receiver = receiver;
        this.receiverLocation = receiverLocation;
        this.received = false;
        this.imagePath = imagePath;
        this.width = width;
        this.length = length;
    }

    public Mail(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, boolean received, String imagePath) {
        this.sender = sender;
        this.senderLocation = senderLocation;
        this.receiver = receiver;
        this.receiverLocation = receiverLocation;
        this.size = size;
        this.received = received;
        this.imagePath = imagePath;
    }

    public Person getSender() {
        return sender;
    }
    public Person getReceiver() {
        return receiver;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getSize() {
        return size;
    }
    public String getSenderLocation() {
        return senderLocation;
    }
    public String getReceiverLocation() {
        return receiverLocation;
    }

    public double getWidth() {
        return width;
    }
    public double getLength() {
        return length;
    }


    public void setReceived(boolean received) {
        this.received = received;
    }
    public boolean isReceived() {
        return received;
    }


    public void setSize(String size) {
        this.size = size;
    }

    public void setWidthLength(double width,double length) {
        this.width = width;
        this.length = length;
    }

    public String calSize(){
        double size = width*length;
        if( size == 184.68){
            setSize("C6");
        }
        else if(size == 242){
            setSize("DL");
        }
        else if(size == 370.98){
            setSize("C5");
        }
        else if(size == 741.96){
            setSize("C4");
        }
        return this.size;
    }

    public String getStructure() {
        return  sender.getFirstName()+ "," +
                sender.getLastName()+ "," +
                senderLocation + "," +
                receiver.getFirstName() + "," +
                receiver.getLastName() + "," +
                receiverLocation + "," +
                size + "," +
                received + "," +
                imagePath;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "sender=" + sender +
                ", senderLocation='" + senderLocation + '\'' +
                ", receiver=" + receiver +
                ", receiverLocation='" + receiverLocation + '\'' +
                ", size='" + size + '\'' +
                ", width=" + width +
                ", length=" + length +
                ", received=" + received +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
