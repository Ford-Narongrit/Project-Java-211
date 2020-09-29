package project.neverland.models;

import javafx.scene.image.Image;

public class Document extends Mail{
    private String degree;

    public Document(Person sender, String senderLocation, Person receiver, String receiverLocation, String size, String degree) {
        super(sender, senderLocation, receiver, receiverLocation, size);
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
}
