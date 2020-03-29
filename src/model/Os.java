package model;

import model.files.File;

import java.util.ArrayList;

public class Os {
    private String name;
    private String version;
    public String address;
    public Folder currentFolder;
    public Drive currentDrive;
    public static Os os;
    private ArrayList<Folder> allFolders = new ArrayList<>();
    public static ArrayList<File> copiedFiles = new ArrayList<>();//0
    public static ArrayList<File> cutFiles = new ArrayList<>();//1
    public static ArrayList<Folder> copiedFolders = new ArrayList<>();//2
    public static ArrayList<Folder> cutFolders = new ArrayList<>();//3
    public static int pasteNumber = -1;


    public Os(String name, String version) {
        this.name = name;
        this.version = version;
        this.currentFolder = null;
        os = this;
    }

    public void addToAllFolders(Folder newFolder) {
        allFolders.add(newFolder);
    }

    public ArrayList<Folder> getAllFolders() {
        return allFolders;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }

    public void setCurrentDrive(Drive currentDrive) {
        this.currentDrive = currentDrive;
    }

    public String getAddress() {
        return address;
    }

    public Drive getCurrentDrive() {
        return currentDrive;
    }

    public Folder getCurrentFolder() {
        return this.currentFolder;
    }

    @Override
    public String toString() {
        return "OS is " + name + " " + version;
    }


}
