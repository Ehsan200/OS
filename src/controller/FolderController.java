package controller;

import model.Folder;
import model.Hard;
import model.Os;
import model.files.File;
import view.ShowToUser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderController {

    public static void openFolder(Matcher folderMatcher) {
        if (folderMatcher.matches()) {
            String folderName = folderMatcher.group(1);
            if (isThereFolderWithName(folderName)) {
                Os.os.currentFolder = Folder.getFolderWithName(folderName);
                //TODO update address
                assert Os.os.currentFolder != null;
                Os.os.currentFolder.addToViews();

            } else
                ShowToUser.showError(0);
        }

    }

    private static boolean isThereFolderWithName(String folderName) {
        Folder folder = Folder.getFolderWithName(folderName);
        return folder != null;
    }

    public static void back() {
        //System.out.println("before  "+Os.os.currentFolder);
        if (Os.os.currentFolder != null) {
            if (Os.os.currentFolder.getMotherFolder() == null) {
                Os.os.currentFolder = null;
                Os.os.address = Os.os.currentDrive.toString();
                //System.out.println("after " + Os.os.currentFolder);
                return;
            }
            Os.os.currentFolder = Os.os.currentFolder.getMotherFolder();
            Os.os.address = Os.os.currentFolder.getAddress();
        }
        //System.out.println("after " + Os.os.currentFolder);
    }

    public static void createFolder(Matcher folderMatcher) {
        if (folderMatcher.matches()) {
            String newFolderName = folderMatcher.group(1);
            if (isThereFolderWithName(newFolderName)) {
                ShowToUser.showError(2);
                return;
            }
            if (Os.os.currentFolder == null) {
                newFolderInDrive(newFolderName);
            } else newFolderInFolder(newFolderName);
            ShowToUser.showSuccessToUser(10);
        }
    }

    private static void newFolderInDrive(String newFolderName) {
        Folder newFolder = new Folder(newFolderName, Os.os.getAddress(), null, true);
        newFolder.setMotherDrive(Os.os.currentDrive);
        Os.os.addToAllFolders(newFolder);
        Os.os.currentDrive.addToFolders(newFolder);
    }

    private static void newFolderInFolder(String newFolderName) {
        Folder newFolder = new Folder(newFolderName, Os.os.getAddress(), Os.os.currentFolder, false);
        newFolder.setMotherFolder(Os.os.currentFolder);
        newFolder.setMotherDrive(Os.os.currentDrive);
        Os.os.addToAllFolders(newFolder);
        Os.os.currentFolder.addToFolders(newFolder);
        Os.os.currentDrive.addToFolders(newFolder);
    }

    public static void deleteFolder(Matcher deleteFolderMatcher) {
        if (deleteFolderMatcher.matches()) {
            String folderName = deleteFolderMatcher.group(1);
            if (isThereFolderWithName(folderName)) {
                Folder folderForDelete = Folder.getFolderWithName(folderName);
                assert folderForDelete != null;
                Os.os.currentDrive.addToFreeSpace(calculateFolderSpace(folderForDelete, 0));
                if (Os.os.currentFolder == null) {
                    Os.os.currentDrive.removeFolder(folderForDelete);
                    ShowToUser.showSuccessToUser(2);
                    return;
                }
                Os.os.currentFolder.removeFolder(folderForDelete);
                Os.os.currentDrive.removeFolder(folderForDelete);
                ShowToUser.showSuccessToUser(2);
                return;
            }
            ShowToUser.showError(0);
        }
    }

    public static long calculateFolderSpace(Folder folderToCalculate, long size) {
        ArrayList<Folder> folders = folderToCalculate.getFolders();
        if (!folderToCalculate.getFiles().isEmpty())
            for (File file : folderToCalculate.getFiles()) {
                size += file.getSize();
            }
        for (Folder folder : folders) {
            size = calculateFolderSpace(folder, size);
        }
        return size;
    }

    public static void renameFolder(Matcher renameFolderMatcher) {
        if (renameFolderMatcher.matches()) {
            String currentFolderName = renameFolderMatcher.group(1);
            if (isThereFolderWithName(currentFolderName)) {
                String newFolderName = renameFolderMatcher.group(2);
                if (!isThereFolderWithName(newFolderName)) {
                    Folder folder = Folder.getFolderWithName(currentFolderName);
                    assert folder != null;
                    folder.setName(newFolderName);
                    ShowToUser.showSuccessToUser(4);
                    return;
                }
                ShowToUser.showError(2);
                return;
            }
            ShowToUser.showError(0);
        }
    }


    public static void cutFolders(String[] folderNames) {
        for (String folderName : folderNames) {
            if (!isThereFolderWithName(folderName)) {
                ShowToUser.showError(0);
                return;
            }
        }
        Os.cutFolders.clear();
        for (String folderName : folderNames) {
            Os.cutFolders.add(Folder.getFolderWithName(folderName));
        }
        ShowToUser.showSuccessToUser(9);
        Os.pasteNumber = 3;
    }

    public static void pasteCutFolders() {
        long allSize = 0;
        for (Folder cutFolder : Os.cutFolders) {
            if (isThereFolderWithName(cutFolder.getName())) {
                ShowToUser.showError(2);
                return;
            }
            allSize += calculateFolderSpace(cutFolder, 0);
        }
        if (allSize > Os.os.currentDrive.getFreeSpace()) {
            ShowToUser.showError(5);
            return;
        }
        Os.cutFolders.get(0).getMotherDrive().addToFreeSpace(allSize);
        for (Folder cutFolder : Os.cutFolders) {
            cutFolder.getMotherDrive().removeFolder(cutFolder);
            if (cutFolder.getMotherFolder() != null)
                cutFolder.getMotherFolder().removeFolder(cutFolder);
            cutFolder.setNumberOfViews(0);
            cutFolder.setMotherDrive(Os.os.currentDrive);
            cutFolder.setMotherFolder(Os.os.currentFolder);
            cutFolder.setSubsMotherDrive(true);
            if (cutFolder.getMotherFolder() == null)
                cutFolder.setForDrive(true);
            else {
                cutFolder.setForDrive(false);
                cutFolder.getMotherFolder().addToFolders(cutFolder);
            }
            cutFolder.getMotherDrive().addToFolders(cutFolder);
            System.out.println("after paste(cut) : " + cutFolder);
        }
        Os.os.currentDrive.decreaseFreeSpace(allSize);
        ShowToUser.showSuccessToUser(7);
        Os.pasteNumber = -1;
        Os.cutFolders.clear();
    }


    public static void copyFolders(String[] folderNames) {
        for (String folderName : folderNames) {
            if (!isThereFolderWithName(folderName)) {
                ShowToUser.showError(0);
                return;
            }
        }
        Os.copiedFolders.clear();
        for (String folderName : folderNames) {
            Os.copiedFolders.add(new Folder(Folder.getFolderWithName(folderName)));
        }
        ShowToUser.showSuccessToUser(6);
        Os.pasteNumber = 2;
    }

    public static void pasteCopiedFolders() {
        long allSize = 0;
        for (Folder copiedFolder : Os.copiedFolders) {
            if (isThereFolderWithName(copiedFolder.getName())) {
                ShowToUser.showError(2);
                return;
            }
            allSize += calculateFolderSpace(copiedFolder, 0);
        }
        if (allSize > Os.os.currentDrive.getFreeSpace()) {
            ShowToUser.showError(5);
            return;
        }
        for (Folder copiedFolder : Os.copiedFolders) {
            copiedFolder.setMotherFolder(Os.os.currentFolder);
            copiedFolder.setMotherDrive(Os.os.currentDrive);
            copiedFolder.setSubsMotherDrive(false);
            if (copiedFolder.getMotherFolder() == null)
                copiedFolder.setForDrive(true);
            else {
                copiedFolder.setForDrive(false);
                copiedFolder.getMotherFolder().addToFolders(copiedFolder);
            }
            copiedFolder.getMotherDrive().addToFolders(copiedFolder);
            Os.os.addToAllFolders(copiedFolder);
            System.out.println("after paste(Copy) : " +copiedFolder);
        }
        Os.os.currentDrive.decreaseFreeSpace(allSize);
        ShowToUser.showSuccessToUser(7);
        Os.pasteNumber = -1;
        Os.copiedFolders.clear();
    }
}
