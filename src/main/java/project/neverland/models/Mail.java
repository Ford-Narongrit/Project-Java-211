package project.neverland.models;

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

    public Mail(Person sender, String senderLocation, Person receiver, String receiverLocation, String size) {
        this.sender = sender;
        this.senderLocation = senderLocation;
        this.receiver = receiver;
        this.receiverLocation = receiverLocation;
        this.size = size;
        this.received = false;
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

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public void setSize(String size) {
        this.size = size;
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



}
