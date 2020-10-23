package project.neverLand.services.fileDataSource;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import sun.applet.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ImageDataSource {

    public String getPathForFileChooser(ActionEvent event, String type) {
        return chooseFile((Node) event.getSource(), type);
    }
    private String chooseFile(Node b, String type) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image PNG JPG", "*.png", "*.jpg"));
        File file = chooser.showOpenDialog(b.getScene().getWindow());
        if (file != null) {
            try {
                File destDir = new File("images");
                destDir.mkdirs();
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "." + fileSplit[fileSplit.length - 1];
                String target = "images" + File.separator + filename;
                File imagePath = new File(target);
                Files.copy(file.toPath(), imagePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return filename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("inbox") && file == null){
            return "image/emptyInbox.png";
        }
        else if(type.equals("person") && file == null){
            return "image/profileDefault.jpg";
        }
        return "";
    }
}
