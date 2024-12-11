package core;

import characters.Player;
import common.AppConstants;
import tools.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class TurnHandler {
    public TurnHandler(){

    }

    public static void handleHumanTurn(Player human, ArrayList<Player> players, Scanner scanner, EventManager eventManager) {
        boolean inputInvalid = false;
        do {
            try {

                System.out.println(AppConstants.createBox("New round begins!", 50));
                System.out.println();
                System.out.println(AppConstants.createBox(players.size() + " players remaining", 50));
                System.out.println(AppConstants.createBox(AppConstants.displayPlayerStats(human), 50));
                System.out.println(AppConstants.createBox(AppConstants.displayWeapons(human), 50));

                System.out.println("Press Enter to continue... ");
                String input = scanner.nextLine();

                // Proceed only if Enter is pressed
                if (input.isEmpty()) {
                    if (human.hasItems()) {
                        inputInvalid = true;
                        handleHumanItemUsage(human, scanner);
                    } else {
                        inputInvalid = true;
                        eventManager.generateEvent(players, human.getTag());
                    }
                } else {
                    System.out.println("Press Enter to continue...");
                }
            } catch (Exception e) {
                System.out.println("Press Enter to continue... ");
            }
        } while (!inputInvalid);
    }


    public static void handleHumanItemUsage(Player human, Scanner scanner) {
        List<Item> items = human.getItems();
        System.out.println(AppConstants.displayItems(items.size(), items));

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= items.size()) {
                    items.get(choice - 1).use(human);
                    break;
                }
                System.out.println("Invalid choice. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void handleNPCTurn(Player npc, ArrayList<Player> players, EventManager eventManager) {
        Random random = new Random();
        if (npc.hasItems() && random.nextInt(4) == 1) {
            List<Item> items = npc.getItems();
            Item item = items.get(random.nextInt(items.size()));
            item.use(npc);
        } else {
            eventManager.generateEvent(players, npc.getTag());
        }
    }
}
