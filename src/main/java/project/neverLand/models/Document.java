package project.neverLand.models;

public class Document extends Mail{
    private String degree;

    public Document(Person sender, String senderLocation, Person receiver, String receiverLocation, String degree, String imagePath, double width, double length, String date, String username) {
        super(sender, senderLocation, receiver, receiverLocation, imagePath, width, length, date, username);
        this.degree = degree;
    }

    public Document(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, boolean received, String degree, String imagePath, String date, String username) {
        super(sender, senderLocation, receiver, receiverLocation, size, received, imagePath, date, username);
        this.degree = degree;
    }

    @Override
    public String calSize() {
        double size = getLength()*getWidth();
        if(size == 623.7){
            setSize("A4");
        }
        else if(size == 310.8){
            setSize("A5");
        }
        else if(size == 38.48){
            setSize("A8");
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
                getReceiver() + "," +
                degree+ "," +
                getImagePath() + "," +
                getDate() + "," +
                getWorkerName();
    }
}
