package common;

import characters.Player;
import tools.Item;
import tools.Weapon;

import java.util.ArrayList;
import java.util.List;

public final class AppConstants {
    public static final int MAX_ITEMS = 4;
    public static final int MAX_LOGS = 1000;

    public static final int STAMINA_LOSS = 5;
    public static final int CHANCE_TO_GET_SHOT = 30;
    public static final int CHANCE_FALL_TRAP = 25;
    public static final int CHANCE_FIND_CITY = 30;
    public static final int CHANCE_OUTRUN_STORM = 25;
    public static final int DROP_ITEM_CHANCE = 75;

    public static final int BOT_CHANCE_TO_USE_ITEM = 30;
    public static final int BOT_CHANCE_TO_FIGHT = 25;


    public static final String STAR_DIVIDER = "**********************";
    public static final String EQUALS_DIVIDER = "=======================";

    public static final String MENU = """
            %s
            *     Welcome to     *
            *  the Battle Royal  *
            %s
            1. Start game
            2. Load Game
            3. Create Game
            4. Credits
            %s
            Option:\s""".formatted(STAR_DIVIDER, STAR_DIVIDER, EQUALS_DIVIDER);

    public static final String CREDITS = """
            %s
            * Andrea Offredi
            * Fernando Alonso
            * Javier Suarez-Barcena
            * Vlad Prisacaru
            ---------------------------
            Thank you for playing!
            %s
            """.formatted(AppConstants.createBox("Credits", 25), EQUALS_DIVIDER);

    public static final String DIFFICULTY = """
            1. Easy Mode
            2. Normal Mode
            3. Hard Mode
            4. Joe Must Die Mode
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);

    public static String createCharacterString(String difficulty) {
        List<Player> players = Player.preMadePlayers.getOrDefault(difficulty, new ArrayList<>());

        StringBuilder builder = new StringBuilder();
        builder.append(difficulty).append(" characters:\n");
        int index = 1;

        for (Player player : players) {
            builder.append(index++)
                    .append(". ")
                    .append(player.getName())
                    .append(" -> [HP ")
                    .append(player.getHealth())
                    .append(", ARMOR ")
                    .append(player.getShield())
                    .append(", STAMINA ")
                    .append(player.getStamina())
                    .append(", STRENGTH ")
                    .append(player.getStrength())
                    .append("]\n");
        }

        builder.append(EQUALS_DIVIDER).append("\nOption: ");
        return builder.toString();
    }

    public static String createHeader(String title) {
        return """
                %s
                %s
                %s
                """.formatted(EQUALS_DIVIDER, title, EQUALS_DIVIDER);
    }

    public static String createSelection(String title) {
        return """
                %s
                %s
                %s
                Option:\s""".formatted(EQUALS_DIVIDER, title, EQUALS_DIVIDER);
    }

    public static String createBox(String text, int width) {
        StringBuilder box = new StringBuilder();
        int padding = 2; // Space between text and the box edges

        // Ensure the width is sufficient to fit the text with padding
        width = Math.max(width, text.length() + padding * 2);
        String border = "*".repeat(width);

        // Add the top border
        box.append(border).append("\n");

        // Add the centered text
        int totalPadding = width - 2 - text.length(); // Space left after subtracting text length and border
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;

        String centeredText = " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
        box.append("*").append(centeredText).append("*").append("\n");

        // Add the bottom border
        box.append(border);

        return box.toString();
    }

    public static String displayItems(Player player) {
        StringBuilder itemList = new StringBuilder();

        List<Item> items = player.getItems();
        int numItems = items.size();

        for (int i = 0; i < numItems; i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        return """
                %s
                Items:
                %s
                %s
                """.formatted(EQUALS_DIVIDER, itemList.toString().trim(), EQUALS_DIVIDER);
    }

    public static String displayItemChoice(Player player) {
        StringBuilder itemList = new StringBuilder();

        List<Item> items = player.getItems();
        int numItems = items.size();

        for (int i = 0; i < numItems; i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        itemList.append(numItems + 1).append(". Don't use any item.");

        return """
                %s
                You have %d items:
                %s
                %s
                Option:\s""".formatted(EQUALS_DIVIDER, numItems, itemList.toString().trim(), EQUALS_DIVIDER);
    }

    public static String displayWeaponComparison(Weapon weapon, Weapon newWeapon) {
        StringBuilder comparison = new StringBuilder();

        comparison.append("You found a " + newWeapon.getName() + ", do you want to pick it up [y/n]\n")
                .append("[(CURRENT) " + weapon.getName() + ": " + weapon.getBaseDamage() + " | (NEW) " +
                        (newWeapon.getName() + ": " + newWeapon.getBaseDamage()) + "]\n");

        return """
                %s
                %s
                %s
                Option :\s""".formatted(EQUALS_DIVIDER, comparison.toString().trim(), EQUALS_DIVIDER);
    }

    public static String displayPlayerStats(Player player) {
        StringBuilder stats = new StringBuilder();

        stats.append("[HP: " + player.getHealth() + "]");
        stats.append("[Armor: " + player.getShield() + "]");
        stats.append("[Stamina: " + player.getStamina() + "]");

        return stats.toString().trim();
    }

    public static String displayWeapon(Player player) {
        StringBuilder stats = new StringBuilder();
        Weapon weapon = player.getWeapon();

        stats.append("[" + weapon.getName() + ": " + weapon.getBaseDamage() + "]");

        return stats.toString().trim();
    }

    public static String displayWeaponStats(Weapon weapon) {
        StringBuilder stats = new StringBuilder();

        stats.append("[" + weapon.getName() + ": " + weapon.getBaseDamage() + "]");

        return stats.toString().trim();
    }

    public static String displayGameOptions(int numHumanPlayers, int numPlayers, String difficulty, List<Player> playableCharacters) {
        StringBuilder builder = new StringBuilder();
        builder.append(AppConstants.createHeader("You selected the following:"));
        builder.append("-> Total Number of Playable Players: ").append(numHumanPlayers).append("\n");
        builder.append("-> Total Number of Players: ").append(numPlayers).append("\n");
        builder.append("-> Difficulty: ").append(difficulty).append("\n");
        builder.append(AppConstants.displayCharacters(playableCharacters));

        return builder.toString();
    }

    public static String displayCharacters(List<Player> playableCharacters) {
        StringBuilder characterList = new StringBuilder();

        int numCharacters = playableCharacters.size();
        characterList.append("-> Chosen characters:\n");

        for (int i = 0; i < numCharacters; i++) {
            characterList.append("\t").append("-> Player " + i).append(": ").append(playableCharacters.get(i).getName()).append("\n");
        }

        return characterList.toString().trim();
    }

    private AppConstants() {
    }
}
