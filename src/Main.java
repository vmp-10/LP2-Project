import characters.Player;
import common.AppConstants;
import common.FileManager;
import common.LoggingManager;
import core.*;

import java.io.File;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameInputHandler inputHandler = new GameInputHandler();
        PlayerManager playerManager = new PlayerManager();
        TurnHandler turnHandler = new TurnHandler();
        EventManager eventManager = new EventManager();
        LoggingManager loggingManager = new LoggingManager();

        boolean running = true;

        while (running) {
            System.out.print(AppConstants.MENU);
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    startGame(scanner, inputHandler, playerManager, turnHandler, eventManager, loggingManager);
                    break;
                case "2":
                    loadGame(playerManager, turnHandler, eventManager, inputHandler, scanner, loggingManager);
                    break;
                case "3":
                    createGame(inputHandler, scanner, playerManager);
                    break;
                case "4":
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

    private static void createGame(GameInputHandler inputHandler, Scanner scanner, PlayerManager playerManager) {
        int numHumanPlayers = 0, numPlayers = 0;
        String difficulty = null;
        List<Player> playableCharacters = null;

        // Get player selections
        numHumanPlayers = inputHandler.promptForHumans(scanner);
        playerManager.setNumHumanPlayers(numHumanPlayers);

        numPlayers = inputHandler.promptForPlayers(scanner);
        difficulty = inputHandler.promptForDifficulty(scanner);
        playableCharacters = inputHandler.promptForCharacter(scanner, difficulty, numHumanPlayers);

        // Display all selections to the user and confirm
        System.out.println((AppConstants.displayGameOptions(numHumanPlayers, numPlayers, difficulty, playableCharacters)));

        boolean confirmation = inputHandler.getGameConfirmation(scanner);
        if (!confirmation) {
            System.out.println("Not saving to file.");
            return;
        }
        FileManager.saveGame(numHumanPlayers, numPlayers, difficulty, playableCharacters);
    }

    private static void startGame(Scanner scanner, GameInputHandler inputHandler,
                                  PlayerManager playerManager, TurnHandler turnHandler,
                                  EventManager eventManager, LoggingManager loggingManager) {
        int numHumanPlayers = 0, numPlayers = 0;
        String difficulty = null;
        List<Player> playableCharacters = null;

        // Get player selections and confirmation
        numHumanPlayers = inputHandler.promptForHumans(scanner);
        playerManager.setNumHumanPlayers(numHumanPlayers);

        numPlayers = inputHandler.promptForPlayers(scanner);
        difficulty = inputHandler.promptForDifficulty(scanner);
        playableCharacters = inputHandler.promptForCharacter(scanner, difficulty, numHumanPlayers);

        // Display all selections to the user and confirm
        System.out.println((AppConstants.displayGameOptions(numHumanPlayers, numPlayers, difficulty, playableCharacters)));

        boolean confirmation = inputHandler.getGameConfirmation(scanner);
        if (!confirmation) {
            System.out.println("Going back to main menu.");
            return;
        }

        // Initialize players
        playerManager.initializePlayers(numPlayers, playableCharacters, difficulty);

        runGameLoop(playerManager, scanner, turnHandler, eventManager, inputHandler, loggingManager);

        announceWinner(playerManager.getPlayers().getFirst(), scanner);

        saveGameLogs(scanner, playerManager, numHumanPlayers, numPlayers, difficulty, playableCharacters, loggingManager);
    }

    private static void runGameLoop(PlayerManager playerManager, Scanner scanner,
                                    TurnHandler turnHandler, EventManager eventManager,
                                    GameInputHandler inputHandler, LoggingManager loggingManager) {
        boolean gameRunning = true;
        boolean allHumansDead = false;
        boolean watchEnd = false;
        int rounds = 1;

        List<Player> deadPlayers = new ArrayList<>();
        ArrayList<Player> players = playerManager.getPlayers();

        while (gameRunning) {
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                // If the player is dead, mark them for removal
                if (player.isDead()) {
                    deadPlayers.add(player);
                } else {
                    if (playerManager.isHuman(player)) {
                        turnHandler.handleHumanTurn(player, scanner, eventManager, playerManager, loggingManager);
                    } else {
                        turnHandler.handleNPCTurn(player, eventManager, playerManager, loggingManager);
                    }
                }

                // Checks if a human player died
                if (playerManager.isHuman(player) && player.isDead() && !allHumansDead) {
                    playerManager.setNumHumanPlayers(playerManager.getNumHumanPlayers() - 1);
                    System.out.println(AppConstants.createHeader("Player " + player.getTag() + " has died."));
                    if (playerManager.getNumHumanPlayers() == 0) {
                        allHumansDead = true;
                    }
                }

                // All human players died
                if (allHumansDead && !watchEnd) {
                    watchEnd = GameInputHandler.getYesNoInput(AppConstants
                            .createSelection("All human players have died, do you want to see who wins [y/n]: "), scanner);
                    if (!watchEnd) {
                        return;
                    }
                }

                // Remove all dead players from the list
                playerManager.removePlayers(deadPlayers);

                if (players.size() == 1) {
                    gameRunning = false;
                    return;
                }

            }
            loggingManager.incrementRounds();
        }
    }

    private static void announceWinner(Player winner, Scanner scanner) {
        System.out.println(AppConstants.createBox("Winner is player " + winner.getTag() + "!", 35));
    }

    private static void saveGameLogs(Scanner scanner, PlayerManager playerManager,
                                     int numHumanPlayers, int numPlayers, String difficulty,
                                     List<Player> playableCharacters, LoggingManager loggingManager) {
        boolean save = GameInputHandler
                .getYesNoInput(AppConstants.createSelection("Do you want to save the match to a file [y/n]: "), scanner);
        if (save) {
            FileManager.saveGameLogs(numHumanPlayers, numPlayers, difficulty, playableCharacters, playerManager, loggingManager);
        } else {
            System.out.println("Match not saved.");
        }
    }

    private static void loadGame(PlayerManager playerManager, TurnHandler turnHandler,
                                 EventManager eventManager, GameInputHandler inputHandler,
                                 Scanner scanner, LoggingManager loggingManager) {
        File file = FileManager.getFile("data", "games.txt");

        if (file == null) {
            System.out.print(AppConstants.createHeader("There are no games to load, returning to main menu."));
            return;
        }

        List<String> games = FileManager.readFile(file);

        // Check if the file is empty or couldn't be read
        if (games == null || games.isEmpty()) {
            System.out.print(AppConstants.createHeader("There are no games to load, returning to main menu."));
            return;
        }

        int choice = chooseGame(scanner, FileManager.formatLines(games));
        if (choice < 0 || choice >= games.size()) {
            System.out.print(AppConstants.createHeader("Invalid choice, returning to main menu."));
            return;
        }

        int numHumanPlayers = 0, numPlayers = 0;
        String difficulty = "";
        List<Player> playableCharacters = new ArrayList<>();

        String game = games.get(choice);

        // Check if the selected game is valid
        if (game == null || game.trim().isEmpty()) {
            System.out.print(AppConstants.createHeader("Invalid game data, returning to main menu."));
            return;
        }

        String[] lines = game.split(";");
        String[] data = lines[0].split(",");
        String[] names = lines[1].split(",");

        try {
            numHumanPlayers = Integer.parseInt(data[0]);
            numPlayers = Integer.parseInt(data[1]);
            difficulty = data[2];
        } catch (NumberFormatException e) {
            System.out.print(AppConstants.createHeader("Error parsing game data, returning to main menu."));
            return;
        }

        playerManager.setNumHumanPlayers(numHumanPlayers);
        List<Player> availableCharacters = Player.preMadePlayers.get(difficulty);

        for (Player character : availableCharacters) {
            for (String name : names) {
                if (character.getName().equals(name)) {
                    playableCharacters.add(character);
                }
            }
        }

        System.out.println(AppConstants.displayGameOptions(numHumanPlayers, numPlayers, difficulty, playableCharacters));
        System.out.println(AppConstants.EQUALS_DIVIDER);

        //Initialize players and proceed with the game
        playerManager.initializePlayers(numPlayers, playableCharacters, difficulty);

        runGameLoop(playerManager, scanner, turnHandler, eventManager, inputHandler, loggingManager);

        announceWinner(playerManager.getPlayers().getFirst(), scanner);

        saveGameLogs(scanner, playerManager, numHumanPlayers, numPlayers, difficulty, playableCharacters, loggingManager);
    }


    private static int chooseGame(Scanner scanner, List<String> games) {
        int choice;

        System.out.print(AppConstants.createHeader("Select which game to load: "));

        for (int i = 0; i < games.size(); i++) {
            System.out.println("Game " + (i + 1) + ":\n" + games.get(i));
        }
        System.out.print("Option: ");

        while (true) {

            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input) - 1;
                if (choice >= 0 && choice < games.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showCredits() {
        System.out.print(AppConstants.CREDITS);
    }

    private static void handleInvalidInput() {
        System.out.println("Invalid input. Please enter a valid option (1, 2, 3, or 'exit').");
    }
}