package core;

import characters.Player;
import common.AppConstants;
import tools.Item;
import tools.Objects;
import tools.Potion;
import tools.Weapon;
import tools.Defense;

import java.util.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;

//Verify if player or NPC, if NPC, no prints nor inputs allowed -> FIXED. Replaced isHuman() with getTag, if tag == 0 player is Human.
public class Events {
    private static final Random random = new Random();
    private static List<Weapon> weapons = List.of(Objects.MUD_SWORD, Objects.COPPER_SWORD, Objects.DAMASCUS_STEEL_SWORD, Objects.IRIDIUM_SWORD);
    private static List<Potion> potions = List.of(Objects.HEALTH_POTION, Objects.STAMINA_POTION, Objects.SUPER_HEALTH_POTION, Objects.SUPER_STAMINA_POTION);
    private static List<Defense> defenses = List.of(Objects.CLOTH_ARMOR, Objects.LEATHER_ARMOR, Objects.CHAIN_ARMOR, Objects.DIAMOND_ARMOR);

    private Events() {

    }

    public static void allQuiet(Player player) {
        //If player is human he'll be given te choice to use an item, else nothing happens
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nothing is happening right now.");

            if (player.hasItems()) {
                System.out.print(AppConstants.createSelection("Do you want to use an item from your inventory? [y/n] "));
                String input;

                boolean validInput = false;
                while (!validInput) {
                    try {
                        input = scanner.nextLine();

                        if (input.toLowerCase().equals("y")) {
                            List<Item> items = player.getItems();
                            System.out.println(AppConstants.displayItems(items.size(), items));

                            boolean choice = false;
                            int itemChoice = 0;
                            while (!choice) {
                                try {
                                    itemChoice = scanner.nextInt();

                                    if (itemChoice > items.size() || itemChoice < 1) {
                                        System.out.print("Insert a valid option: ");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.print("Insert a valid option: ");
                                }
                            }

                            items.get(itemChoice).use(player);

                            validInput = true;
                        } else if (input.toLowerCase().equals("n")) {
                            validInput = true;
                        } else {
                            System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public static void dropLandedNearby(Player player) {
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            String input;

            boolean validInput = false;

            System.out.print(AppConstants.createSelection("A drop has landed nearby, do you want to loot it (Keep in mind it can be traped) [y/n]: "));

            while (!validInput) {
                try {
                    input = scanner.nextLine();

                    if (input.toLowerCase().equals("y")) {
                        validInput = true;
                        switch (random.nextInt(3)) {
                            case 0 -> weaponFound(player);
                            case 1 -> itemFound(player);
                            case 2 -> dropTrapFound(player);
                        }
                    } else if (input.toLowerCase().equals("n")){
                        validInput = true;
                        System.out.println("You ran away.");
                    } else {
                        System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                }
            }
        } else {
            switch (random.nextInt(0, 3)) {
                case 0 -> weaponFound(player);
                case 1 -> itemFound(player);
                case 2 -> dropTrapFound(player);
                case 3 -> allQuiet(player);
            }
        }
    }

    //The drop was rigged with a trap
    private static void dropTrapFound(Player player) {
        boolean tookDamage = false;

        if (random.nextInt(0, 100) < 25) {
            tookDamage = true;
        }

        if (player.getTag() == 0) {
            if (tookDamage) {
                System.out.println("You opened the drop but there was a trap.");
                player.takeDamage(10, true);
            } else {
                System.out.println("You opened the drop but there was a trap. You got lucky and avoided it.");
            }
        } else {
            player.takeDamage(10, false);
        }
    }

    public static void enemyFound(Player player1, Player player2) {
        if (player1.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            String input;

            boolean validInput = false;

            System.out.print(AppConstants.createSelection("You found a player, do you want to fight [y/n]: "));
            while (!validInput) {
                try {
                    input = scanner.nextLine();

                    if (input.toLowerCase().equals("y")) {
                        validInput = true;
                        fight(player1, player2);
                    } else if (input.toLowerCase().equals("n")){
                        validInput = true;
                        escape(player1, player2, true);
                    } else {
                        System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                }
            }
        } else {
            if (random.nextInt(0, 100) < 25) {
                fight(player1, player2);
            } else {
                escape(player1, player2, false);
            }
        }
    }

    private static void fight(Player player1, Player player2) {
        if (player1.getTag() == 0) {
            player1.attack(player2, true);
        } else {
            player1.attack(player2, false);
        }
    }

    // You have a chance to be seen by the other player and be attacked
    private static void escape(Player player1, Player player2, boolean isHuman) {
        int staminaLoss = random.nextInt(2);
        int stamina = player1.getStamina();

        if (isHuman) {
            System.out.println("Trying to escape. ");
        }

        if (stamina > 5)
            player1.setStamina(stamina - staminaLoss);
        else {
            player2.attack(player1, true); //Couldn't escape, takes tamage
        }
    }

    public static void weaponFound(Player player) {

        //TODO: Apply rarity for each weapon.
        Weapon weapon = weapons.get(random.nextInt(weapons.size()));

        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            // TODO: Show difference in stats and prompt user which weapon he wants to replace
            System.out.print(AppConstants.createSelection("You found a " + weapon.getName() + ", do you want to pick it up? [y/n] "));

            String input = "";
            boolean validInput = false;
            while (!validInput) {
                try {
                    input = scanner.nextLine();

                    if (input.toLowerCase().equals("y")) {
                        validInput = true;
                        player.addWeapon(weapon);
                        System.out.println("The weapon " + weapon.getName() + " was added to the inventory");
                    } else if (input.toLowerCase().equals("n")) {
                        validInput = true;
                    } else {
                        System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                }
            }
        } else {

            Random random = new Random();
            boolean NPCchoice = random.nextBoolean();  // Randomly choose between true (y) or false (n)

            if (NPCchoice) {
                player.addWeapon(weapon);
            } else {
                allQuiet(player);
            }
        }
    }

    public static void itemFound(Player player) {
        //Get all items available
        List<Item> items = Stream.concat(potions.stream(), defenses.stream()).collect(Collectors.toList());
        Item item = items.get(random.nextInt(items.size()));

        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            System.out.print(AppConstants.createSelection("You found a " + item.getName() + ", do you want to pick it up? [y/n]: "));

            if (player.hasItems()) {
                List<Item> playerItems = player.getItems();
                System.out.print(AppConstants.displayItems(playerItems.size(), playerItems));
            }

            String input = "";
            boolean validInput = false;
            while (!validInput) {
                try {
                    input = scanner.nextLine();

                    if (input.toLowerCase().equals("y")) {
                        player.addItem(item);
                        validInput = true;
                    } else if (input.toLowerCase().equals("n")) {
                        validInput = true;
                    } else {
                        System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter a valid answer [y/n]: ");
                }
            }

        } else {
            // Non-Human Player (NPC) Case
            // Automatically decide if the NPC will pick up the item (e.g., randomly choose "y" or "n")

            // Randomly choose between true (y) or false (n)
            if (random.nextBoolean()) {
                player.addItem(item);
            } else {
                allQuiet(player);
            }
        }
    }

    public static void trapFound(Player player) {
        boolean tookDamage = false;

        if (random.nextInt(0, 100) < 25) {
            tookDamage = true;
        }

        if (player.getTag() == 0) {
            if (tookDamage) {
                System.out.println("You fell on a trap.");
                player.takeDamage(15, true);
            } else {
                System.out.println("You got lucky and you avoided a trap.");
            }
        } else {
            player.takeDamage(15, false);
        }
    }

    public static void outOfSafeZone(Player player) {
        int chanceToOutrun = random.nextInt(0, 100);
        boolean escaped = false;
        if (chanceToOutrun < 25) {
            escaped = false;
        }

        if (player.getTag() == 0) {
            if (!escaped) {
                //TODO: This specific event should remove damage from Health, not armor. Fix.
                System.out.println("You're in the storm, taking damage.");
                player.takeDamage(5, true);
            } else {
                System.out.println("You escaped the storm.");
            }
        } else {
            if (!escaped) {
                player.takeDamage(5, false);
            }
        }
    }
}
