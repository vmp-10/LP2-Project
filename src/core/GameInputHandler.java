package core;

import characters.Player;
import common.AppConstants;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameInputHandler {
    private static final int MIN_NPC_PLAYERS = 50;
    private static final int MAX_NPC_PLAYERS = 150;

    private static final int MIN_PLAYERS = 1;
    private static final int MAX_PLAYERS = 4;

    private static final String[] DIFFICULTIES = {"Easy", "Normal", "Hard", "Joe Must Die"};


    public GameInputHandler() {

    }

    public static int promptForPlayers(Scanner scanner) {
        int numPlayers;
        while (true) {
            System.out.print(AppConstants.createSelection("Choose number of players between " + MIN_NPC_PLAYERS + " and " + MAX_NPC_PLAYERS + ": "));
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers >= MIN_NPC_PLAYERS && numPlayers <= MAX_NPC_PLAYERS) {
                    break;
                }
                System.out.println("Invalid input. Please try again.: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
        return numPlayers;
    }

    public static String promptForDifficulty(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print(AppConstants.createHeader("Choose difficulty: "));
            System.out.print(AppConstants.DIFFICULTY);

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= DIFFICULTIES.length) {
                    return DIFFICULTIES[choice - 1];
                }
                System.out.println("Invalid input. Please try again: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static List<Player> promptForCharacter(Scanner scanner, String difficulty, int numHumanPlayers) {
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);
        int choice;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numHumanPlayers; i++) {
            while (true) {
                System.out.print(AppConstants.createHeader("Choose character for player "+ i + ": "));
                System.out.print(AppConstants.createCharacterString(difficulty));

                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice > 0 && choice <= availablePlayers.size()) {
                        players.add(availablePlayers.get(choice - 1));
                        break;
                    }
                    System.out.println("Invalid input. Please try again: ");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number: ");
                }
            }
        }

        return players;
    }

    public static boolean getGameConfirmation(Scanner scanner) {
        return getYesNoInput(AppConstants.createSelection("Is everything correct? [y/n]: "), scanner);
    }

    public static boolean getYesNoInput(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                // Validate input
                if ("y".equals(input)) return true; // Valid input: yes
                if ("n".equals(input)) return false; // Valid input: no

                // Handle invalid input case
                System.out.println("Invalid input. Please enter [y/n]: ");
            } catch (InputMismatchException e) {
                System.out.println("Input error: Please enter a valid [y/n] response.");
            }
        }
    }

    public int promptForHumans(Scanner scanner) {
        int numPlayers;
        while (true) {
            System.out.print(AppConstants.createSelection("Choose number of players between " + MIN_PLAYERS + " and " + +MAX_PLAYERS + ": "));
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
                if (numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS) {
                    break;
                }
                System.out.println("Invalid input. Please try again.: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
        return numPlayers;
    }
}
