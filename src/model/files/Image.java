package model.files;

import controller.OsController;
import model.Os;

public class Image extends File {
    private String resolution;
    private String extension;

    public Image(String fileName, long size, boolean forDrive) {
        super(fileName, size, "img", forDrive);
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return fileName + " " + type + '\n' +
                OsController.createAddress(Os.os.currentFolder, "", Os.os.currentDrive) + '\\' + fileName + '\n' +
                "Size: " + size + "MB" + '\n' +
                "Resolution: " + resolution + '\n' +
                "Extension: " + extension;
    }
}
