package model.files;

import controller.OsController;
import model.Os;

public class Text extends File {
    private String text;

    public Text(String fileName, long size, boolean forDrive) {
        super(fileName, size, "txt", forDrive);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return fileName + " " + type + '\n' +
                OsController.createAddress(Os.os.currentFolder, "", Os.os.currentDrive) + '\\' + fileName + '\n' +
                "Size: " + size + "MB" + '\n' +
                "Text: " + text;
    }
}
