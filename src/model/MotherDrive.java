package model;

import model.files.File;

import java.util.ArrayList;

public abstract class MotherDrive {
    protected String name;
    protected ArrayList<Folder> folders = new ArrayList<>();
    protected ArrayList<File> files = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public void addToFolders(Folder newFolder) {
        folders.add(newFolder);
    }

    public void addToFiles(File newFile) {
        files.add(newFile);
    }

    public void removeFolder(Folder folder) {
        folders.remove(folder);
    }

    public void removeFile(File file) {
        files.remove(file);
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setName(String name) {
        this.name = name;
    }
}

