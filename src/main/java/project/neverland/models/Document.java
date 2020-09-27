package project.neverland.models;

import javafx.scene.image.Image;

public class Document extends Mail{
    private String Degree;

    public Document(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, String degree) {
        super(sender, senderLocation, receiver, receiverLocation, size);
        Degree = degree;
    }
}
