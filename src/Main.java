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
        GameInputHandler inputHandler = new GameInputHandler();
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
                    if (!loadGame(playerManager, turnHandler, eventManager, inputHandler, scanner)) {
                        startGame(scanner, inputHandler, playerManager, turnHandler, eventManager);
                    }
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

        // Get player selections
        int numHumanPlayers = inputHandler.promptForHumans(scanner);
        playerManager.setNumHumanPlayers(numHumanPlayers);

        int numPlayers = inputHandler.promptForPlayers(scanner);
        String difficulty = inputHandler.promptForDifficulty(scanner);
        List<Player> playableCharacters = inputHandler.promptForCharacter(scanner, difficulty, numHumanPlayers);

        // Display all selections to the user and confirm
        System.out.println((AppConstants.displayGameOptions(numHumanPlayers, numPlayers, difficulty, playableCharacters)));

        // Ask for confirmation
        boolean confirmation = inputHandler.getGameConfirmation(scanner);

        if (!confirmation) {
            System.out.println("Game setup canceled. Going back to main menu.");
            return;
        }

        // Initialize players
        playerManager.initializePlayers(numPlayers, playableCharacters, difficulty);

        runGameLoop(playerManager, scanner, turnHandler, eventManager, inputHandler);

        announceWinner(playerManager.getPlayers().getFirst(), inputHandler, scanner); // Winner is at index 0
    }


    private static void runGameLoop(PlayerManager playerManager, Scanner scanner,
                                    TurnHandler turnHandler, EventManager eventManager, GameInputHandler inputHandler) {
        boolean gameRunning = true;

        ArrayList<Player> players = playerManager.getPlayers();
        List<Player> playersToRemove = new ArrayList<>();


        while (gameRunning) {
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                //DEBUG: System.out.println("[DEBUG] TAG: " + currentPlayer.getTag() + ", HP: " + currentPlayer.getHealth());

                // If the player is dead, mark them for removal
                if (!currentPlayer.isAlive()) {
                    playersToRemove.add(currentPlayer);
                    continue; // Skip iteration
                }

                if (currentPlayer.getTag() > 0 && currentPlayer.getTag() <= playerManager.getNumHumanPlayers()) {
                    turnHandler.handleHumanTurn(currentPlayer, players, scanner, eventManager, turnHandler, inputHandler, playerManager);
                } else {
                    turnHandler.handleNPCTurn(currentPlayer, players, eventManager, turnHandler, inputHandler, i, playerManager);
                }

                //Checks if human player is dead and displays a game over screen.
                if (currentPlayer.getTag() > 0 && currentPlayer.getTag() <= playerManager.getNumHumanPlayers() && !currentPlayer.isAlive()) {
                    String prompt = AppConstants.createBox(AppConstants.createSelection("You have died, do you want to see who wins [y/n]"), 35);
                    boolean choice = inputHandler.getYesNoInput(prompt, scanner);
                    if (!choice) {
                        break;
                    }
                }

                // Remove all dead players from the list
                players.removeAll(playersToRemove);

                if (players.size() == 1) {
                    gameRunning = false;
                    break;
                }
            }
        }
    }

    private static void announceWinner(Player winner, GameInputHandler inputHandler, Scanner scanner) {
        if (winner.getTag() == 0) {
            System.out.println(AppConstants.createBox("You have won, congrats!", 35));
        }
        System.out.println(AppConstants.createBox("Winner is player " + winner.getTag() + "!", 35));
        boolean save = inputHandler
                .getYesNoInput(AppConstants.createSelection("Do you want to save the match to the file [y/n]"), scanner);
        if (save) {
            System.out.println("Match saved successfully.");
        } else {
            System.out.println("Match not saved.");
        }
    }

    private static boolean loadGame(PlayerManager playerManager, TurnHandler turnHandler,
                                    EventManager eventManager, GameInputHandler inputHandler, Scanner scanner) {
        return false;
    }

    //private static boolean loadGame(PlayerManager playerManager, TurnHandler turnHandler,
    //                                EventManager eventManager, GameInputHandler inputHandler, Scanner scanner) {
    //    File file = FileManager.createFile("/data/", "games.txt");
    //    List<String> lines = FileManager.readFile(file);
    //    if (lines.isEmpty()) {
    //        boolean choice = inputHandler.getYesNoInput(AppConstants.createSelection("No data found in the file or file could not be read. Do you want to start a new game [y/n]"), scanner);
    //        return choice;
    //    }
    //    System.out.println("Game Details:");
    //    System.out.printf("%-5s %-10s %-15s %-15s %-10s%n", "Index", "Name", "Number of People", "Difficulty", "Character");
    //    System.out.println("-------------------------------------------------------------------");
    //    for (int i = 0; i < lines.size(); i++) {
    //        String[] parts = lines.get(i).split(",");
    //        if (parts.length == 4) {
    //            String name = parts[0].trim();
    //            String numberOfPeople = parts[1].trim();
    //            String difficulty = parts[2].trim();
    //            String character = parts[3].trim();
    //            // Print the details with index
    //            System.out.printf("%-5d %-10s %-15s %-15s %-10s%n", i + 1, name, numberOfPeople, difficulty, character);
    //        } else {
    //            System.out.println("Invalid line format: " + lines.get(i));
    //        }
    //    }
    //    int choice = -1;
    //    while (choice < 1 || choice > lines.size()) {
    //        System.out.print("Enter the number of your choice (1-" + lines.size() + "): ");
    //        if (scanner.hasNextInt()) {
    //            choice = scanner.nextInt();
    //            if (choice < 1 || choice > lines.size()) {
    //                System.out.println("Invalid choice. Please choose a number between 1 and " + lines.size() + ".");
    //            }
    //        } else {
    //            System.out.println("Invalid input. Please enter a number.");
    //            scanner.next(); // Clear invalid input
    //        }
    //    }
    //    System.out.println("You selected:");
    //    String[] selectedParts = lines.get(choice - 1).split(",");
    //    String name = selectedParts[0].trim();
    //    int numPlayers = Integer.parseInt(selectedParts[1].trim());
    //    String difficulty = selectedParts[2].trim(); // Difficulty is a string
    //    int character = Integer.parseInt(selectedParts[3].trim());
    //    List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);
    //    Player human = null;
    //    for (Player currentPlayer : availablePlayers) {
    //        if (currentPlayer.getName().equals(name)) {
    //            human = currentPlayer;
    //        }
    //    }
    //    // Display all selections to the user and confirm
    //    System.out.println((AppConstants.displayGameOptions(numHumanPlayers, numPlayers, difficulty, playableCharacters)));
    //    playerManager.initializePlayers(numPlayers, playableCharacters, difficulty);
    //    runGameLoop(playerManager, scanner, turnHandler, eventManager, inputHandler);
    //    announceWinner(playerManager.getPlayers().getFirst(), inputHandler, scanner);
    //    return true;
    //}
}