package controller;

import model.Os;
import model.files.File;
import model.files.Image;
import model.files.Text;
import model.files.Video;
import view.ConsoleGetCommand;
import view.ShowToUser;

import java.util.regex.Matcher;

public class FileController {
    public static void createFile(Matcher fileMatcher) {
        if (fileMatcher.matches()) {
            String fileName = fileMatcher.group(1);
            String fileFormat = fileMatcher.group(2);
            long fileSize = Long.parseLong(fileMatcher.group(3));
            if (isThereFileWithName(fileName)) {
                ShowToUser.showError(3);
                return;
            }
            switch (fileFormat) {
                case "txt":
                    createTextFile(fileName, fileSize);
                    break;
                case "img":
                    createImageFile(fileName, fileSize);
                    break;
                case "mp4":
                    createVideoFile(fileName, fileSize);
                    break;
                default:
                    ShowToUser.showError(4);
                    break;
            }
        }
    }

    private static boolean isDriveHasNotEnoughSize(long newFileSize) {
        return Os.os.currentDrive.getFreeSpace() < newFileSize;
    }

    private static void createVideoFile(String fileName, long fileSize) {
        if (isDriveHasNotEnoughSize(fileSize)) {
            ShowToUser.showError(5);
            return;
        }
        Video newVideo;
        if (Os.os.currentFolder == null) {
            newVideo = new Video(fileName, fileSize, true);
        } else {
            newVideo = new Video(fileName, fileSize, false);
            Os.os.currentFolder.addToFiles(newVideo);
        }
        Os.os.currentDrive.addToFiles(newVideo);
        Os.os.currentDrive.decreaseFreeSpace(fileSize);
        String[] behavior = ConsoleGetCommand.completeCreateVideoFile();
        newVideo.setQuality(behavior[0]);
        newVideo.setVideosLength(behavior[1]);
        ShowToUser.showSuccessToUser(0);
    }

    private static void createImageFile(String fileName, long fileSize) {
        if (isDriveHasNotEnoughSize(fileSize)) {
            ShowToUser.showError(5);
            return;
        }
        Image newImage;
        if (Os.os.currentFolder == null) {
            newImage = new Image(fileName, fileSize, true);
        } else {
            newImage = new Image(fileName, fileSize, false);
            Os.os.currentFolder.addToFiles(newImage);
        }
        Os.os.currentDrive.addToFiles(newImage);
        Os.os.currentDrive.decreaseFreeSpace(fileSize);
        String[] behavior = ConsoleGetCommand.completeCreateImageFile();
        newImage.setResolution(behavior[0]);
        newImage.setExtension(behavior[1]);
        ShowToUser.showSuccessToUser(0);
    }

    private static void createTextFile(String fileName, long fileSize) {
        if (isDriveHasNotEnoughSize(fileSize)) {
            ShowToUser.showError(5);
            return;
        }
        Text newText;
        if (Os.os.currentFolder == null) {
            newText = new Text(fileName, fileSize, true);
        } else {
            newText = new Text(fileName, fileSize, false);
            Os.os.currentFolder.addToFiles(newText);
        }
        Os.os.currentDrive.addToFiles(newText);
        Os.os.currentDrive.decreaseFreeSpace(fileSize);

        newText.setText(ConsoleGetCommand.completeCreateTextFile());
        ShowToUser.showSuccessToUser(0);
    }

    public static void deleteFile(Matcher deleteFileMatcher) {
        if (deleteFileMatcher.matches()) {
            String fileName = deleteFileMatcher.group(1);
            if (isThereFileWithName(fileName)) {
                File fileForDelete = File.getFileWithName(fileName);
                assert fileForDelete != null;
                Os.os.currentDrive.addToFreeSpace(fileForDelete.getSize());
                if (Os.os.currentFolder == null) {
                    Os.os.currentDrive.removeFile(fileForDelete);
                    ShowToUser.showSuccessToUser(1);
                    return;
                }
                Os.os.currentFolder.removeFile(fileForDelete);
                Os.os.currentDrive.removeFile(fileForDelete);
                ShowToUser.showSuccessToUser(1);
                return;
            }
            ShowToUser.showError(0);
        }
    }

    private static boolean isThereFileWithName(String fileName) {
        return File.getFileWithName(fileName) != null;
    }


    public static void renameFile(Matcher renameFileMatcher) {
        if (renameFileMatcher.matches()) {
            String currentFileName = renameFileMatcher.group(1);
            if (isThereFileWithName(currentFileName)) {
                String newFileName = renameFileMatcher.group(2);
                if (!isThereFileWithName(newFileName)) {
                    File file = File.getFileWithName(currentFileName);
                    assert file != null;
                    file.setFileName(newFileName);
                    ShowToUser.showSuccessToUser(3);
                    return;
                }
                ShowToUser.showError(3);
                return;
            }
            ShowToUser.showError(0);
        }
    }

    public static void copyFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            if (!isThereFileWithName(fileName)) {
                ShowToUser.showError(0);
                return;
            }
        }
        Os.copiedFiles.clear();
        for (String fileName : fileNames) {
            Os.copiedFiles.add(new File(File.getFileWithName(fileName)));
        }
        ShowToUser.showSuccessToUser(5);
        Os.pasteNumber = 0;
    }

    public static void cutFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            if (!isThereFileWithName(fileName)) {
                ShowToUser.showError(0);
                return;
            }
        }
        Os.cutFiles.clear();
        for (String fileName : fileNames) {
            Os.cutFiles.add(File.getFileWithName(fileName));
        }
        ShowToUser.showSuccessToUser(8);
        Os.pasteNumber = 1;
    }

    public static void pasteCutFiles() {
        long allSize = 0;
        for (File cutFile : Os.cutFiles) {
            if (isThereFileWithName(cutFile.getFileName())) {
                ShowToUser.showError(3);
                return;
            }
            allSize += cutFile.getSize();
        }
        if (allSize > Os.os.currentDrive.getFreeSpace()) {
            ShowToUser.showError(5);
            return;
        }
        Os.cutFiles.get(0).getMotherDrive().addToFreeSpace(allSize);
        for (File cutFile : Os.cutFiles) {
            if (cutFile.getMotherFolder() != null)
                cutFile.getMotherFolder().removeFile(cutFile);
            cutFile.getMotherDrive().removeFile(cutFile);
            cutFile.setMotherFolder(Os.os.currentFolder);
            cutFile.setMotherDrive(Os.os.currentDrive);
            if (cutFile.getMotherFolder() == null)
                cutFile.setForDrive(true);
            else
                cutFile.setForDrive(false);
            cutFile.getMotherDrive().addToFiles(cutFile);
            if (cutFile.getMotherFolder() != null)
                cutFile.getMotherFolder().addToFiles(cutFile);
        }
        Os.os.currentDrive.decreaseFreeSpace(allSize);
        ShowToUser.showSuccessToUser(7);
        Os.pasteNumber = -1;
        Os.cutFiles.clear();
    }

    public static void pasteCopiedFiles() {
        long allSize = 0;
        for (File copiedFile : Os.copiedFiles) {
            if (isThereFileWithName(copiedFile.getFileName())) {
                ShowToUser.showError(3);
                return;
            }
            allSize += copiedFile.getSize();
        }
        if (allSize > Os.os.currentDrive.getFreeSpace()) {
            ShowToUser.showError(5);
            return;
        }
        for (File copiedFile : Os.copiedFiles) {
            copiedFile.setMotherFolder(Os.os.currentFolder);
            copiedFile.setMotherDrive(Os.os.currentDrive);
            if (copiedFile.getMotherFolder() == null)
                copiedFile.setForDrive(true);
            else {
                copiedFile.setForDrive(false);
                copiedFile.getMotherFolder().addToFiles(copiedFile);
            }
            copiedFile.getMotherDrive().addToFiles(copiedFile);
        }
        Os.os.currentDrive.decreaseFreeSpace(allSize);
        ShowToUser.showSuccessToUser(7);
        Os.pasteNumber = -1;
        Os.copiedFiles.clear();
    }

    public static void printFileState(Matcher matcher) {
        if (matcher.matches()) {
            String fileName = matcher.group(1);
            if (isThereFileWithName(fileName)) {
                File file = File.getFileWithName(fileName);
                assert file != null;
                if (file.getType().equals("img"))
                    ShowToUser.showString(((Image) file).toString());
                else if (file.getType().equals("mp4"))
                    ShowToUser.showString(((Video) file).toString());
                else
                    ShowToUser.showString(((Text) file).toString());
                return;
            }
            ShowToUser.showError(0);
        }
    }

    public static void writeText(Matcher writeTextMatcher) {
        if (writeTextMatcher.matches()) {
            String fileName = writeTextMatcher.group(1);
            if(isThereFileWithName(fileName)){
                File file = File.getFileWithName(fileName);
                assert file != null;
                if(file.getType().equals("txt")){
                    ((Text)file).setText(ConsoleGetCommand.getInputString());
                    return;
                }
                ShowToUser.showError(6);
                return;
            }
            ShowToUser.showError(0);
        }
    }
}
