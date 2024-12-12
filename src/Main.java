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
                    loadGame();
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
            return;
        }

        // Initialize players
        //playerManager.initializePlayers(numPlayers, human, difficulty);
        playerManager.initializeDebugPlayers(numPlayers, human);

        runGameLoop(playerManager, scanner, turnHandler, eventManager, inputHandler);

        announceWinner(playerManager.getPlayers().get(0)); // Winner is at index 0
    }

    private static void runGameLoop(PlayerManager playerManager, Scanner scanner,
                                    TurnHandler turnHandler, EventManager eventManager, GameInputHandler inputHandler) {
        boolean gameRunning = true;

        ArrayList<Player> players = playerManager.getPlayers();
        List<Player> playersToRemove = new ArrayList<>();


        while (gameRunning) {
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                System.out.println("[DEBUG] TAG: " + currentPlayer.getTag() + ", HP: " + currentPlayer.getHealth());

                // If the player is dead, mark them for removal
                if (!currentPlayer.isAlive()) {
                    playersToRemove.add(currentPlayer);
                    continue; // Skip iteration
                }

                if (currentPlayer.getTag() == 0) {
                    turnHandler.handleHumanTurn(currentPlayer, players, scanner, eventManager, turnHandler, inputHandler);
                } else {
                    turnHandler.handleNPCTurn(currentPlayer, players, eventManager, turnHandler, inputHandler, i);
                }

                //Checks if human player is dead and displays a game over screen.
                if (currentPlayer.getTag() == 0 && !currentPlayer.isAlive()) {
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

    private static void announceWinner(Player winner) {
        if (winner.getTag() == 0) {
            System.out.println(AppConstants.createBox("You have won, congrats!", 35));
        }
        System.out.println(AppConstants.createBox("Winner is player " + winner.getTag() + "!", 35));
    }

    private static void loadGame() {
        File file = FileManager.createFile("/data/", "game-1.txt");
        // Implement game loading logic here
    }
}