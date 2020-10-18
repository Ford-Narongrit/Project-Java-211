package project.neverLand.models;

import org.jetbrains.annotations.NotNull;

public class Mail implements Comparable<Mail>{
    private Person sender;
    private String senderLocation;
    private Person receiver;
    private String receiverLocation;

    private String size;
    private double width;
    private double length;
    private boolean received;
    private String imagePath;

    private String date;
    private String workerName;

    public Mail(Person sender, String senderLocation, Person receiver, String receiverLocation, String imagePath, double width, double length, String date, String workerName) {
        this.sender = sender;
        this.senderLocation = senderLocation;
        this.receiver = receiver;
        this.receiverLocation = receiverLocation;
        this.received = false;
        this.imagePath = imagePath;
        this.width = width;
        this.length = length;
        this.date = date;
        this.workerName = workerName;
    }

    public Mail(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, boolean received, String imagePath, String date, String workerName) {
        this.sender = sender;
        this.senderLocation = senderLocation;
        this.receiver = receiver;
        this.receiverLocation = receiverLocation;
        this.size = size;
        this.received = received;
        this.imagePath = imagePath;
        this.date = date;
        this.workerName = workerName;
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

    public String getDate() {
        return date;
    }

    public String getWorkerName() {
        return workerName;
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
        else{
            setSize("Unknown");
        }
        return this.size;
    }

    public String getStructure() {
        return sender.getFirstName() + "," +
                sender.getLastName() + "," +
                senderLocation + "," +
                receiver.getFirstName() + "," +
                receiver.getLastName() + "," +
                receiverLocation + "," +
                size + "," +
                received + "," +
                imagePath + "," +
                date + "," +
                workerName;
    }

    @Override
    public int compareTo(@NotNull Mail o) {
        return -1*this.getDate().compareTo(o.getDate());
    }
}
