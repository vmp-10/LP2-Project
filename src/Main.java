import characters.Player;
import common.AppConstants;
import common.LoggingManager;
import core.Events;
import tools.Item;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger DEBUG_LOGGER = LoggingManager.getInstance().getLogger();

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

    public static void generateEvent(ArrayList<Player> players, int index) {
        Random random = new Random();
        int event = random.nextInt(0,7);
        switch (event) {
            //If trap placed, event that kills random player can be active
            case 0 -> Events.enemyFound(players.get(index), players.get(random.nextInt(players.size())));
            case 1 -> Events.weaponFound(players.get(index));
            case 2 -> Events.itemFound(players.get(index));
            case 3 -> Events.trapFound(players.get(index));
            case 4 -> Events.outOfSafeZone(players.get(index));
            case 5 -> Events.dropLandedNearby(players.get(index));
            case 6 -> Events.allQuiet(players.get(index));
            case 7 -> Events.takeDamage(players.get(index), 10);
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

                    boolean confirmed = false; //Make sure player desires his options

                    int characterChoice = 0;
                    int numNPCs = 0;
                    int difficulty;

                    while (!confirmed) {
                        System.out.print(AppConstants.createSelection("Choose number of players between 50 and 150: "));
                        numNPCs = 0;
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
                        difficulty = 0;

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

                        characterChoice = 0;

                        while (!validInput) {
                            try {
                                System.out.print(AppConstants.createHeader("Choose character: "));
                                switch (difficulty) {
                                    case 1: {
                                        System.out.print(AppConstants.CHARACTERS_EASY);
                                    }
                                    break;
                                    case 2: {
                                        System.out.print(AppConstants.CHARACTERS_NORMAL);
                                    }
                                    break;
                                    case 3: {
                                        System.out.print(AppConstants.CHARACTERS_HARD);
                                    }
                                    break;
                                    case 4: {
                                        System.out.print(AppConstants.CHARACTERS_JOE_MUST_DIE);
                                    }
                                    break;
                                    case 5: {
                                        System.out.print(AppConstants.CHARACTERS_CUSTOM);
                                    }
                                    break;
                                    default: {
                                        System.out.print("Invalid difficulty level. Please choose a valid difficulty.");
                                    }
                                    break;
                                }
                                input = scanner.nextLine();
                                characterChoice = Integer.parseInt(input);
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a valid integer for the character choice: ");
                            }
                        }

                        System.out.print(AppConstants.createHeader("You selected: "));
                        System.out.println("Number of players: " + numNPCs);
                        System.out.println("Difficulty: " + "Difficulty"); //TODO: Display difficulty, not number
                        System.out.println("Character choice: " + "Character Name"); //TODO: Display Character name

                        validInput = false;
                        System.out.print(AppConstants.createSelection("Are you sure of these options [y/n]: "));

                        while (!validInput) {
                            try {
                                input = scanner.nextLine();

                                if (input.toLowerCase().equals("y")) {
                                    confirmed = true;  // Exit the loop if 'y' is selected
                                    validInput = true; // Exit this inner loop
                                } else if (input.toLowerCase().equals("n")) {
                                    System.out.println("Restarting the selection process.");
                                    validInput = true; // Exit this inner loop and restart the process
                                    break; // Break the inner loop to start over
                                } else {
                                    System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                                }
                            } catch (InputMismatchException e) {
                                System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                            }
                        }
                    }

                    System.out.print(AppConstants.createHeader("Game started, godspeed!"));

                    ArrayList<Player> players = new ArrayList<>(); //Human is getFirst()
                    generatePlayers(numNPCs, players, characterChoice);

                    //Start game, game runs on a turn system
                    boolean gameRunning = true;

                    while (gameRunning) {
                        for (int i = 0; i < players.size(); i++) {
                            //Human's turn
                            if (i == 0) {
                                Player mc = players.getFirst();
                                //If player has an item, displays option to use them.
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
                                gameRunning = false;
                            }
                        }
                        System.out.println(AppConstants.STAR_DIVIDER);
                        System.out.println(AppConstants.CROWN);
                        System.out.println(AppConstants.createHeader("Winner is ") + "Winner name" + "!");
                    }
                }
                break;
                case "2": {
                    //TODO: Fernando's part
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
        DEBUG_LOGGER.info("Game ended.");
    }
}


