package core;

import characters.Player;
import common.AppConstants;
import tools.Item;

import java.util.*;
import java.util.Scanner;

//Verify if player or NPC, if NPC, no prints nor inputs allowed -> FIXED. Replaced isHuman() with getTag, if tag == 0 player is Human.
public class Events {
    private static final Random random = new Random();

    private Events() {

    }

    //DONE
    public static void allQuiet(Player player) {
        //If player is human he'll be given te choice to use an item
        //If NPC, nothing happens
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nothing is happening right now.");

            if (player.hasItems()) {
                System.out.println("Do you want to use an item from your inventory? (y/n)");
                String choice = "";

                while (true) {
                    try {
                        choice = scanner.nextLine().trim().toLowerCase();

                        if (!choice.equals("y") || !choice.equals("n")) {
                            throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                        }
                        break; // exit the loop is has invalid input
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (choice.equals("y")) {
                    List<Item> items = player.getItems();
                    System.out.println(AppConstants.displayItems(items.size(), items));
                    boolean validInput = false;

                    while (!validInput) {
                        try {
                            int itemChoice = scanner.nextInt();

                            if (itemChoice > items.size() || itemChoice < 0) {
                                System.out.print("Insert a valid option: ");
                            }

                            items.get(itemChoice).use();

                        } catch (NumberFormatException e) {
                            System.out.print("Insert a valid option: ");
                        }
                    }
                }
            }
        }
    }

    public static void dropLandedNearby(Player player) {
        String choice = "";
        int NPC_choice = random.nextInt(1);

        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("A drop has landed nearby, do you want to loot it?");

            while (true) {
                try {
                    choice = scanner.nextLine().trim().toLowerCase();

                    if (!choice.equals("y") || !choice.equals("n")) {
                        throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                    }
                    break; // exit the loop is has invalid input
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (choice.equals("y")) {
                // choose another event as weapon/item found or takeDamage
                int option = random.nextInt(3);
                switch (option) {
                    case 0 -> weaponFound(player);
                    case 1 -> itemFound(player);
                    case 2 -> trapFound(player);
                }
            } else {
                System.out.println("You ran away.");
            }

        } else {
            int option = random.nextInt(0, 3);
            switch (option) {
                case 0 -> weaponFound(player);
                case 1 -> itemFound(player);
                case 2 -> trapFound(player);
                case 3 -> allQuiet(player);
            }
        }
    }

    //DONE
    public static void enemyFound(Player player1, Player player2) {
        if (player1.getTag() == 0) {

            Scanner scanner = new Scanner(System.in);
            String choice;

            System.out.print("You found a player, do you want to fight [y/n]? ");
            while (true) {
                try {
                    choice = scanner.nextLine().trim().toLowerCase();

                    if (!choice.equals("y") && !choice.equals("n")) {
                        throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                    }
                    break; // exit the loop if the input is valid
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (choice.equals("y")) {
                fight(player1, player2);
            } else {
                escape(player1, player2, true);
            }
        } else {
            int chance = random.nextInt(0, 100);
            if (chance < 25) {
                fight(player1, player2);
            } else {
                escape(player1, player2, false);
            }
        }
    }

    //DONE
    private static void fight(Player player1, Player player2) {
        if (player1.getTag() == 0) {
            player1.attack(player2, true);
        } else {
            player1.attack(player2, false);
        }
    }

    //DONE
    private static void escape(Player player1, Player player2, boolean isHuman) {
        // You have a chance to be seen by the other player and be attacked
        int staminaLoss = random.nextInt(2);
        int stamina = player1.getStamina();

        if (isHuman) {
            System.out.println("Trying to escape");
        }

        if (stamina > 5)
            player1.setStamina(stamina - staminaLoss);
        else {
            player2.attack(player1, true); //Couldn't escape, takes tamage
        }
    }


    //TODO: add the weapon to the player inventory
    public static void weaponFound(Player player) {
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            // TODO: Show difference in stats, you need Javi's part done for that
            System.out.println("You found a weapon, do you want to pick " + "NAME OF THE WEAPON" + "up? (y/n)");
            System.out.println("DISPLAY OLD AND NEW WEAPON STATS WITH");
            String choice = "";
            while (true) {
                try {
                    choice = scanner.nextLine().trim().toLowerCase();

                    if (!choice.equals("y") && !choice.equals("n")) {
                        throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                    }
                    break; // exit the loop is has invalid input
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (Objects.equals(choice, "y")) {
                //TODO: Need Javi's part to add weapon
            } else {
                // Non-Human Player (NPC) Case
                // Automatically decide if the NPC will pick up the weapon (e.g., randomly choose "y" or "n")
                Random random = new Random();
                boolean NPCchoice = random.nextBoolean();  // Randomly choose between true (y) or false (n)

                if (NPCchoice) {
                    // TODO: Need Javi's part to add weapon
                    // NPC decides to pick up the weapon
                } else {
                    // NPC decides not to pick up the weapon
                }
            }
        }
    }

    public static void itemFound(Player player) {
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("You found an item, do you want to pick " + "NAME OF THE ITEM" + "up? (y/n)");

            //TODO: Show all items

            String choice = "";
            while (true) {
                try {
                    choice = scanner.nextLine().trim().toLowerCase();

                    if (!choice.equals("y") && !choice.equals("n")) {
                        throw new IllegalArgumentException("Invalid input. Please enter 'y' or 'n'.");
                    }
                    break; // exit the loop is has invalid input
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (choice.equals("y")) {
                // TODO: Item swap, need Javi's part
            }
        } else {
            // Non-Human Player (NPC) Case
            // Automatically decide if the NPC will pick up the item (e.g., randomly choose "y" or "n")
            Random random = new Random();
            boolean NPCchoice = random.nextBoolean();  // Randomly choose between true (y) or false (n)

            if (NPCchoice) {
                // TODO: Need Javi's part to add item
            } else {
                // NPC decides not to pick up the weapon
            }
        }
    }

    public static void trapFound(Player player) {
        int chanceToTakeDamage = random.nextInt(0, 100);
        boolean tookDamage = false;

        if (chanceToTakeDamage < 25) {
            tookDamage = true;
        }

        if (player.getTag() == 0) {
            if (tookDamage) {
                player.takeDamage(15, true);
                if (player.getTag() == 0)
                    System.out.println("There is a trap and you fell for it.");
            } else {
                if (player.getTag() == 0)
                    System.out.println("You got lucky and you avoided a trap.");
            }
        } else {
            player.takeDamage(15, false);
        }
    }

    //DONE
    public static void outOfSafeZone(Player player) {
        int chanceToOutrun = random.nextInt(0, 100);
        boolean escaped = false;
        if (chanceToOutrun < 25) {
            escaped = false;
        }

        if (player.getTag() == 0) {
            player.takeDamage(20, true);
            if (player.getTag() == 0)
                System.out.println("You're in the storm, taking damage");
        } else {
            player.takeDamage(5, true);
        }
    }
}
