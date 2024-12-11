package core;

import characters.Player;
import common.AppConstants;
import tools.*;
import tools.Objects;

import java.util.*;
import java.util.Scanner;

//Verify if player or NPC, if NPC, no prints nor inputs allowed -> FIXED. Replaced isHuman() with getTag, if tag == 0 player is Human.
public class Events {
    private static final Random random = new Random();

    private Events() {

    }

    public static void allQuiet(Player player, TurnHandler turnHandler, GameInputHandler inputHandler) {
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nothing is happening right now.");

            if (player.hasItems()) {
                boolean useItem = inputHandler
                        .getYesNoInput(AppConstants
                                .createSelection("Do you want to use an item from your inventory [y/n]: "), scanner);
                if (useItem) {
                    turnHandler.handleHumanItemUsage(player, scanner);
                }
            }
        }
    }

    public static void dropLandedNearby(Player player, TurnHandler turnHandler, GameInputHandler inputHandler) {
        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            boolean lootDrop = inputHandler.getYesNoInput(AppConstants.createSelection("A drop has landed nearby, do you want to loot it (Keep in mind it can be trapped) [y/n]: "), scanner);
            if (lootDrop) {
                switch (random.nextInt(3)) {
                    case 0 -> weaponFound(player, turnHandler, inputHandler);
                    case 1 -> itemFound(player, turnHandler, inputHandler);
                    case 2 -> dropTrapFound(player);
                }
            } else {
                System.out.println("You ran away.");
            }
        } else {
            switch (random.nextInt(0, 3)) {
                case 0 -> weaponFound(player, turnHandler, inputHandler);
                case 1 -> itemFound(player, turnHandler, inputHandler);
                case 2 -> dropTrapFound(player);
                case 3 -> allQuiet(player, turnHandler, inputHandler);
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

    public static void enemyFound(Player player1, Player player2, GameInputHandler inputHandler) {
        if (player1.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            boolean fight = inputHandler
                    .getYesNoInput(AppConstants
                            .createSelection("You found a player, do you want to fight [y/n]: "), scanner);
            if (fight) {
                fight(player1, player2);
            } else {
                escape(player1, player2, true);
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
        int staminaLoss = random.nextInt(10);
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

    public static void weaponFound(Player player, TurnHandler turnHandler, GameInputHandler inputHandler) {
        Weapon weapon = chooseWeapon();
        Weapon currentWeapon = player.getWeapon(0);

        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            boolean pickWeapon = inputHandler.getYesNoInput(AppConstants
                    .createSelection("You found a " + weapon.getName() + ", do you want to pick it up [y/n]: "), scanner);
            if (pickWeapon) {
                System.out.println(AppConstants.displayWeaponsChoice(player.getWeapons()));
                player.addWeapon(weapon);
                System.out.println("The weapon " + weapon.getName() + " was added to the inventory");
            }
        } else {
            if (random.nextBoolean()) {
                player.addWeapon(weapon);
            } else {
                allQuiet(player, turnHandler, inputHandler);
            }
        }
    }

    private static Weapon chooseWeapon() {
        int percentage = random.nextInt(101);
        List<Weapon> weapons = new ArrayList<>();

        if (percentage < Rarity.LEGENDARY.getPercentage()) {
            weapons = Objects.weaponsByRarity.get(Rarity.LEGENDARY);
        } else if (percentage < Rarity.EPIC.getPercentage()) {
            weapons = Objects.weaponsByRarity.get(Rarity.EPIC);
        } else if (percentage < Rarity.RARE.getPercentage()) {
            weapons = Objects.weaponsByRarity.get(Rarity.RARE);
        } else {
            weapons = Objects.weaponsByRarity.get(Rarity.COMMON);
        }

        // Select a random weapon from the list of weapons of the chosen rarity
        Weapon weapon = weapons.get(random.nextInt(weapons.size()));

        return weapon;
    }

    public static void itemFound(Player player, TurnHandler turnHandler, GameInputHandler inputHandler) {
        // Get all items available
        List<Item> items = new ArrayList<>();
        items.addAll(Objects.POTIONS);

        // TODO: Uncomment when defense items are ready
        // items.addAll(Objects.DEFENSES);

        Item item = items.get(random.nextInt(items.size()));

        if (player.getTag() == 0) {
            Scanner scanner = new Scanner(System.in);

            boolean pickItem = inputHandler.getYesNoInput(AppConstants
                    .createSelection("You found a " + item.getName() + ", do you want to pick it up? [y/n]: "), scanner);
            if (pickItem) {
                player.addItem(item);
            }
        } else {
            // Non-Human Player (NPC) Case
            // Automatically decide if the NPC will pick up the item (e.g., randomly choose "y" or "n")
            if (random.nextBoolean()) {
                player.addItem(item);
            } else {
                allQuiet(player, turnHandler, inputHandler);
            }
        }
    }


    private static Potion choosePotion() {
        int percentage = random.nextInt(101);
        List<Potion> potions = new ArrayList<>();

        if (percentage <= Rarity.EPIC.getPercentage()) {
            potions = Objects.potionsByRarity.get(Rarity.EPIC);
        } else {
            potions = Objects.potionsByRarity.get(Rarity.RARE);
        }
        Potion potion = potions.get(random.nextInt(potions.size()));

        return potion;
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
                System.out.println("You're in the storm, taking damage.");
                player.takeStormDamage(5, true);
            } else {
                System.out.println("You escaped the storm.");
            }
        } else {
            if (!escaped) {
                player.takeStormDamage(5, false);
            }
        }
    }
}
