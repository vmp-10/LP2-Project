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

    private static void generatePlayers(int numNPCs, ArrayList<Player> players, int characterChoice, String difficulty) {
        Random random = new Random();
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);

        // Add the user's chosen character
        Player chosenPlayer = availablePlayers.get(characterChoice - 1);
        players.add(chosenPlayer);

        // Add random NPCs
        for (int i = 1; i < numNPCs; i++) {
            Player temp = availablePlayers.get(random.nextInt(availablePlayers.size()));
            Player randomPlayer = new Player(temp);
            players.add(randomPlayer);
            players.get(i).setTag(i);
        }
        players.get(0).setTag(0);
    }

    public static void generateEvent(ArrayList<Player> players, int index) {
        Random random = new Random();
        int event = random.nextInt(1,6);
        switch (event) {
            //If trap placed, event that kills random player can be active
            case 0 -> Events.enemyFound(players.get(index), players.get(random.nextInt(players.size())));
            case 1 -> Events.weaponFound(players.get(index));
            case 2 -> Events.itemFound(players.get(index));
            case 3 -> Events.trapFound(players.get(index));
            case 4 -> Events.outOfSafeZone(players.get(index));
            case 5 -> Events.dropLandedNearby(players.get(index));
            case 6 -> Events.allQuiet(players.get(index));
        }
    }

    public static void main(String[] args) {
        LoggingManager loggingManager = LoggingManager.getInstance();
        loggingManager.setLoggingLevel(Level.ALL);
        loggingManager.disableLogging();
        DEBUG_LOGGER.info("Application started.");

        String[] difficulties = {"Easy", "Normal", "Hard", "Joe Must Die", "Custom"};

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
                     */

                    boolean confirmed = false; //Make sure player desires his options

                    int characterChoice = 0;
                    int numNPCs = 0;
                    int difficultyChoice = 0;
                    String difficulty = null;

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

                        System.out.print(AppConstants.createHeader("Choose difficulty: "));
                        System.out.print(AppConstants.DIFFICULTY);

                        while (!validInput) {
                            try {
                                input = scanner.nextLine();
                                difficultyChoice = Integer.parseInt(input);
                                if (difficultyChoice >= 1 && difficultyChoice <= 5) {
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
                        switch (difficultyChoice) {
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
                        }

                        difficulty = difficulties[difficultyChoice - 1];
                        while (!validInput) {
                            try {
                                List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);

                                input = scanner.nextLine();
                                characterChoice = Integer.parseInt(input);

                                if (characterChoice > 0 && characterChoice <= availablePlayers.size()) {
                                    validInput = true;
                                } else {
                                    System.out.print("Invalid input. Please select a valid option: ");
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a valid integer for the character choice: ");
                            }
                        }

                        System.out.println(AppConstants.EQUALS_DIVIDER);
                        System.out.println("Number of players: " + numNPCs);
                        System.out.println("Difficulty: " + difficulty);
                        System.out.println("Character choice: " + Player.preMadePlayers.get(difficulty).get(characterChoice - 1).getName());

                        validInput = false;
                        System.out.print(AppConstants.createSelection("Are you sure of these options [y/n]: "));

                        while (!validInput) {
                            try {
                                input = scanner.nextLine();

                                if (input.toLowerCase().equals("y")) {
                                    confirmed = true;  // Exit the loop if 'y' is selected
                                    validInput = true; // Exit this inner loop
                                } else if (input.toLowerCase().equals("n")) {
                                    System.out.println("Restarting the game creation process.");
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
                    generatePlayers(numNPCs, players, characterChoice, difficulty);

                    //Start game, game runs on a turn system
                    boolean gameRunning = true;
                    while (gameRunning) {
                        for (int i = 0; i < players.size(); i++) {
                            //Human's turn
                            if (players.get(i).getTag() == 0) {
                                Player human = players.getFirst();

                                System.out.println(AppConstants.createBox(AppConstants.displayStats(human)));

                                //If player has an item, displays option to use them.
                                if (human.hasItems()) {
                                    List<Item> items = human.getItems();
                                    System.out.println(AppConstants.displayItems(items.size(), items));
                                    boolean validInput = false;

                                    int itemChoice = 0;
                                    while (!validInput) {
                                        try {
                                            itemChoice = scanner.nextInt();

                                            if (itemChoice > items.size() || itemChoice < 0) {
                                                System.out.print("Insert a valid option: ");
                                            }


                                        } catch (NumberFormatException e) {
                                            System.out.print("Insert a valid option: ");
                                        }
                                    }

                                    items.get(itemChoice).use(human);
                                } else {
                                    System.out.println(AppConstants.EQUALS_DIVIDER);
                                    generateEvent(players, i); //if is a npc this doesn't print anything
                                }
                            } else {
                                Player current = players.get(i);
                                Random random = new Random();
                          
                                //Simulate a 25% to use item
                                if (current.hasItems() && random.nextInt(4) == 1) {
                                    List<Item> items = current.getItems();
                                    Item item = items.get(random.nextInt(0, items.size()));
                                    item.use(current);
                                } else {
                                    generateEvent(players, i);
                                }
                            }

                            if (players.size() == 1) {
                                gameRunning = false;
                            }
                        }
                    }
                    System.out.println(AppConstants.createBox("Winner is player " + players.get(0).getTag() + " !"));
                }
                break;
                case "2": {
                    //TODO: Fernando's part
                }
                break;
                case "3": {
                    //TODO: Javi's part
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


