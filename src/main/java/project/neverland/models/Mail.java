package project.neverland.models;

public class Mail {
    private Person sender;
    private String senderLocation;
    private Person receiver;
    private String receiverLocation;
    private String size;
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

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
