package Backend;

import Utils.MessageBroker;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static Utils.DriftyConstants.*;

/**
 * This class checks if a directory exists or not. if it doesn't, the directory is created.
 */
class CheckDirectory {
    private static final MessageBroker messageBroker = Drifty.messageBroker;
    /**
     * This constructor creates the directory if it does not exist.
     * @param dir Name of the folder where the user wants to download the file.
     * @throws IOException when creating the directory fails.
     */
    CheckDirectory(String dir) throws IOException {
        if (!(checkIfFolderExists(dir))) {
            Path directory = FileSystems.getDefault().getPath(dir);
            Files.createDirectory(directory);
            messageBroker.sendMessage(DIRECTORY_CREATED, LOGGER_INFO, "directory");
        } else {
            messageBroker.sendMessage("Directory is valid !", LOGGER_INFO, "directory");
        }
    }

    /**
     * This function checks if a folder exists or not.
     * @param folderName Name of the folder where the user wants to download the file.
     * @return true if the folder exists and false if the folder is missing.
     */
    private static boolean checkIfFolderExists(String folderName) {
        boolean found = false;
        try {
            File file = new File(folderName);
            if (file.exists() && file.isDirectory()) {
                found = true;
            }
        } catch (Exception e) {
            messageBroker.sendMessage(ERROR_WHILE_CHECKING_FOR_DIRECTORY, LOGGER_ERROR, "directory");
        }
        return found;
    }
}
