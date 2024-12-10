
package common;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class used for Logging, mainly for debugging
public final class LoggingManager {

    private static LoggingManager instance; // Singleton instance
    private final Logger LOGGER;
    private Level level;

    // Private constructor to prevent direct instantiation
    public LoggingManager() {
        this.LOGGER = Logger.getLogger(LoggingManager.class.getName()); // Get a logger specific to the common.LoggingManager class
        this.level = Level.ALL;
        LOGGER.setLevel(level);

        // Ensure that only one ConsoleHandler is added
        if (LOGGER.getHandlers().length == 0) {  // Check if no handlers are present
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(level);
            LOGGER.addHandler(handler); // Add the handler
        }

        // Disable the root logger's console handler to avoid double logging
        Logger rootLogger = Logger.getLogger("");  // Get the root logger
        for (var handler : rootLogger.getHandlers()) { // Get handlers of the root logger
            rootLogger.removeHandler(handler);  // Remove handlers from root logger
        }
    }

    // Public method to access the Singleton instance
    public static LoggingManager getInstance() {
        if (instance == null) {
            instance = new LoggingManager(); // Create instance if not already created
        }
        return instance;
    }

    public void enableLogging() {
        LOGGER.setLevel(Level.ALL);
        for (var handler : LOGGER.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
    }

    public void disableLogging() {
        LOGGER.setLevel(Level.OFF);
        for (var handler : LOGGER.getHandlers()) {
            handler.setLevel(Level.OFF);
        }
    }

    public void setLoggingLevel(Level level) {
        this.level = level;
        LOGGER.setLevel(level);
        for (var handler : LOGGER.getHandlers()) {
            handler.setLevel(level);
        }
    }

    public static void disableAllLoggers() {
        Logger rootLogger = Logger.getLogger(""); // Get the root logger
        for (var handler : rootLogger.getHandlers()) { // Get handlers of the root logger
            handler.setLevel(Level.OFF);
        }
        rootLogger.setLevel(Level.OFF); // Optionally disable the root logger itself
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public Level getLevel() {
        return level;
    }
}
