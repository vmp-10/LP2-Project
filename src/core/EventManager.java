package core;

import characters.Player;
import common.AppConstants;
import common.LoggingManager;
import tools.*;
import tools.Objects;

import java.util.*;

public class EventManager {
    private static final Random random = new Random();

    // Generate a random event
    public void generateEvent(Player player, PlayerManager playerManager, LoggingManager loggingManager) {
        Event eventType = Event.values()[random.nextInt(Event.values().length)];
        handleEvent(eventType, player, playerManager);

        // Add logging details
        loggingManager.addToGameLog("Round " + loggingManager.getRounds() + ": Player " + player.getTag() + " got event " + eventType);
    }

    // Centralized Event Handling
    private void handleEvent(Event eventType, Player player, PlayerManager playerManager) {
        switch (eventType) {
            case DROP_LANDED -> handleDropLanded(player, playerManager);
            case WEAPON_FOUND -> handleWeaponFound(player, playerManager);
            case TRAP_FOUND -> handleTrapFound(player, playerManager);
            case ENEMY_FOUND -> handleEnemyFound(player, playerManager);
            case ITEM_FOUND -> handleItemFound(player, playerManager);
            case OUT_OF_SAFE_ZONE -> handleOutOfSafeZone(player, playerManager);
            case NOTHING -> allQuiet(player, playerManager);
            case GET_SHOT_AT -> getShotAt(player, playerManager);
        }
    }

    private Weapon getWeaponDrop() {
        List<Weapon> availableWeapons = new ArrayList<>();
        if (random.nextInt(0, 100) < AppConstants.DROP_ITEM_CHANCE) {
            availableWeapons.addAll(Objects.weaponsByRarity.get(Rarity.EPIC));
        } else {
            availableWeapons.addAll(Objects.weaponsByRarity.get(Rarity.LEGENDARY));
        }

        return availableWeapons.get(random.nextInt(availableWeapons.size()));
    }

    private Potion getItemDrop() {
        List<Potion> availablePotions = new ArrayList<>();
        if (random.nextInt(0, 100) < AppConstants.DROP_ITEM_CHANCE) {
            availablePotions.addAll(Objects.potionsByRarity.get(Rarity.RARE));
        } else {
            availablePotions.addAll(Objects.potionsByRarity.get(Rarity.EPIC));
        }

        return availablePotions.get(random.nextInt(availablePotions.size()));
    }

    private void handleDropItem(Player player, PlayerManager playerManager) {
        Potion potion = getItemDrop();

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean pickItem = GameInputHandler.getYesNoInput(AppConstants
                    .createSelection("You found a " + potion.getName() + ", do you want to pick it up? [y/n]: "), scanner);
            if (pickItem) {
                player.addItem(potion, true);
            }
        } else {
            if (random.nextInt(0, 100) < AppConstants.BOT_CHANCE_TO_USE_ITEM) {
                player.addItem(potion, false);
            }
        }
    }

    private void handleDropWeapon(Player player, PlayerManager playerManager) {
        Weapon newWeapon = getWeaponDrop();

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean pickWeapon = GameInputHandler.getYesNoInput(
                    (AppConstants.displayWeaponComparison(player.getWeapon(), newWeapon)), scanner);

            if (pickWeapon) {
                player.setWeapon(newWeapon);
                System.out.println("The weapon " + newWeapon.getName() + " was added to the inventory");
            }
        } else {
            if (random.nextInt(0, 100) < AppConstants.BOT_CHANCE_TO_USE_ITEM) {
                player.setWeapon(newWeapon);
            }
        }
    }

    private void handleDropLanded(Player player, PlayerManager playerManager) {
        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean lootDrop = GameInputHandler.getYesNoInput(AppConstants
                    .createSelection("A drop has landed nearby, do you want to loot it (Keep in mind it can be trapped) [y/n]: "), scanner);
            if (lootDrop) {
                switch (random.nextInt(0, 2)) {
                    case 0 -> handleDropWeapon(player, playerManager);
                    case 1 -> handleDropItem(player, playerManager);
                    case 2 -> dropTrapFound(player, playerManager);
                }
            }
        } else {
            switch (random.nextInt(0, 2)) {
                case 0 -> handleDropWeapon(player, playerManager);
                case 1 -> handleDropItem(player, playerManager);
                case 2 -> dropTrapFound(player, playerManager);
            }
        }
    }

    private static void dropTrapFound(Player player, PlayerManager playerManager) {
        boolean tookDamage = random.nextInt(0, 100) < AppConstants.CHANCE_FALL_TRAP;

        if (playerManager.isHuman(player)) {
            if (tookDamage) {
                System.out.println("You opened the drop but there was a trap.");
                player.takeDamage(10, true);
            } else {
                System.out.println("You got lucky and you avoided a trap, but spent 5 stamina.");
                player.setStamina(player.getStamina() - 5);
            }
        } else {
            player.takeDamage(10, false);
        }
    }

    private void allQuiet(Player player, PlayerManager playerManager) {
        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nothing is happening right now.");


            if (player.hasItems()) {
                boolean useItem = GameInputHandler
                        .getYesNoInput(AppConstants
                                .createSelection("Do you want to use an item from your inventory [y/n]: "), scanner);
                if (useItem) {
                    TurnHandler.handleHumanItemUsage(player, scanner, playerManager);
                }

            } else if (random.nextInt(0, 100) < AppConstants.CHANCE_FIND_CITY) {
                //30% chance to spawn a "see city" event
                boolean toCity = GameInputHandler
                        .getYesNoInput(AppConstants
                                .createSelection("You see a city in the distance, do you want to explore [y/n]: "), scanner);
                if (toCity) {
                    // The player chooses to explore the city, trigger a new event from the allowed city events
                    // Randomly pick an event from the city events list
                    Event cityEvent = Event.getCityEvents()[random.nextInt(Event.getCityEvents().length)];

                    handleEvent(cityEvent, player, playerManager);
                }
            }
        }
    }

    private void handleOutOfSafeZone(Player player, PlayerManager playerManager) {
        int chanceToOutrun = random.nextInt(0, 100);
        boolean escaped = chanceToOutrun >= AppConstants.CHANCE_OUTRUN_STORM || player.getStamina() < AppConstants.STAMINA_LOSS;

        if (playerManager.isHuman(player)) {
            if (!escaped) {
                System.out.println("You're in the storm, taking damage.");
                player.takeStormDamage(5, true);
            } else {
                player.setStamina(player.getStamina() - AppConstants.STAMINA_LOSS);
                System.out.println("You escaped the storm, but spent 5 stamina.");
            }
        } else {
            if (!escaped) {
                player.takeStormDamage(5, false);
            } else {
                player.setStamina(player.getStamina() - AppConstants.STAMINA_LOSS);
            }
        }
    }

    private void handleWeaponFound(Player player, PlayerManager playerManager) {
        Weapon newWeapon = chooseWeapon();

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean pickWeapon = GameInputHandler.getYesNoInput(
                    (AppConstants.displayWeaponComparison(player.getWeapon(), newWeapon)), scanner);

            if (pickWeapon) {
                player.setWeapon(newWeapon);
                System.out.println("The weapon " + newWeapon.getName() + " was added to the inventory");
            }
        } else {
            if (random.nextInt(0, 100) < AppConstants.BOT_CHANCE_TO_USE_ITEM) {
                player.setWeapon(newWeapon);
            } else {
                allQuiet(player, playerManager);
            }
        }
    }

    private static Weapon chooseWeapon() {
        int percentage = random.nextInt(101);
        List<Weapon> weapons;

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
        return weapons.get(random.nextInt(weapons.size()));
    }

    private void handleTrapFound(Player player, PlayerManager playerManager) {
        boolean tookDamage = random.nextInt(0, 100) < AppConstants.CHANCE_FALL_TRAP || player.getStamina() < AppConstants.STAMINA_LOSS;

        if (playerManager.isHuman(player)) {
            if (tookDamage) {
                System.out.println("You fell on a trap.");
                player.takeDamage(15, true);
            } else {
                System.out.println("You got lucky and you avoided a trap, but spent 5 stamina.");
                player.setStamina(player.getStamina() - AppConstants.STAMINA_LOSS);
            }
        } else {
            player.takeDamage(15, false);
            player.setStamina(player.getStamina() - AppConstants.STAMINA_LOSS);
        }
    }

    private void handleEnemyFound(Player player, PlayerManager playerManager) {
        Player other;
        do {
            other = playerManager.getRandomPlayer();
        } while (other.equals(player));

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean fight = GameInputHandler.getYesNoInput(AppConstants
                    .createSelection("You found a player, do you want to fight [y/n]: "), scanner);
            if (fight) {
                fight(player, other, playerManager);
            } else {
                escape(player, other, true);
            }
        } else {
            if (random.nextInt(0, 100) < AppConstants.BOT_CHANCE_TO_FIGHT) {
                fight(player, other, playerManager);
            } else {
                escape(player, other, false);
            }
        }
    }

    private static void fight(Player player, Player other, PlayerManager playerManager) {
        player.attack(other, playerManager.isHuman(player));
    }

    private static void escape(Player player, Player other, boolean isHuman) {
        int stamina = player.getStamina();

        if (stamina >= 5) {
            if (isHuman) {
                System.out.println("You managed to escape. ");
            }
            player.setStamina(player.getStamina() - AppConstants.STAMINA_LOSS);
        }
        else {
            if (isHuman) {
                System.out.println("You couldn't escape. ");
            }
            other.attack(player, isHuman);
        }
    }

    private void getShotAt(Player player, PlayerManager playerManager) {
        Player other;
        do {
            other = playerManager.getRandomPlayer();
        } while (other.equals(player));

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            if (random.nextInt(0, 100) < AppConstants.CHANCE_TO_GET_SHOT) {
                System.out.println("You got shot from Player " + other.getTag() + "'s " + other.getWeapon().getName() + ".");
                other.attack(player, true);
            } else {
                System.out.println("You got shot from Player " + other.getTag() + "'s " + other.getWeapon().getName() + ", but he missed.");
            }

            boolean fight = GameInputHandler.getYesNoInput(AppConstants
                    .createSelection("Do you want to try to fight back [y/n]: "), scanner);
            if (fight) {
                fight(player, other, playerManager);
            } else {
                escape(player, other, true);
            }
        } else {
            if (random.nextInt(0, 100) < AppConstants.CHANCE_TO_GET_SHOT) {
                other.attack(player, false);
                if (random.nextBoolean()) {
                    fight(player, other, playerManager);
                } else {
                    escape(player, other, false);
                }
            }
        }
    }

    private void handleItemFound(Player player, PlayerManager playerManager) {
        Potion potion = choosePotion();

        if (playerManager.isHuman(player)) {
            Scanner scanner = new Scanner(System.in);

            boolean pickItem = GameInputHandler.getYesNoInput(AppConstants
                    .createSelection("You found a " + potion.getName() + ", do you want to pick it up? [y/n]: "), scanner);
            if (pickItem) {
                player.addItem(potion, true);
            }
        } else {
            // Non-Human Player (NPC) Case
            // Automatically decide if the NPC will pick up the item (e.g., randomly choose "y" or "n")
            if (random.nextBoolean()) {
                player.addItem(potion, false);
            }
        }
    }

    private static Potion choosePotion() {
        int percentage = random.nextInt(0, 100);
        List<Potion> potions;

        if (percentage <= Rarity.EPIC.getPercentage()) {
            potions = Objects.potionsByRarity.get(Rarity.EPIC);
        } else {
            potions = Objects.potionsByRarity.get(Rarity.RARE);
        }

        return potions.get(random.nextInt(potions.size()));
    }
}
