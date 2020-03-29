package model.files;

import model.Drive;
import model.Folder;
import model.Os;

public class File {
    protected String fileName;
    protected long size;
    protected String type;
    protected Folder motherFolder;
    protected Drive motherDrive;
    protected boolean forDrive;

    public File(String fileName, long size, String type, boolean forDrive) {
        this.fileName = fileName;
        this.size = size;
        //TODO check for address
        this.type = type;
        this.motherFolder = Os.os.currentFolder;
        this.motherDrive = Os.os.currentDrive;
        this.forDrive = forDrive;
    }

    public File(File file) {
        fileName = file.fileName;
        size = file.size;
        type = file.type;
        motherFolder = file.motherFolder;
        motherDrive = file.motherDrive;
    }

    public void setForDrive(boolean forDrive) {
        this.forDrive = forDrive;
    }

    public boolean isForDrive() {
        return forDrive;
    }

    public Drive getMotherDrive() {
        return motherDrive;
    }

    public void setMotherDrive(Drive motherDrive) {
        this.motherDrive = motherDrive;
    }

    public void setMotherFolder(Folder motherFolder) {
        this.motherFolder = motherFolder;
    }

    public Folder getMotherFolder() {
        return motherFolder;
    }

    public long getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public static File getFileWithName(String fileName) {
        if (Os.os.currentFolder != null) {
            for (File file : Os.os.currentFolder.getFiles()) {
                if (file.getFileName().equalsIgnoreCase(fileName))
                    return file;
            }
            return null;
        }
        for (File file : Os.os.currentDrive.getFiles()) {
            if (file.getFileName().equalsIgnoreCase(fileName) && file.isForDrive())
                return file;
        }
        return null;
    }

}
