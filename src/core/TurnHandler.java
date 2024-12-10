package core;

import characters.Player;
import common.AppConstants;
import tools.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static core.EventManager.generateEvent;

public class TurnHandler {
    public TurnHandler(){

    }

    public static void handleHumanTurn(Player human, ArrayList<Player> players, Scanner scanner) {
        System.out.println(AppConstants.createBox(players.size() + " players remaining"));
        System.out.println(AppConstants.createBox(AppConstants.displayStats(human)));

        if (human.hasItems()) {
            handleHumanItemUsage(human, scanner);
        } else {
            generateEvent(players, human.getTag());
        }
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

    public static void handleNPCTurn(Player npc, ArrayList<Player> players) {
        Random random = new Random();
        if (npc.hasItems() && random.nextInt(4) == 1) {
            List<Item> items = npc.getItems();
            Item item = items.get(random.nextInt(items.size()));
            item.use(npc);
        } else {
            generateEvent(players, npc.getTag());
        }
    }
}
