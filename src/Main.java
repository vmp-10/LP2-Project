import characters.Character;
import characters.Player;
import common.AppConstants;
import common.FileUtils;
import common.LoggingManager;
import core.Events;
import tools.Item;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger DEBUG_LOGGER = LoggingManager.getInstance().getLogger();
    private static final Logger GAME_LOGGER = LoggingManager.getInstance().getLogger(); //Used for saving logs on file


    public static void generateEvent(ArrayList<Player> players, int index) {
        Random random = new Random();
        int event = random.nextInt(10);
        switch (event) {
            //If trap placed, event that kills random player can be active
            case 0 -> Events.enemyFound(players.get(index), players.get(random.nextInt(players.size())));
            case 1 -> Events.weaponFound();
            case 2 -> Events.itemFound();
            case 3 -> Events.trapFound(players.get(index));
            case 4 -> Events.outOfSafeZone(players.get(index));
            case 5 -> Events.dropLandedNearby();
            case 6 -> Events.allQuiet();
            case 7 -> Events.takeDamage(players.get(index), 10);
            default -> System.out.println("Wrong input");
        }
    }

    public static void main(String[] args) {
        LoggingManager loggingManager = LoggingManager.getInstance();
        loggingManager.setLoggingLevel(Level.ALL);
        loggingManager.disableLogging();
        DEBUG_LOGGER.info("Application started.");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print(AppConstants.MENU);
            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1": {
                    DEBUG_LOGGER.info("Game started.");

                    /*
                     * Allows you to select a number of players (Max 150)
                     * Allows them to choose characters and customize them by adding a tool that helps them in the fight.
                     * The game must initialize everything and simulate a battle between the players.
                     * In the end, there should be a winner and a reward for the winner.
                     *
                     * 1. Choose difficulty
                     * 2. Nº players
                     * 3. Choose human character, NPC will be restricted to difficulty's characters
                     * */

                    System.out.print(AppConstants.createHeader("Choose number of players between 50 and 150: "));
                    int numNPCs = 0;
                    boolean validInput = false;

                    while (!validInput) {
                        try {
                            input = scanner.nextLine();
                            numNPCs = Integer.parseInt(input);

                            if (numNPCs >= 50 && numNPCs <= 150) {
                                validInput = true;
                            } else {
                                System.out.print("Invalid input. Please enter a number between 50 and 150: ");
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Please enter a valid integer: ");
                        }
                    }


                    validInput = false;
                    int difficulty = 0;

                    System.out.print(AppConstants.createHeader("Choose difficulty: "));
                    System.out.print(AppConstants.DIFFICULTY);

                    while (!validInput) {
                        try {
                            input = scanner.nextLine();
                            difficulty = Integer.parseInt(input);
                            if (difficulty >= 1 && difficulty <= 5) {
                                validInput = true;
                            } else {
                                System.out.print("Invalid input. Please enter a number between 1 and 5: ");
                            }
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Please enter a valid integer for the difficulty: ");
                        }
                    }

                    validInput = false;

                    System.out.print(AppConstants.createHeader("Choose character: "));
                    System.out.print(AppConstants.CHARACTERS);

                    int characterChoice = 0;

                    while (!validInput) {
                        try {
                            input = scanner.nextLine();
                            characterChoice = Integer.parseInt(input);
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.print("Invalid input. Please enter a valid integer for the character choice: ");
                        }
                    }

                    System.out.println(AppConstants.createHeader("You selected: "));
                    System.out.println("    -> Number of players: " + numNPCs);
                    System.out.println("    -> Difficulty: " + difficulty);
                    System.out.println("    -> Character choice: " + characterChoice);

                    ArrayList<Player> players = new ArrayList<>(); //will keep all player. Human is pos 0
                    generatePlayers(numNPCs, players, characterChoice);
                    Player winner = null;

                    //Start game, game runs on a turn system
                    boolean gameRunning = true;

                    while (gameRunning){
                        for (int i = 0; i < players.size(); i++) {
                            //Human's turn
                            if (i == 0) {
                                Player mc = players.getFirst();
                                //If player has an item, event to use it.
                                if (mc.hasItems()) {
                                    List<Item> items = mc.getItems();
                                    System.out.println(AppConstants.displayItems(items.size(), items));
                                }
                                //If chooses not to or doesn't use it, event
                                generateEvent(players, i);
                            }

                            //If chooses not to or doesn't use it, event
                            generateEvent(players, i);

                            if (players.size() == 1) {
                                winner = players.get(i);
                                gameRunning = false;
                            }
                        }
                        System.out.println(AppConstants.STAR_DIVIDER);
                        System.out.println(AppConstants.CROWN);
                        System.out.println(AppConstants.createHeader("Winner is ") + winner.getName() + "!");
                    }
                }
                break;
                case "2": {
                    //List files
                    File[] files = FileUtils.listFiles("data");

                    //Choose file


                    //Check file
                    //if (file != null) {
                    //    List<String> lines = FileUtils.readFile(file);
                    //    if (!lines.isEmpty()) {
                    //        DEBUG_LOGGER.info("Loaded file: " + file.getName());
                    //    } else {
                    //        DEBUG_LOGGER.info("The selected file is empty. Creating a new one.");
                    //        //Create game file
                    //    }
                    //} else {
                    //    DEBUG_LOGGER.info("No existing game files located. Creating a new one.");
                    //    //Create game file
                    //}
                }
                break;
                case "3": {

                }
                break;
                case "exit": {
                    DEBUG_LOGGER.info("Exiting application.");
                    running = false;
                }
                break;
                default: {
                    System.out.println("Insert valid input (1, 2, 3 or 'exit')");
                    DEBUG_LOGGER.warning("Invalid input received: " + input);
                }
                break;
            }
        }
        DEBUG_LOGGER.info("core.Game ended.");
    }

    private static void generatePlayers(int numNPCs, ArrayList<Player> players, int characterChoice) {
        Random random = new Random();
        for (int i = 0; i < numNPCs; i++) {
            if (i == 0) {
                players.add(Player.preMadePlayers.get(characterChoice));
            } else {
                players.add(Player.preMadePlayers.get(random.nextInt(0, 5)));
            }
        }
    }
}


