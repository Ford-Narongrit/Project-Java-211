package project.neverland.models;

import javafx.scene.image.Image;

public class Mail {
    Person personTo;
    Person personFrom;
    double size;
//    Image image;

    public Mail(Person personTo, Person personFrom, double size) {
        this.personTo = personTo;
        this.personFrom = personFrom;
        this.size = size;
//        this.image = image;
    }

    public Person getPersonTo() {
        return personTo;
    }

    public Person getPersonFrom() {
        return personFrom;
    }

    public double getSize() {
        return size;
    }

//    public Image getImage() {
//        return image;
//    }

    @Override
    public String toString() {
        return "Mail{" +
                "personTo=" + personTo +
                ", personFrom=" + personFrom +
                ", size=" + size +
                '}';
    }
}
