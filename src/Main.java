import characters.Player;
import common.AppConstants;
import common.FileManager;
import common.SaveToFile;
import core.*;
import tools.Item;

import java.io.File;
import java.sql.SQLOutput;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final SaveToFile SAVE_TO_FILE = new SaveToFile();

    public static void main(String[] args) {
        SAVE_TO_FILE.addContent("App started.");

        Scanner scanner = new Scanner(System.in);
        GameInputHandler inputHandler = new GameInputHandler(scanner);
        PlayerManager playerManager = new PlayerManager();
        TurnHandler turnHandler = new TurnHandler();
        EventManager eventManager = new EventManager();

        boolean running = true;

        while (running) {
            System.out.print(AppConstants.MENU);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    startGame(scanner, inputHandler, playerManager, turnHandler, eventManager);
                    break;
                case "2":
                    loadGame(playerManager,turnHandler, eventManager);
                    break;
                case "3":
                    showCredits();
                    break;
                case "exit":
                    running = false;
                    showCredits();
                    scanner.close();
                    break;
                default:
                    handleInvalidInput();
                    break;
            }
        }
    }

    private static void handleInvalidInput() {
        System.out.println("Invalid input. Please enter a valid option (1, 2, 3, or 'exit').");
    }

    private static void showCredits() {
        System.out.print(AppConstants.CREDITS);
    }

    private static void startGame(Scanner scanner, GameInputHandler inputHandler, PlayerManager playerManager,
                                  TurnHandler turnHandler, EventManager eventManager) {
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

        if (!confirmation) {
            System.out.println("Game setup canceled. Going back to main menu.");
            return;  // Exit method if not confirmed
        }

        // Initialize players
        playerManager.initializePlayers(numPlayers, human, difficulty);

        runGameLoop(playerManager, scanner, turnHandler, eventManager);

        announceWinner(playerManager.getPlayers().get(0)); // Winner is at index 0
    }

    private static void runGameLoop(PlayerManager playerManager, Scanner scanner, TurnHandler turnHandler, EventManager eventManager) {
        boolean gameRunning = true;

        ArrayList<Player> players = playerManager.getPlayers();

        while (gameRunning) {
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                if (currentPlayer.getTag() == 0) {
                    turnHandler.handleHumanTurn(currentPlayer, players, scanner, eventManager);
                } else {
                    turnHandler.handleNPCTurn(currentPlayer, players, eventManager);
                }

                if (players.size() == 1) {
                    gameRunning = false;
                    break;
                }
            }
        }
    }

    private static void announceWinner(Player winner) {
        Scanner scanner = new Scanner(System.in);

        // Display the winner's information
        System.out.println(AppConstants.createBox("Winner is player " + winner.getTag() + "!", 25));
        System.out.println("Do you want to save the match to the file? Y/N");

        try {
            String choice = scanner.nextLine().trim().toUpperCase();
            if ("Y".equals(choice)) {
                System.out.println("Match saved successfully.");
            } else if ("N".equals(choice)) {
                System.out.println("Match not saved.");
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while processing your input: " + e.getMessage());
        }
    }

    private static void loadGame(PlayerManager playerManager, TurnHandler turnHandler, EventManager eventManager) {
        String directoryPath = "/data/";
        String fileName = "game-1.txt";

        File file = FileManager.createFile(directoryPath, fileName);

        List<String> lines = FileManager.readFile(file);

        if (lines.isEmpty()) {
            System.out.println("No data found in the file or file could not be read.");
            return;
        }

        System.out.println("Game Details:");
        System.out.printf("%-5s %-10s %-15s %-15s %-10s%n", "Index", "Name", "Number of People", "Difficulty", "Character");
        System.out.println("-------------------------------------------------------------------");

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(","); // Split the line by comma
            if (parts.length == 4) {
                String name = parts[0].trim();
                String numberOfPeople = parts[1].trim();
                String difficulty = parts[2].trim(); // Parse difficulty as a string
                String character = parts[3].trim();

                // Print the details with index
                System.out.printf("%-5d %-10s %-15s %-15s %-10s%n", i + 1, name, numberOfPeople, difficulty, character);
            } else {
                System.out.println("Invalid line format: " + lines.get(i));
            }
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice < 1 || choice > lines.size()) {
            System.out.print("Enter the number of your choice (1-" + lines.size() + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > lines.size()) {
                    System.out.println("Invalid choice. Please choose a number between 1 and " + lines.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }

        System.out.println("You selected:");
        String[] selectedParts = lines.get(choice - 1).split(",");
        String name = selectedParts[0].trim();
        int numPlayers = Integer.parseInt(selectedParts[1].trim());
        String difficulty = selectedParts[2].trim(); // Difficulty is a string
        int character = Integer.parseInt(selectedParts[3].trim());
        System.out.printf("Name: %s, Number of People: %d, Difficulty: %s, Character: %d%n",
                name, numPlayers, difficulty, character);

        playerManager.initializePlayers(numPlayers, "Lincoln", difficulty);
        runGameLoop(playerManager, scanner, turnHandler, eventManager);
        announceWinner(playerManager.getPlayers().get(0));
    }
}