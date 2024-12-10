package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//A class that's merely a utility function
public final class FileManager {
    private static final Logger LOGGER = LoggingManager.getInstance().getLogger();

    // Private constructor to prevent instantiation
    private FileManager() {
        LoggingManager loggingManager = LoggingManager.getInstance();
        loggingManager.setLoggingLevel(Level.ALL);
        loggingManager.disableLogging();
    }

    public static File createFile(String directoryPath, String fileName) {
        String filePathDirectory;

        if (directoryPath.isEmpty()) {
            filePathDirectory = directoryPath + "/" + fileName;
        } else {
            filePathDirectory = fileName;
        }

        File file = new File(filePathDirectory);
        try {
            if (file.createNewFile()) {
                LOGGER.fine("File created successfully: " + fileName);
            } else {
                LOGGER.info("File already exists: " + fileName);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating file " + fileName, e);
        }
        return file;
    }

    public static File getFile(String directoryPath, String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            LOGGER.warning("File name cannot be null or empty.");
            return null;
        }

        File[] files;
        files = FileManager.listFiles(directoryPath);
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (fileName.equals(file.getName()) && file.isFile() && file.length() > 0) {
                LOGGER.fine("File " + fileName + " was found in " + directoryPath + " and is not empty.");
                return file;
            } else if (fileName.equals(file.getName()) && file.length() == 0) {
                LOGGER.info("File " + fileName + " was found in " + directoryPath + " but is empty.");
                return null;
            }
        }

        LOGGER.info("No file named " + fileName + " was found in " + directoryPath + ".");
        return null;
    }


    public static void writeToFile(File file, String input, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(input);
            writer.newLine(); // Add newline after each entry for clarity
            LOGGER.fine("Content written to file successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to file", e);
        }
    }


    public static List<String> readFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            LOGGER.fine("Successfully read from file: " + file.getName());
            return lines;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: " + file.getName(), e);
            return Collections.emptyList();
        }
    }

    public static File[] listFiles(String directoryPath) {
        File path = new File(directoryPath);
        if (!path.exists() || !path.isDirectory()) {
            LOGGER.warning("Directory '" + directoryPath + "' does not exist.");
            return null;
        }

        File[] files = path.listFiles();
        if (files == null) {
            LOGGER.warning("There are no files in the directory: " + directoryPath);
            return null;
        }
        return files;
    }
}
