package controller;

import model.Drive;
import model.Folder;
import model.Hard;
import model.Os;
import model.files.File;
import view.ShowToUser;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DriveController {


    public static void goToDrive(Matcher matcher) {
        if (matcher.matches()) {
            String driveName = matcher.group(1);
            if (isThereDriveWithName(driveName)) {
                Os.os.currentDrive = Drive.getDriveWithName(driveName);
                Os.os.currentFolder = null;
            } else
                ShowToUser.showError(0);
        }
    }

    private static boolean isThereDriveWithName(String driveName) {
        return Drive.getDriveWithName(driveName) != null;
    }

    public static void allStatus() {
        Folder currentFolder = Os.os.currentFolder;
        ShowToUser.showString(OsController.createAddress(currentFolder, "", Os.os.currentDrive));
        if (currentFolder != null) {
            ShowToUser.showFolders(sortFolders(currentFolder.getFolders()));
            sortFiles(currentFolder.getFiles());
            return;
        }
        Drive currentDrive = Os.os.currentDrive;
        ShowToUser.showFolders(sortFolders(currentDrive.getFolders()));
        sortFiles(currentDrive.getFiles());
    }

    private static void sortFiles(ArrayList<File> files) {
        ArrayList<File> images = new ArrayList<>();
        ArrayList<File> texts = new ArrayList<>();
        ArrayList<File> videos = new ArrayList<>();
        for (File file : files) {
            if (file.getType().equals("img"))
                images.add(file);
            else if (file.getType().equals("mp4"))
                videos.add(file);
            else
                texts.add(file);
        }
        ShowToUser.showString("Files:");
        ShowToUser.showFiles(sortFilesByName(images));
        ShowToUser.showFiles(sortFilesByName(texts));
        ShowToUser.showFiles(sortFilesByName(videos));
    }

    private static ArrayList<File> sortFilesByName(ArrayList<File> files) {
        File temp;
        for (int i = 0; i < files.size(); i++) {
            for (int j = 0; j < files.size() - 1; j++) {
                if (files.get(j).getFileName().compareTo(files.get(j + 1).getFileName()) > 0) {
                    temp = files.get(j);
                    files.set(j, files.get(j + 1));
                    files.set(j + 1, temp);
                }
            }
        }
        return files;
    }


    private static ArrayList<Folder> sortFolders(ArrayList<Folder> folders) {
        Folder temp;
        for (int i = 0; i < folders.size(); i++) {
            for (int j = 0; j < folders.size() - 1; j++) {
                if (folders.get(j).getName().compareTo(folders.get(j + 1).getName()) > 0) {
                    temp = folders.get(j);
                    folders.set(j, folders.get(j + 1));
                    folders.set(j + 1, temp);
                }
            }
        }
        return folders;
    }

    public static void drivesStatus() {
        ShowToUser.showDrivesStatus(Hard.hard.getDrives());
    }
}
