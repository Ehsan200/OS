package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommands {
    INSTALL_OS("install OS (\\S+) (\\S+)"),
    HARD_INFO("(\\S+) (\\d+)"),
    SET_DRIVES("(\\S+) (\\d+)"),
    OPEN_FOLDER("open (\\S+)"),
    GO_TO_DRIVE("go to drive (\\S+)"),
    CREATE_Folder("create folder (\\S+)"),
    CREATE_FILE("create file (\\S+) (\\S+) (\\d+)"),
    DELETE_FILE("delete file (\\S+)"),
    DELETE_FOLDER("delete folder (\\S+)"),
    RENAME_FILE("rename file (\\S+) (\\S+)"),
    RENAME_FOLDER("rename folder (\\S+) (\\S+)"),
    PRINT_FILE_STATES("print file stats (\\S+)"),
    WRITE_TEXT("write text (\\S+)");

    private Pattern commandPattern;

    ConsoleCommands(String commandPattern) {
        this.commandPattern = Pattern.compile(commandPattern);
    }

    public Matcher getCommandMatcher(String inputCommand) {
        return commandPattern.matcher(inputCommand);
    }
}
