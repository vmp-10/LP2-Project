package common;

import characters.Player;
import core.PlayerManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//A class that's merely a utility function
public final class FileManager {
    private static final Logger LOGGER = DebuggingManager.getInstance().getLogger();

    // Private constructor to prevent instantiation
    private FileManager() {

    }

    static {
        DebuggingManager debuggingManager = DebuggingManager.getInstance();
        debuggingManager.disableLogging();
        debuggingManager.disableAllLoggers();
    }

    public static File createFile(String directoryPath, String fileName) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                LOGGER.warning("Failed to create directory: " + directoryPath);
            }
        }

        File file = new File(directory, fileName);

        try {
            if (file.createNewFile()) {
                LOGGER.fine("File created successfully: " + file.getAbsolutePath());
            } else {
                LOGGER.info("File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating file " + file.getAbsolutePath(), e);
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

    public static void saveGame(int numHumanPlayers, int numPlayers, String difficulty, List<Player> playableCharacters) {
        try {
            // Ensure the "data" directory exists
            Files.createDirectories(new File("data").toPath());

            // Retrieve or create the file
            File file = getFile("data", "games.txt");
            if (file == null) {
                file = createFile("data", "games.txt");
            }

            StringBuilder content = new StringBuilder();

            // Game settings
            content.append(numHumanPlayers).append(",")
                    .append(numPlayers).append(",")
                    .append(difficulty).append(";");

            // Playable characters
            content
                    .append(String.join(",", playableCharacters.stream().map(Player::getName).toArray(String[]::new)))
                    .append("\n");

            writeToFile(file, content.toString(), true);
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Failed to save game.");
            LOGGER.log(Level.SEVERE, "Failed to save game.", e);
        }
    }

    public static List<String> formatLines(List<String> games) {
        if (games == null || games.isEmpty()) {
            LOGGER.warning("Games file is empty or null.");
            return null;
        }

        List<String> formattedGames = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (String game : games) {
            // data[0] has numPlayers and diff, data[1] has names of human players
            String[] data = game.split(";");

            if (data.length < 2) {
                LOGGER.warning("Invalid game data format, skipping.");
                continue;
            }

            String[] gameInfo = data[0].split(",");
            int numHumans = Integer.parseInt(gameInfo[0]);
            int numPlayers = Integer.parseInt(gameInfo[1]);
            String difficulty = gameInfo[2];

            String[] names = data[1].split(",");

            // Start building the formatted string
            builder.setLength(0);  // Clear previous content from StringBuilder
            builder.append("-> Total Number of Playable Players: ").append(numHumans).append("\n");
            builder.append("-> Total Number of Players: ").append(numPlayers).append("\n");
            builder.append("-> Difficulty: ").append(difficulty).append("\n");

            builder.append("-> Players: ").append("\n");
            for (int j = 0; j < names.length; j++) {
                builder.append("\t-> Player ").append(j).append(": ").append(names[j]).append("\n");
            }

            // Add the formatted game to the result list
            formattedGames.add(builder.toString());
        }

        LOGGER.info("Successfully formatted game records.");
        return formattedGames;
    }

    public static void saveGameLogs(int numHumanPlayers, int numPlayers,
                                    String difficulty, List<Player> playableCharacters,
                                    PlayerManager playerManager, LoggingManager loggingManager) {
        try {
            // Ensure the "data" directory exists
            Files.createDirectories(new File("data").toPath());

            // Retrieve or create the file
            File file = getFile("data", "gameLogs.txt");
            if (file == null) {
                file = createFile("data", "gameLogs.txt");
            }

            StringBuilder content = new StringBuilder();

            // Game settings
            content.append(numHumanPlayers).append(",")
                    .append(numPlayers).append(",")
                    .append(difficulty).append("\n");

            // Playable characters
            content.append(String.join(",", playableCharacters.stream().map(Player::getName).toArray(String[]::new)))
                    .append("\n");

            // Non-playable characters
            content.append(String.join(",", playerManager.getPlayersForFile().stream().map(Player::getName).toArray(String[]::new)))
                    .append("\n");

            // Events of each player on every "turn"
            content.append(loggingManager.getGameLog()).append("\n");

            writeToFile(file, content.toString(), true);
            System.out.println("Game logs saved successfully.");
        } catch (Exception e) {
            System.out.println("Failed to save game logs.");
            LOGGER.log(Level.SEVERE, "Failed to save game.", e);
        }
    }


}
