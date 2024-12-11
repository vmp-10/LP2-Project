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

    public static void handleHumanTurn(Player human, ArrayList<Player> players, Scanner scanner,
                                       EventManager eventManager, TurnHandler turnHandler, GameInputHandler inputHandler) {
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
                        eventManager.generateEvent(players, human.getTag(), turnHandler, inputHandler);
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
        System.out.print(AppConstants.displayItemChoice(human));

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= items.size()) {
                    items.get(choice - 1).use(human);
                    break;
                } else if (choice == items.size() + 1){ //equals don't use any item
                    break;
                }
                System.out.println("Invalid choice. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    public static void handleNPCTurn(Player NPC, ArrayList<Player> players,
                                     EventManager eventManager, TurnHandler turnHandler, GameInputHandler inputHandler) {
        Random random = new Random();
        if (NPC.hasItems() && random.nextInt(4) == 1) {
            List<Item> items = NPC.getItems();
            Item item = items.get(random.nextInt(items.size()));
            item.use(NPC);
        } else {
            //TODO: Passing NPC.getTag() will give errors in the future, 80%. If there's out of bound, this is where it comes from.
            eventManager.generateEvent(players, NPC.getTag(), turnHandler, inputHandler);
        }
    }
}
