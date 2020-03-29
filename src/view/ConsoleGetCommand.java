package view;

import controller.DriveController;
import controller.FileController;
import controller.FolderController;
import controller.OsController;
import model.files.File;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ConsoleGetCommand {
    private static Scanner consoleInputScanner = new Scanner(System.in);
    private static Matcher matcher;
    private static String inputCommand;

    public static void start() {
        while (!(inputCommand = consoleInputScanner.nextLine().trim()).equals("end")) {
            matcher = ConsoleCommands.INSTALL_OS.getCommandMatcher(inputCommand);
            if (matcher.matches()) {
                OsController.installOs(matcher);
            } else
                ShowToUser.showError(-1);
        }
        System.exit(0);
    }

    public static void getHardInfo() {
        while (!(inputCommand = consoleInputScanner.nextLine().trim()).equals("end")) {
            matcher = ConsoleCommands.HARD_INFO.getCommandMatcher(inputCommand);
            if (matcher.matches())
                OsController.setHardInfo(matcher);
            else
                ShowToUser.showError(-1);
        }
        System.exit(0);
    }

    public static void getDrivesInfo() {
        while (!(inputCommand = consoleInputScanner.nextLine().trim()).equals("end")) {
            matcher = ConsoleCommands.SET_DRIVES.getCommandMatcher(inputCommand);
            if (matcher.matches())
                OsController.setDrivesInfo(matcher);
            else
                ShowToUser.showError(-1);
        }
        System.exit(0);
    }

    public static void getAnotherCommands() {
        while (!(inputCommand = consoleInputScanner.nextLine().trim()).equals("end")) {
            if (ConsoleCommands.OPEN_FOLDER.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.OPEN_FOLDER.getCommandMatcher(inputCommand);
                FolderController.openFolder(matcher);
            } else if (ConsoleCommands.GO_TO_DRIVE.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.GO_TO_DRIVE.getCommandMatcher(inputCommand);
                DriveController.goToDrive(matcher);
            } else if (inputCommand.equals("back")) {
                FolderController.back();
            } else if (ConsoleCommands.CREATE_Folder.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.CREATE_Folder.getCommandMatcher(inputCommand);
                FolderController.createFolder(matcher);
            } else if (ConsoleCommands.CREATE_FILE.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.CREATE_FILE.getCommandMatcher(inputCommand);
                FileController.createFile(matcher);
            } else if (ConsoleCommands.DELETE_FILE.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.DELETE_FILE.getCommandMatcher(inputCommand);
                FileController.deleteFile(matcher);
            } else if (ConsoleCommands.DELETE_FOLDER.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.DELETE_FOLDER.getCommandMatcher(inputCommand);
                FolderController.deleteFolder(matcher);
            } else if (ConsoleCommands.RENAME_FILE.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.RENAME_FILE.getCommandMatcher(inputCommand);
                FileController.renameFile(matcher);
            } else if (ConsoleCommands.RENAME_FOLDER.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.RENAME_FOLDER.getCommandMatcher(inputCommand);
                FolderController.renameFolder(matcher);
            } else if (inputCommand.equals("status")) {
                DriveController.allStatus();
            } else if (inputCommand.equals("print drives status")) {
                DriveController.drivesStatus();
            } else if (inputCommand.startsWith("copy file ")) {
                FileController.copyFiles(inputCommand.substring(10).split(" "));
            } else if (inputCommand.startsWith("copy folder ")) {
                FolderController.copyFolders(inputCommand.substring(12).split(" "));
            } else if (inputCommand.equals("paste")) {
                OsController.paste();
            } else if (inputCommand.startsWith("cut file ")) {
                FileController.cutFiles(inputCommand.substring(9).split(" "));
            } else if (inputCommand.startsWith("cut folder ")) {
                FolderController.cutFolders(inputCommand.substring(11).split(" "));
            } else if (ConsoleCommands.PRINT_FILE_STATES.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.PRINT_FILE_STATES.getCommandMatcher(inputCommand);
                FileController.printFileState(matcher);
            } else if (ConsoleCommands.WRITE_TEXT.getCommandMatcher(inputCommand).matches()) {
                matcher = ConsoleCommands.WRITE_TEXT.getCommandMatcher(inputCommand);
                FileController.writeText(matcher);
            } else if (inputCommand.equals("print frequent folders")) {
                OsController.printFrequentFolders();
            } else if (inputCommand.equals("print OS information")) {
                OsController.printOsInformation();
            } else System.out.println("invalid command");
        }
        System.exit(0);
    }

    public static String completeCreateTextFile() {
        System.out.println("Text:");
        return consoleInputScanner.nextLine();
    }

    public static String[] completeCreateVideoFile() {
        String[] lastString = new String[2];
        System.out.println("Quality:");
        lastString[0] = consoleInputScanner.nextLine();
        System.out.println("Video Length:");
        lastString[1] = consoleInputScanner.nextLine();
        return lastString;
    }

    public static String[] completeCreateImageFile() {
        String[] lastString = new String[2];
        System.out.println("Resolution:");
        lastString[0] = consoleInputScanner.nextLine();
        System.out.println("Extension:");
        lastString[1] = consoleInputScanner.nextLine();
        return lastString;
    }

    public static String getInputString() {
        return consoleInputScanner.nextLine();
    }


}
