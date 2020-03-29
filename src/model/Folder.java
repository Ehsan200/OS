package model;

import model.files.File;

import java.util.ArrayList;
import java.util.Iterator;

public class Folder extends MotherDrive {
    private long numberOfViews;
    private Folder motherFolder;
    private String address;
    private Drive motherDrive;
    private boolean forDrive;

    public Folder(String name, String address, Folder motherFolder, boolean forDrive) {
        this.name = name;
        numberOfViews = 0;
        this.address = address + "/" + name;
        this.motherFolder = motherFolder;
        this.folders = new ArrayList<>();
        this.motherDrive = Os.os.currentDrive;
        this.forDrive = forDrive;
    }

    public Folder(Folder folder) {
        super();
        numberOfViews = 0;
        motherFolder = null;
        name = folder.name;
        folders = (ArrayList<Folder>) folder.getFolders().clone();
        files = (ArrayList<File>) folder.getFiles().clone();
        motherDrive = folder.getMotherDrive();
        forDrive = folder.forDrive;
    }

    public void setForDrive(boolean forDrive) {
        this.forDrive = forDrive;
    }

    public boolean isForDrive() {
        return forDrive;
    }

    @Override
    public ArrayList<Folder> getFolders() {
        return super.getFolders();
    }

    @Override
    public ArrayList<File> getFiles() {
        return super.getFiles();
    }

    public Drive getMotherDrive() {
        return motherDrive;
    }

    public void setMotherDrive(Drive motherDrive) {
        this.motherDrive = motherDrive;
    }

    public String getAddress() {
        return address;
    }

    public Folder getMotherFolder() {
        return motherFolder;
    }


    public long getNumberOfViews() {
        return numberOfViews;
    }

    public void addToViews() {
        numberOfViews++;
    }

    public static Folder getFolderWithName(String name) {
        Folder fatherFolder = Os.os.currentFolder;
        Drive folderDrive = Os.os.currentDrive;
        if (fatherFolder == null) {
            for (Folder folder : folderDrive.getFolders()) {
                if (folder.name.equalsIgnoreCase(name) && folder.isForDrive())
                    return folder;
            }
        } else {
            for (Folder folder : fatherFolder.getFolders()) {
                if (folder.name.equalsIgnoreCase(name))
                    return folder;
            }
        }
        return null;
    }

    public void setMotherFolder(Folder motherFolder) {
        this.motherFolder = motherFolder;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public void setSubsMotherDrive(boolean isCut) {
        for (File file : this.getFiles()) {
            if(isCut) {
                file.getMotherDrive().removeFile(file);
                //file.getMotherFolder().removeFile(file);
                //file.setMotherFolder(this);
            }
            //file.setMotherFolder(this);
            file.
                    setMotherDrive(Os.os.currentDrive);
            file.getMotherDrive().addToFiles(file);
            //file.getMotherFolder().addToFiles(file);
        }
        int size = this.folders.size();
        //for(int i = 0; i < size - 1; i++){
        for (Folder folder : this.folders) {
            if (isCut) {
                folder.getMotherDrive().removeFolder(folder);
                //folder.setMotherFolder(this);
            }
            //folder.getMotherFolder().removeFolder(folder);
            //folder.setMotherFolder(this);
            folder.setMotherDrive(Os.os.currentDrive);
            folder.getMotherDrive().addToFolders(folder);
            if (isCut)
                folder.setNumberOfViews(0);
            folder.setSubsMotherDrive(isCut);
        }
    }

    /*@Override
    public String toString() {
        return address + " " + numberOfViews;
    }
    */


    @Override
    public String toString() {
        return "Folder{" +
                "numberOfViews=" + numberOfViews +
                ", motherFolder=" + motherFolder +
                ", address='" + address + '\'' +
                ", motherDrive=" + motherDrive +
                ", forDrive=" + forDrive +
                ", name='" + name + '\'' +
                '}';
    }


}
