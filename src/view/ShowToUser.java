package view;

import controller.FolderController;
import model.Drive;
import model.Folder;
import model.Os;
import model.files.File;

import java.util.ArrayList;

public class ShowToUser {

    public static void showError(int numberOfException) {
        switch (numberOfException) {
            case -1:
                System.out.println("invalid command");
                break;
            case 0:
                System.out.println("invalid name");
                break;
            case 1:
                System.out.println("insufficient hard size");
                break;
            case 2:
                System.out.println("folder exists with this name");
                break;
            case 3:
                System.out.println("file exists with this name");
                break;
            case 4:
                System.out.println("invalid format");
                break;
            case 5:
                System.out.println("insufficient drive size");
                break;
            case 6:
                System.out.println("this file is not a text file");
        }
    }

    public static void showSuccessToUser(int numberOfSuccess) {
        switch (numberOfSuccess) {
            case 0:
                System.out.println("file created");
                break;
            case 1:
                System.out.println("file deleted");
                break;
            case 2:
                System.out.println("folder deleted");
                break;
            case 3:
                System.out.println("file renamed");
                break;
            case 4:
                System.out.println("folder renamed");
                break;
            case 5:
                System.out.println("files copied");
                break;
            case 6:
                System.out.println("folders copied");
                break;
            case 7:
                System.out.println("paste completed");
                break;
            case 8:
                System.out.println("files cut completed");
                break;
            case 9:
                System.out.println("folders cut completed");
                break;
            case 10:
                System.out.println("folder created");
        }
    }

    public static void showString(String stringToShow) {
        System.out.println(stringToShow);
    }

    public static void showFolders(ArrayList<Folder> folders) {
        System.out.println("Folders:");
        for (Folder folder : folders) {
            System.out.println(folder);
        }
        /*for (Folder folder: folders) {
            if(folder.isForDrive())
                System.out.println(folder.getName() + " " + FolderController.calculateFolderSpace(folder, 0) + "MB");
            else if(!folder.isForDrive() && Os.os.currentFolder != null)
                System.out.println(folder.getName() + " " + FolderController.calculateFolderSpace(folder, 0) + "MB");
        }*/
    }

    public static void showFiles(ArrayList<File> files) {
        for (File file : files) {
            if (file.isForDrive())
            System.out.println(file.getFileName() + " " + file.getType() + " " + file.getSize() + "MB");
            else if(!file.isForDrive() && Os.os.currentFolder != null)
                System.out.println(file.getFileName() + " " + file.getType() + " " + file.getSize() + "MB");
        }
    }

    public static void showDrivesStatus(ArrayList<Drive> drives) {
        for (Drive drive : drives) {
            System.out.println(drive.getName() + " " + drive.getSize() + "MB " + (drive.getSize() - drive.getFreeSpace())
                    + "MB");
        }
    }

}
