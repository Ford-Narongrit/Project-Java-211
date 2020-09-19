package project.neverland.models;

import javafx.scene.image.Image;

public class Document extends Mail{
    private String Degree;

    public Document(Person personTo, Person personFrom, double size, String degree) {
        super(personTo, personFrom, size);
        Degree = degree;
    }

    @Override
    public String toString() {
        return "Document{" +
                "Degree='" + Degree + '\'' +
                '}';
    }
}
