package core;

import characters.Player;
import common.AppConstants;

import java.util.List;
import java.util.Scanner;

public class GameInputHandler {
    private Scanner scanner;

    private static final int MIN_PLAYERS = 50;
    private static final int MAX_PLAYERS = 150;

    private static final String[] DIFFICULTIES = {"Easy", "Normal", "Hard", "Joe Must Die", "Custom"};


    public GameInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static int promptForPlayers(Scanner scanner) {
        int numPlayers;
        while (true) {
            System.out.print(AppConstants.createSelection("Choose number of players between " + MIN_PLAYERS + " and " + MAX_PLAYERS + ": "));
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

    public static Player promptForCharacter(Scanner scanner, String difficulty) {
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);
        int choice;
        while (true) {
            System.out.print(AppConstants.createHeader("Choose character: "));

            switch (difficulty) {
                case "Easy": {
                    System.out.print(AppConstants.CHARACTERS_EASY);
                }
                break;
                case "Normal": {
                    System.out.print(AppConstants.CHARACTERS_NORMAL);
                }
                break;
                case "Hard": {
                    System.out.print(AppConstants.CHARACTERS_HARD);
                }
                break;
                case "Joe Must Die": {
                    System.out.print(AppConstants.CHARACTERS_JOE_MUST_DIE);
                }
                break;
                case "Custom": {
                    System.out.print(AppConstants.CHARACTERS_CUSTOM);
                }
                break;
            }

            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice > 0 && choice <= availablePlayers.size()) {
                    return availablePlayers.get(choice - 1);
                }
                System.out.println("Invalid input. Please try again: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static boolean getGameConfirmation(Scanner scanner){
       String input;
        while (true) {
            System.out.print(AppConstants.createSelection("Is everything correct? (yes/no): "));

            try {
                input = scanner.nextLine();

                if (input.equals("yes")) {
                    return true;
                } else if (input.equals("no")){
                    return false;
                }

                System.out.println("Invalid input. Please try again: ");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
