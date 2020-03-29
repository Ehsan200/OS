package controller;

import model.Drive;
import model.Folder;
import model.Hard;
import model.Os;
import view.ConsoleGetCommand;
import view.ShowToUser;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OsController {
    private static Os os;
    private static Hard hard;
    private static Matcher driveMatcher;
    public static Pattern drivesInfoPattern = Pattern.compile("[A-Z]");
    private static boolean isFirstDrive = true;
    private static Drive DriveForAddress;

    public static void installOs(Matcher installMatcher) {
        if (installMatcher.matches()) {
            String osName = installMatcher.group(1);
            String osVersion = installMatcher.group(2);
            os = new Os(osName, osVersion);
            ConsoleGetCommand.getHardInfo();
        }
    }

    public static void setHardInfo(Matcher hardInfoMatcher) {
        if (hardInfoMatcher.matches()) {
            long size = Long.parseLong(hardInfoMatcher.group(1));
            int numberOfDrives = Integer.parseInt(hardInfoMatcher.group(2));
            hard = new Hard(size, numberOfDrives);
            ConsoleGetCommand.getDrivesInfo();
        }

    }

    public static void setDrivesInfo(Matcher drivesInfoMatcher) {
        Drive drive;
        if (drivesInfoMatcher.matches()) {
            String driveName = drivesInfoMatcher.group(1);
            driveMatcher = drivesInfoPattern.matcher(driveName);
            if (isNameValid() && isNewDrive(driveName)) {
                long freeSpace = Long.parseLong(drivesInfoMatcher.group(2));
                if (isThereEnoughSpaceForNewDrive(freeSpace)) {
                    hard.decreaseNumberOfDrives();
                    hard.decreaseFreeSpace(freeSpace);
                    if (isFirstDrive) {
                        setOsDrive(driveName, freeSpace);
                    } else {
                        drive = new Drive(driveName, false, freeSpace);
                        hard.addDrive(drive);
                    }
                    if (hard.getNumberOfDrives() == 0)
                        ConsoleGetCommand.getAnotherCommands();
                    return;
                }
                ShowToUser.showError(1);
                return;
            }
            ShowToUser.showError(0);
        }
    }

    private static boolean isNewDrive(String driveName) {
        ArrayList<Drive> drives = hard.getDrives();
        for (Drive drive : drives) {
            if (drive.getName().equals(driveName))
                return false;
        }
        return true;
    }

    private static boolean isThereEnoughSpaceForNewDrive(long driveSpace) {
        return driveSpace <= hard.getFreeSpace();
    }

    private static void setOsDrive(String driveName, long freeSpace) {
        Drive drive;
        isFirstDrive = false;
        drive = new Drive(driveName, true, freeSpace);
        os.address = driveName + ":";
        hard.addDrive(drive);
        os.setCurrentDrive(drive);

    }

    private static boolean isNameValid() {
        return driveMatcher.matches();
    }


    public static void paste() {
        int pasteNumber = Os.pasteNumber;
        switch (pasteNumber) {
            case 0:
                FileController.pasteCopiedFiles();
                break;
            case 1:
                FileController.pasteCutFiles();
                break;
            case 2:
                FolderController.pasteCopiedFolders();
                break;
            case 3:
                FolderController.pasteCutFolders();
        }
    }

    public static String createAddress(Folder folder, String address, Drive motherDrive) {
        if (folder == null)
            return motherDrive.toString();
        address += createAddress(folder.getMotherFolder(), address, motherDrive);
        address += "\\" + folder.getName();
        return address;
    }

    public static void printFrequentFolders() {
        for (Folder folder : sortByViewNum(Os.os.getAllFolders())) {
            folder.setAddress(createAddress(folder, "", folder.getMotherDrive()));
        }
        int count = 0;
        for (Folder folder : sortByLexicographical(Os.os.getAllFolders())) {
            if (folder.getNumberOfViews() > 0)
                ShowToUser.showString(folder.toString());
            count++;
            if (count == 5)
                return;
        }
    }

    private static ArrayList<Folder> sortByLexicographical(ArrayList<Folder> folders) {
        Folder temp;
        for (int i = 0; i < folders.size(); i++) {
            for (int j = 0; j < folders.size() - 1; j++) {
                boolean numberOfViewIsEqual = folders.get(j).getNumberOfViews() ==
                        folders.get(j + 1).getNumberOfViews();
                if (folders.get(j).getAddress().compareTo(folders.get(j + 1).getAddress()) > 0 && numberOfViewIsEqual) {
                    temp = folders.get(j + 1);
                    folders.set(j + 1, folders.get(j));
                    folders.set(j, temp);
                }
            }
        }
        return folders;
    }

    private static ArrayList<Folder> sortByViewNum(ArrayList<Folder> folders) {
        Folder temp;
        for (int i = 0; i < folders.size(); i++) {
            for (int j = 0; j < folders.size() - 1; j++) {
                if (folders.get(j).getNumberOfViews() < folders.get(j + 1).getNumberOfViews()) {
                    temp = folders.get(j + 1);
                    folders.set(j + 1, folders.get(j));
                    folders.set(j, temp);
                }
            }
        }
        return folders;
    }


    public static void printOsInformation() {
        ShowToUser.showString(Os.os.toString());
    }
}