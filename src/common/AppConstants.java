package common;

import characters.Player;
import tools.Item;
import tools.Weapon;

import java.util.ArrayList;
import java.util.List;

public final class AppConstants {
    public static final int MAX_ITEMS = 4;

    public static final String STAR_DIVIDER = "**********************";
    public static final String EQUALS_DIVIDER = "=======================";

    public static final String CHARACTER_HEADER = createHeader("Available characters: ");
    public static final String TOOLS_HEADER = createHeader("Available tools: ");
    public static final String WEAPONS_HEADER = createHeader("Available weapons: ");

    public static final String MENU = """
            %s
            *     Welcome to     *
            *  the Battle Royal  *
            %s
            1. Start game
            2. Load file
            3. Credits
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

    public static final String DIFFICULTY = """
            1. Easy Mode
            2. Normal Mode
            3. Hard Mode
            4. Joe Must Die Mode
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);


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


    private AppConstants() {
    }
}
