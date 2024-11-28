package common;

import java.io.IOException;
import java.util.logging.*;

public class SaveToFile {
    private static final Logger gameLogger = LoggingManager.getInstance().getLogger();
    private static MemoryHandler memoryHandler;

    static {
        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());

            memoryHandler = new MemoryHandler(consoleHandler, 1000, Level.ALL);

            // Add handlers to the logger
            gameLogger.addHandler(memoryHandler);
            gameLogger.addHandler(consoleHandler);

            // Set logger level and disable parent handlers
            gameLogger.setLevel(Level.ALL);
            gameLogger.setUseParentHandlers(false);
        } catch (Exception e) {
            gameLogger.log(Level.SEVERE, "There was a game error during logger initialization!", e);
        }
    }

    // Public method to retrieve the logger instance
    public static Logger getLogger() {
        return gameLogger;
    }

    // Method to save logs to a file
    public static void saveLogsToFile() {
        FileHandler fileHandler = null;
        try {
            // Create a FileHandler to write logs to the specified file
            fileHandler = new FileHandler("game_record.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            // Temporarily add the file handler to the logger
            gameLogger.addHandler(fileHandler);

            memoryHandler.push();
        } catch (IOException e) {
            gameLogger.log(Level.SEVERE, "Failed to save logs to file: ", e);
        } finally {
            // Ensure the FileHandler is closed
        }
    }
}