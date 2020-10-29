package project.neverLand.services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.plaf.IconUIResource;
import java.io.File;

public class ImageSetter {
    public void setImage(ImageView imageView, String imagePath) {
        if (imagePath.contains("profileDefault.jpg")) {
            imageView.setImage(new Image("image/profileDefault.jpg", 150.00, 150.00, false, false));
        } else if (imagePath.contains("emptyInbox.png")) {
            imageView.setImage(new Image("image/emptyInbox.png", 150.00, 150.00, false, false));
        } else {
            String path = "images" + File.separator + imagePath;
            imageView.setImage(new Image(new File(path).toURI().toString(), 150.00, 150.00, false, false));
        }
    }
}
