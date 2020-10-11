package project.neverLand.services.fileDataSource;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class ImageDateSource {

    public String getPathForFileChooser(ActionEvent event) {
        return chooseFile((Node) event.getSource());
    }

    private String chooseFile(Node b) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image PNG JPG", "*.png", "*.jpg"));
        File file = chooser.showOpenDialog(b.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "." + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);

                return target.toUri().toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "image/profileDefault.jpg";
    }

}
