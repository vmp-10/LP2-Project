import characters.Player;
import common.AppConstants;
import common.FileManager;
import common.LoggingManager;
import common.SaveToFile;
import core.Events;
import core.GameInputHandler;
import core.PlayerManager;
import core.TurnHandler;
import tools.Item;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger DEBUG_LOGGER = LoggingManager.getInstance().getLogger();
    private static final SaveToFile SAVE_TO_FILE = new SaveToFile();

    private static final int MIN_PLAYERS = 50;
    private static final int MAX_PLAYERS = 150;

    private static final String[] DIFFICULTIES = {"Easy", "Normal", "Hard", "Joe Must Die", "Custom"};

    public static void main(String[] args) {
        setupLogging();
        SAVE_TO_FILE.addContent("App started.");

        Scanner scanner = new Scanner(System.in);
        GameInputHandler inputHandler = new GameInputHandler(scanner);
        PlayerManager playerManager = new PlayerManager();
        TurnHandler turnHandler = new TurnHandler();

        boolean running = true;

        while (running) {
            System.out.print(AppConstants.MENU);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    startGame(scanner, inputHandler, playerManager, turnHandler);
                    break;
                case "2":
                    loadGame();
                    break;
                case "3":
                    showCredits();
                    break;
                case "exit":
                    DEBUG_LOGGER.info("Exiting application.");
                    running = false;
                    break;
                default:
                    handleInvalidInput(input);
                    break;
            }
        }
    }

    private static void startGame(Scanner scanner, GameInputHandler inputHandler, PlayerManager playerManager, TurnHandler turnHandler) {
        SAVE_TO_FILE.addContent("Game started.");

        // Get player selections
        int numPlayers = inputHandler.promptForPlayers(scanner);
        String difficulty = inputHandler.promptForDifficulty(scanner);
        Player human = inputHandler.promptForCharacter(scanner, difficulty);

        // Display all selections to the user and confirm
        System.out.print(AppConstants.createHeader("You selected the following:"));
        System.out.println("-> Number of Players: " + numPlayers);
        System.out.println("-> Difficulty: " + difficulty);
        System.out.println("-> Chosen Character: " + human.getName());

        // Ask for confirmation
        boolean confirmation = inputHandler.getGameConfirmation(scanner);

        if (confirmation) {
            System.out.print(AppConstants.createBox("Game started, Godspeed!"));
        } else {
            System.out.println("Game setup canceled. Going back to main menu.");
            return;  // Exit method if not confirmed
        }

        // Initialize players
        playerManager.initializePlayers(numPlayers, human, difficulty);

        runGameLoop(playerManager, scanner, turnHandler);

        announceWinner(playerManager.getPlayers().get(0)); // Winner is at index 0
    }



    private static void setupLogging() {
        LoggingManager loggingManager = LoggingManager.getInstance();
        loggingManager.setLoggingLevel(Level.ALL);
        loggingManager.disableLogging();
    }

    private static void runGameLoop(PlayerManager playerManager, Scanner scanner, TurnHandler turnHandler) {
        boolean gameRunning = true;

        ArrayList<Player> players = playerManager.getPlayers();

        while (gameRunning) {
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                if (currentPlayer.getTag() == 0) {
                    turnHandler.handleHumanTurn(currentPlayer, players, scanner);
                } else {
                    turnHandler.handleNPCTurn(currentPlayer, players);
                }

                if (players.size() == 1) {
                    gameRunning = false;
                    break;
                }
            }
        }
    }

    private static void announceWinner(Player winner) {
        System.out.println(AppConstants.createBox("Winner is player " + winner.getTag() + "!"));
    }

    private static void loadGame() {
        File file = FileManager.createFile("data", "game-v1");
        // Implement game loading logic here
    }

    private static void showCredits() {
        System.out.print(AppConstants.CREDITS);
    }

    private static void handleInvalidInput(String input) {
        System.out.println("Invalid input. Please enter a valid option (1, 2, 3, or 'exit').");
        DEBUG_LOGGER.warning("Invalid input received: " + input);
    }
}





