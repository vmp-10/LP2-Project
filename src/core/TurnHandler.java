package core;

import characters.Player;
import common.AppConstants;
import common.LoggingManager;
import tools.Item;

import java.util.*;

public class TurnHandler {
    public TurnHandler() {

    }

    public static void handleHumanTurn(Player player, Scanner scanner, EventManager eventManager,
                                       PlayerManager playerManager, LoggingManager loggingManager) {
        while(true) {
            try {

                System.out.println(AppConstants.createBox("Player " + player.getTag() + "'s turn", 50));
                System.out.println();
                System.out.println(AppConstants.createBox(playerManager.getPlayers().size() + " players remaining", 50));
                System.out.println(AppConstants.createBox(AppConstants.displayPlayerStats(player), 50));
                System.out.println(AppConstants.createBox(AppConstants.displayWeapon(player), 50));

                System.out.println("Press Enter to continue... ");
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    if (player.hasItems()) {
                        handleHumanItemUsage(player, scanner, playerManager);
                    }
                    eventManager.generateEvent(player, playerManager, loggingManager);

                    break;
                } else {
                    System.out.println("Press Enter to continue...");
                }
            } catch (Exception e) {
                System.out.println("Press Enter to continue...");
            }
        }
    }

    public static void handleHumanItemUsage(Player human, Scanner scanner, PlayerManager playerManager) {
        List<Item> items = human.getItems();
        System.out.print(AppConstants.displayItemChoice(human));

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= items.size()) {
                    items.get(choice - 1).use(human, playerManager);
                    break;
                } else if (choice == items.size() + 1) {
                    break;
                }
                System.out.println("Invalid choice. Please try again.");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void handleNPCTurn(Player player, EventManager eventManager,
                                     PlayerManager playerManager, LoggingManager loggingManager) {
        Random random = new Random();
        //30% of using item if NPC has items
        if (player.hasItems() && random.nextInt(0, 100) < AppConstants.BOT_CHANCE_TO_USE_ITEM) {
            List<Item> items = player.getItems();
            Item item = items.get(random.nextInt(items.size()));
            item.use(player, playerManager);
        } else {
            eventManager.generateEvent(player, playerManager, loggingManager);
        }
    }
}
