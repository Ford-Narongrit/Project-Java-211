package project.neverLand.services.fileDataSource;

import java.io.File;
import java.io.IOException;

public abstract class FileDataSource {
    private String fileDirectoryName;
    private String fileName;

    public FileDataSource(String fileDirectoryName, String fileName) throws IOException {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() throws IOException {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdir();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("Cannot create" + filePath);
            }
        }
    }

    public String getFilePath() {
        return fileDirectoryName + File.separator + fileName;
    }


}
